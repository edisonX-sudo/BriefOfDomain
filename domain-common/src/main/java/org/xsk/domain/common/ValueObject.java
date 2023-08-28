package org.xsk.domain.common;

import cn.hutool.core.util.ObjectUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ValueObject extends AggregateComponent {
    protected ValueObject() {
        super(new ConcurrentHashMap<>(1));
    }

    protected ValueObject(Map<Object, Object> metaData) {
        super(metaData);
    }

    protected static <T extends ValueObject> T cloneObject(T obj) {
        return ObjectUtil.clone(obj);
    }
}
