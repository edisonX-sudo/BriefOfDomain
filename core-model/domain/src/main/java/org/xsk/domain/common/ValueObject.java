package org.xsk.domain.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ValueObject {
    Map<Object, Object> metaData = new ConcurrentHashMap<>();

    <T> T getMetaData(Object key, Class<T> valType) {
        return valType.cast(metaData.get(key));
    }

    void putMetaData(Object key, Object val) {
        metaData.put(key, val);
    }
}
