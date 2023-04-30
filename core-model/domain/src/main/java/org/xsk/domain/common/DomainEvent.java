package org.xsk.domain.common;

public abstract class DomainEvent {

    private final long occurredAt;

    public DomainEvent() {
        occurredAt = System.currentTimeMillis();
    }

    public long occurredAt() {
        return occurredAt;
    }

    public RecordStatus recordInfo() {
        return RecordStatus.NONE;
    }

    enum RecordStatus {
        NONE,
        TSDB,
    }
}
