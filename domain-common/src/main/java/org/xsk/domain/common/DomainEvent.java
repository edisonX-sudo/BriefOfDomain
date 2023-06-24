package org.xsk.domain.common;

import java.util.function.Supplier;

public abstract class DomainEvent {

    private final long occurredAt;

    public DomainEvent() {
        occurredAt = System.currentTimeMillis();
    }

    public long occurredAt() {
        return occurredAt;
    }

    public RecordStatus recordInfo() {
        return RecordStatus.DONT_RECORD;
    }

    public enum RecordStatus {
        DONT_RECORD,//不记录
        DO_RECORD,//如记录到TSDB
    }

    public static abstract class EntityCreatedEvent<T extends Id<?>> extends DomainEvent {
        private final Supplier<T> idSupplier;

        public EntityCreatedEvent(Supplier<T> idSupplier) {
            this.idSupplier = idSupplier;
        }

        public T entityId() {
            return idSupplier.get();
        }
    }

}
