package org.xsk.domain.common;

public abstract class Id<T> extends Code<T> {
    public Id(T code) {
        super(code);
    }
}
