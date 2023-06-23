package org.xsk.domain.common;

public abstract class ComponentIntegration {

    protected static void triggerEventBeforeMainProcessCompleted() {
        EventBus.triggerBeforeMainProcessCompleted();
    }

    protected static void triggerEventAfterMainProcessCompleted() {
        EventBus.triggerAfterMainProcessCompleted();
    }

    protected static void cleanEventQueue() {
        EventBus.cleanEventQueue();
    }
}
