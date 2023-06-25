package org.xsk.domain.common;

import java.util.function.Consumer;

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

    protected static void consumeEventNeedRecord(Consumer<DomainEvent> consumer) {
        EventBus.consumeEventNeedRecord = consumer;
    }
}
