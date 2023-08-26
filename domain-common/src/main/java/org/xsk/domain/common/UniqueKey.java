package org.xsk.domain.common;

public abstract class UniqueKey<T> extends Id<T> {

    public UniqueKey(T id) {
        super(id);
    }
}
