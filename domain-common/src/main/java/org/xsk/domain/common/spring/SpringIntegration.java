package org.xsk.domain.common.spring;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.transaction.support.TransactionSynchronization;
import org.xsk.domain.common.DomainAbility;
import org.xsk.domain.common.DomainEvent;
import org.xsk.domain.common.FrameworkIntegration;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.function.Consumer;

public class SpringIntegration extends FrameworkIntegration {
    /**
     * 事务提交前/后触发事件总线事件(在程序中手动注册为@Bean)
     */
    public static class MainProcessCompletionSubscriberPointTrigger implements TransactionSynchronization {

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

    public static class DomainRegistryItemInitialization implements BeanDefinitionRegistryPostProcessor {
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

    public static class RecordEventConsumer implements InitializingBean {

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
