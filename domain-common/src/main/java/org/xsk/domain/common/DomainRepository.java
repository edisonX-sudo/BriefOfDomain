package org.xsk.domain.common;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.List;

public abstract class DomainRepository<E extends Entity<I>, I extends Id<?>> {

    public E findNotNone(I id) {
        E entity = find(id);
        if (entity == null)
            throw notFoundException();
        return entity;
    }

    public abstract E find(I id);

    public E findExclusiveNotNone(I id) {
        E entity = findExclusive(id);
        if (entity == null)
            throw notFoundException();
        return entity;
    }

    /**
     * 独占性的查询(如mysql内利用select for update,一般用不到)
     *
     * @param id
     * @return
     */
    public E findExclusive(I id) {
        throw new UnsupportedOperationException();
    }

    protected abstract DomainException notFoundException();

    public void save(E entity) {
        refreshEntityTs(entity);
        saveInternal(entity);
    }

    protected abstract void saveInternal(E entity);

    public void saveAll(List<E> entities) {
        entities.forEach(this::refreshEntityTs);
        saveAllInternal(entities);
    }

    protected void saveAllInternal(List<E> entities) {
        throw new UnsupportedOperationException();
    }

    void refreshEntityTs(E entity) {
        if (entity.isNew()) {
            entity.markAsCreate();
        } else {
            entity.markAsModified();
        }
    }

    protected boolean isNewEntity(Entity<I> entity) {
        return entity.isNew();
    }

    public void delete(I id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 计算集合的单差集，即只返回【集合1】中有，但是【集合2】中没有的元素，例如：
     * subtractVo([1,2,3,4],[2,3,4,5]) -》 [1]
     */
    protected <T> Collection<T> subtractVo(Collection<T> subjectSet, Collection<T> objectSet) {
        return CollUtil.subtract(subjectSet, objectSet);
    }

    protected <K extends MetaKey> void putComponentMetaData(AggregateComponent component, Class<K> key, Object val) {
        component.putMetaData(key, val);
    }

    protected <K extends MetaKey, V> V getComponentMetaData(AggregateComponent component, Class<K> key, Class<V> valType) {
        return component.getMetaData(key, valType);
    }

    protected static abstract class MetaKey {
    }

}
