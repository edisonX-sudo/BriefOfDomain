package org.xsk.domain.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聚合内的组件对象
 */
public class AggregateComponent {
    /**
     * 用例: 若实体内值对象存储在不同的表,那么在对对象进行restore时可以把值对象对应表的id存入,
     * 方便后面进行实体更新的时候,可以用此找到值对象的表的id来进行更新操作
     * <p>
     * 当然场景不一定要如上,上面只是一种用例,理论上可以用来存储任何需要的数据,做不同上下文的数据交换
     */
    Map<Object, Object> metaData = new ConcurrentHashMap<>(2);

    <V> V getMetaData(Object key, Class<V> valType) {
        return valType.cast(metaData.get(key));
    }

    void putMetaData(Object key, Object val) {
        metaData.put(key, val);
    }

    protected void mergeMetaData(ValueObject vo) {
        metaData.putAll(vo.metaData);
    }

}
