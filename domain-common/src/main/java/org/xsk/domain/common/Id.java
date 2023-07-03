package org.xsk.domain.common;

public abstract class Id<T> extends ValueObject{
    T id;

    public Id(T id) {
        this.id = id;
    }

    public T value(){
        return id;
    }
}
