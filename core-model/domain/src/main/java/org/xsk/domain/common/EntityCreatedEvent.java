package org.xsk.domain.common;

import java.util.function.Supplier;

public abstract class EntityCreatedEvent<T extends Id<?>> extends DomainEvent {

    private final Supplier<T> idSupplier;

    public EntityCreatedEvent(Supplier<T> idSupplier) {
        this.idSupplier = idSupplier;
    }

    public T entityId() {
        return idSupplier.get();
    }

}
