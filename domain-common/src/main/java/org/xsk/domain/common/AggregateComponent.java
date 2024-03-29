package org.xsk.domain.common;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 聚合内的组件对象
 * ps: 虽然AggregateComponent实现Serializable,但直接对其进行序列化存储并非常不被推荐(建议用对应pojo进行序列化存储)
 */
public abstract class AggregateComponent implements Serializable {
    /**
     * 用例: 若实体内值对象存储在不同的表,那么在对对象进行restore时可以把值对象对应表的id存入(vo中和关联实体中),
     * 方便后面进行实体更新的时候,可以用此找到值对象的表的id来进行更新操作,
     * 如: 加载附属表记录(id:1,2,3),除vo内metaData挂上对应id,实体也挂上vo_ids:1,2,3,后期经过新增和删除,vo对线剩下:vo1(id:''),vo1(id:1),
     * 则可要保存: (插入: vo1, 更新: vo2), 删除: vos(id:2,3) //DomainRepository.subtractVo([1,2,3],['',1])->[2,3]
     * <p>
     * 经验上实体的metaData可以存自身id(id:1),延伸表的ids(xx_vo_ids:[2,3]);关联延伸表的Vo上可以存自身id(id:2)
     * <p>
     * 当然场景不一定要如上,上面只是一种用例,理论上可以用来存储任何需要的数据,做一个场景内不同方法上下文的数据交换
     */
    final Map<Object, Object> metaData;

    public AggregateComponent(Map<Object, Object> metaData) {
        this.metaData = metaData;
    }

    <V> V getMetaData(Object key, Class<V> valType) {
        Object obj = metaData.get(key);
        if (obj == null)
            return null;
        return valType.cast(obj);
    }

    void putMetaData(Object key, Object val) {
        metaData.put(key, val);
    }

    protected void mergeMetaData(AggregateComponent component) {
        metaData.putAll(component.metaData);
    }

    /**
     * 校验器, 校验字段内容合法性
     *
     * @return
     */
    protected abstract DomainSpecificationValidator specificationValidator();

    protected void validSpecification() {
        validSpecification(null);
    }

    protected void validSpecification(DomainSpecificationValidator additionalValidator) {
        DomainSpecificationValidator domainSpecificationValidator = specificationValidator();
        if (domainSpecificationValidator == null) {
            throw new UnsupportedOperationException("specificationValidator() suppose 2 be implemented");
        }
        BiConsumer<Boolean, String> throwIllegalStateException = (condition, msg) -> {
            if (condition) {
                throw new IllegalStateDomainException(msg);
            }
        };
        domainSpecificationValidator.validSpecification();
        if (additionalValidator == null) {
            return;
        }
        additionalValidator.validSpecification();
    }

}
