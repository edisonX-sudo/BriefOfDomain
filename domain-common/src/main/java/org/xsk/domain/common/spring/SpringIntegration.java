package org.xsk.domain.common.spring;

import org.springframework.transaction.support.TransactionSynchronization;
import org.xsk.domain.common.ComponentIntegration;

public class SpringIntegration extends ComponentIntegration {
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
}
