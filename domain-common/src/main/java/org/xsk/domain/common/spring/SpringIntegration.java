package org.xsk.domain.common.spring;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xsk.domain.common.DomainAbility;
import org.xsk.domain.common.EventBus;
import org.xsk.domain.common.FrameworkIntegration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

public class SpringIntegration extends FrameworkIntegration implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    protected <T> T tx(Callable<T> callable) {
        MainProcessCompletionSubscriberPointTrigger trigger = getMainProcessCompletionSubscriberPointTriggerBean();
        TransactionTemplate transactionTemplate = applicationContext.getBean(TransactionTemplate.class);
        return transactionTemplate.execute(status -> {
            try {
                if (trigger != null) {
                    TransactionSynchronizationManager.registerSynchronization(trigger);
                }
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private MainProcessCompletionSubscriberPointTrigger getMainProcessCompletionSubscriberPointTriggerBean() {
        try {
            return applicationContext.getBean(MainProcessCompletionSubscriberPointTrigger.class);
        } catch (NoSuchBeanDefinitionException ex) {
            return null;
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResourceCleanInterceptor());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //todo 可能能通过spring的AppListener订阅容器启动完毕来检查MustInit是否都被初始化了
    public class ResourceCleanInterceptor implements HandlerInterceptor, MustInit {
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            cleanEventQueue();
        }
    }

    /**
     * 事务提交前/后触发事件总线事件(在程序中手动注册为@Bean)
     */
    public class MainProcessCompletionSubscriberPointTrigger implements TransactionSynchronization, MustInit {

        @Override
        public void beforeCommit(boolean readOnly) {
            triggerEventBeforeMainProcessCompleted();
        }

        @Override
        public void afterCompletion(int status) {
            if (STATUS_COMMITTED != status) {
                cleanEventQueue();
                return;
            }
            triggerEventAfterMainProcessCompleted();
        }
    }

    public class DomainRegistryItemInitialization implements BeanDefinitionRegistryPostProcessor, InitAble {
        private String basePackage;

        public DomainRegistryItemInitialization(String basePackage) {
            this.basePackage = basePackage;
        }

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            ClassPathScanningCandidateComponentProvider scanner =
                    new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AssignableTypeFilter(DomainAbility.class));
            Set<BeanDefinition> definitions = scanner.findCandidateComponents(basePackage);
            registerDomainAbilityBeanDefinition(registry, definitions);
        }

        private void registerDomainAbilityBeanDefinition(BeanDefinitionRegistry registry, Set<BeanDefinition> definitions) {
            definitions.forEach(definition -> {
                try {
                    String beanClassName = definition.getBeanClassName();
                    Class<?> beanClazz = Class.forName(beanClassName);
                    if (isClassAccessLevelPrivate(beanClazz)) {
                        return;
                    }
                    String simpleClassName = buildSimpleClassName(beanClassName);
                    if (registry.isBeanNameInUse(simpleClassName)) {
                        BeanDefinition beanDefinition = registry.getBeanDefinition(simpleClassName);
                        if (!Objects.equals(beanClassName, beanDefinition.getBeanClassName())) {
                            throw new IllegalStateException("bean name in used, " + simpleClassName);
                        }
                        return;
                    }
                    registry.registerBeanDefinition(simpleClassName, definition);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        private boolean isClassAccessLevelPrivate(Class<?> beanClazz) {
            return Modifier.toString(beanClazz.getModifiers()).startsWith("private");
        }

        private String buildSimpleClassName(String className) {
            return StrUtil.lowerFirst(StrUtil.subAfter(className, ".", true));
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        }

    }

    public class RecordEventConsumer implements InitializingBean, InitAble {

        private EventBus.EventConsumer consumer;

        public RecordEventConsumer(EventBus.EventConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            if (consumer == null)
                return;
            consumeEventNeedRecord(consumer);
        }
    }


}
