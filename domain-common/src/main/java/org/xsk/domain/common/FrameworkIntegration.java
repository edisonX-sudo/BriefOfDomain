package org.xsk.domain.common;

import cn.hutool.core.util.ObjectUtil;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public abstract class FrameworkIntegration {
    volatile static FrameworkIntegration current;

    public FrameworkIntegration() {
        if (current != null) {
            throw new RuntimeException("FrameworkIntegration can only be initialized once");
        }
        current = this;
    }

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

    protected abstract <T> T tx(Callable<T> callable);

    protected static <T extends ValueObject> T cloneValueObject(T valueObject) {
        return ObjectUtil.clone(valueObject);
    }
}
