package org.xsk.domain.common;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;

public class SpringIntegration {
    @Component
    public static class MainProcessCompletionSubscriberPointInvoker implements TransactionSynchronization {

        @Override
        public void beforeCommit(boolean readOnly) {
            EventBus.triggerBeforeMainProcessCompleted();
        }

        @Override
        public void afterCompletion(int status) {
            if (STATUS_COMMITTED != status) {
                EventBus.cleanEventQueue();
                return;
            }
            EventBus.triggerAfterMainProcessCompleted();
        }
    }
}
