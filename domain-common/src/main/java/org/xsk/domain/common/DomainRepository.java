package org.xsk.domain.common;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.Set;

public abstract class DomainRepository<E extends Entity<?>, I extends Id<?>> extends DomainAbility {

    public DomainRepository() {
    }

    /**
     * 验证是否存在,不存在抛异常
     *
     * @param id 领域id
     */
    public void validExistence(I id) {
        findNotNone(id);
    }

    /**
     * 验证是否存在
     *
     * @param id 领域id
     * @return 存在为true
     */
    public boolean exist(I id) {
        return find(id) != null;
    }

    /**
     * 查找领域实体,不存在抛异常
     *
     * @param id 领域id
     * @return 领域实体
     */
    public E findNotNone(I id) {
        E entity = find(id);
        if (entity == null)
            throw notFoundException(id);
        return entity;
    }

    /**
     * 查找领域实体
     *
     * @param id 领域id
     * @return 领域实体
     */
    public E find(I id) {
        E internal = findInternal(id);
        refreshEntityAsNotNew(internal);
        return internal;
    }

    /**
     * 被find/findNotNone使用,不需要调用refreshEntityAsNotNew()
     * @param id 领域id
     * @return 领域实体
     */
    protected abstract E findInternal(I id);

    /**
     * 独占(如mysql内利用select for update,一般用不到)的查找领域实体,不存在抛异常
     *
     * @param id 领域id
     * @return 领域实体
     */
    public E findExclusiveNotNone(I id) {
        E entity = findExclusive(id);
        if (entity == null)
            throw notFoundException(id);
        return entity;
    }

    /**
     * 独占(如mysql内利用select for update,一般用不到)的查找领域实体
     *
     * @param id 领域id
     * @return 领域实体
     */
    public E findExclusive(I id) {
        E exclusiveInternal = findExclusiveInternal(id);
        refreshEntityAsNotNew(exclusiveInternal);
        return exclusiveInternal;
    }

    /**
     * 被findExclusive/findExclusiveNotNone使用,不需要调用refreshEntityAsNotNew()
     * @param id 领域id
     * @return 领域实体
     */
    protected E findExclusiveInternal(I id) {
        throw new UnsupportedOperationException("this method need 2 be implemented");
    }

    protected abstract NotFoundEntityDomainException notFoundException(I id);

    public void save(E entity) {
        refreshEntityModifiedTs(entity);
        saveInternal(entity);
        refreshEntityAsNotNew(entity);
    }

    protected abstract void saveInternal(E entity);

    public void saveAll(Set<E> entities) {
        entities.forEach(this::refreshEntityModifiedTs);
        saveAllInternal(entities);
        entities.forEach(this::refreshEntityAsNotNew);
    }

    protected void saveAllInternal(Set<E> entities) {
        throw new UnsupportedOperationException("this method need 2 be implemented");
    }

    void refreshEntityModifiedTs(E entity) {
        if (isNewEntity(entity)) {
            entity.markAsCreate();
        }
        entity.markAsModified();
    }

    /**
     * 标记领域实体实体是否为新实体,在自定义find方法时基本都会用
     *
     * @param entity 领域实体
     */
    protected void refreshEntityAsNotNew(E entity) {
        entity.markAsNotNew();
    }

    /**
     * 判断实体是否是新实体
     *
     * @param entity 领域实体
     * @return 是否是新实体
     */
    protected boolean isNewEntity(E entity) {
        return entity.isNew();
    }

    public void delete(I id) {
        deleteInternal(id);
    }

    protected void deleteInternal(I id) {
        throw new UnsupportedOperationException("this method need 2 be implemented");
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
