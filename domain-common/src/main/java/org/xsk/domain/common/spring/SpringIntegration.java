package org.xsk.domain.common.spring;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.xsk.domain.common.DomainAbility;
import org.xsk.domain.common.DomainEvent;
import org.xsk.domain.common.FrameworkIntegration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class SpringIntegration extends FrameworkIntegration implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;

    public SpringIntegration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected <T> T tx(Callable<T> callable) {
        MainProcessCompletionSubscriberPointTrigger trigger = applicationContext.getBean(MainProcessCompletionSubscriberPointTrigger.class);
        return applicationContext.getBean(TransactionTemplate.class).execute(status -> {
            try {
                TransactionSynchronizationManager.registerSynchronization(trigger);
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResourceCleanInterceptor());
    }

    public class ResourceCleanInterceptor extends HandlerInterceptorAdapter {
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            cleanEventQueue();
        }
    }

    /**
     * 事务提交前/后触发事件总线事件(在程序中手动注册为@Bean)
     */
    public class MainProcessCompletionSubscriberPointTrigger implements TransactionSynchronization {

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

    public class DomainRegistryItemInitialization implements BeanDefinitionRegistryPostProcessor {
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
            return StrUtil.lowerFirst(StrUtil.subBefore(className, ".", false));
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        }

    }

    public class RecordEventConsumer implements InitializingBean {

        private Consumer<DomainEvent> consumer;

        public RecordEventConsumer(Consumer<DomainEvent> consumer) {
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
