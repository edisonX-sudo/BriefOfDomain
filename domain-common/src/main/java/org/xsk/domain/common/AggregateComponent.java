package org.xsk.domain.common;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * 聚合内的组件对象
 */
public abstract class AggregateComponent implements Serializable {
    /**
     * 用例: 若实体内值对象存储在不同的表,那么在对对象进行restore时可以把值对象对应表的id存入(vo中和关联实体中),
     * 方便后面进行实体更新的时候,可以用此找到值对象的表的id来进行更新操作,
     * 如: 加载附属表记录(id:1,2,3),除vo内metaData挂上对应id,实体也挂上vo_ids:1,2,3,后期经过新增和删除,vo对线剩下:vo1(id:''),vo1(id:1),
     * 则可要保存: (插入: vo1, 更新: vo2), 删除: vos(id:2,3) //DomainRepository.subtractVo([1,2,3],['',1])->[2,3]
     * <p>
     * 当然场景不一定要如上,上面只是一种用例,理论上可以用来存储任何需要的数据,做一个场景内不同方法上下文的数据交换
     */
    Map<Object, Object> metaData = new ConcurrentHashMap<>(2);

    <V> V getMetaData(Object key, Class<V> valType) {
        return valType.cast(metaData.get(key));
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
        domainSpecificationValidator.validSpecification(throwIllegalStateException);
        if (additionalValidator == null) {
            return;
        }
        additionalValidator.validSpecification(throwIllegalStateException);
    }

}
