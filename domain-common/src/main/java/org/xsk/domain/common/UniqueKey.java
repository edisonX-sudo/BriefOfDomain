package org.xsk.domain.common;

public abstract class UniqueKey<T> extends Id<T> {
    boolean isHolderNew = true;

    public UniqueKey(T key) {
        super(key);
    }
}
