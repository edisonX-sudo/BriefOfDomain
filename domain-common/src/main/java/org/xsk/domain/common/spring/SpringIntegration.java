package org.xsk.domain.common.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.support.TransactionSynchronization;
import org.xsk.domain.common.DomainEvent;
import org.xsk.domain.common.FrameworkIntegration;

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
