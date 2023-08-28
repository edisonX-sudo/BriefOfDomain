package org.xsk.domain.common;

import cn.hutool.core.util.ObjectUtil;

import java.util.concurrent.ConcurrentHashMap;

public abstract class ValueObject extends AggregateComponent {
    protected ValueObject() {
        this(1);
    }

    protected ValueObject(Integer metaDataCap) {
        super(new ConcurrentHashMap<>(metaDataCap));
    }

    protected static <T extends ValueObject> T cloneObject(T obj) {
        return ObjectUtil.clone(obj);
    }
}
