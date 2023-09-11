package org.xsk.domain.common;

import cn.hutool.core.util.ObjectUtil;

import java.util.concurrent.ConcurrentHashMap;

public abstract class ValueObject extends AggregateComponent {
    boolean isUpdate = false;

    protected ValueObject() {
        this(1);
    }

    protected ValueObject(Integer metaDataCap) {
        super(new ConcurrentHashMap<>(metaDataCap));
    }

    protected <T extends ValueObject> T beginUpdateObject(T obj) {
        isUpdate = true;
        return ObjectUtil.clone(obj);
    }
}
