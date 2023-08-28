package org.xsk.domain.common;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public abstract class DomainEvent implements Serializable {
    private final long occurredAt;
    Map<String, Object> metaData = new ConcurrentHashMap<>(2);
    Entity<?> subject;

    public DomainEvent() {
        occurredAt = System.currentTimeMillis();
    }

    public long occurredAt() {
        return occurredAt;
    }

    public RecordStatus recordInfo() {
        return RecordStatus.DONT_RECORD;
    }

    void putMetaData(String key, Object obj) {
        metaData.put(key, obj);
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

        public EntityCreatedEvent(T id) {
            this.idSupplier = () -> id;
        }

        public T entityId() {
            return idSupplier.get();
        }
    }

}
