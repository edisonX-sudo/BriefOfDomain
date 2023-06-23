package org.xsk.domain.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ValueObject {
    /**
     * 若实体内值对象存储在不同的表,那么在对对象进行restore时可以把值对象对应表的id存入,
     * 方便后面进行实体更新的时候,可以用此找到值对象的表的id来进行更新操作
     */
    Map<Object, Object> metaData = new ConcurrentHashMap<>();

    <T> T getMetaData(Object key, Class<T> valType) {
        return valType.cast(metaData.get(key));
    }

    void putMetaData(Object key, Object val) {
        metaData.put(key, val);
    }

    protected void mergeMetaData(ValueObject vo) {
        metaData.putAll(vo.metaData);
    }

}
