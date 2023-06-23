package org.xsk.domain.common;

public abstract class ComponentIntegration {

    protected static void triggerEventsBeforeMainProcessCompleted() {
        EventBus.triggerBeforeMainProcessCompleted();
    }

    protected static void triggerEventsAfterMainProcessCompleted() {
        EventBus.triggerAfterMainProcessCompleted();
    }

    protected static void cleanEventQueue() {
        EventBus.cleanEventQueue();
    }
}
