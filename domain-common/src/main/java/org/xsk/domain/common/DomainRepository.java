package org.xsk.domain.common;

import java.util.List;

public abstract class DomainRepository<E extends Entity<I>, I extends Id<?>> {

    public abstract E find(I id);

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

    protected void putVoMetaData(ValueObject valueObject, Object key, Object val) {
        valueObject.putMetaData(key, val);
    }

    protected <T> T getVoMetaData(ValueObject valueObject, Object key, Class<T> valType) {
        return valueObject.getMetaData(key, valType);
    }

    protected boolean isNewEntity(Entity<I> entity) {
        return entity.isNew();
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

    public void delete(I id) {
        throw new UnsupportedOperationException();
    }

    public E findNotNone(I id) {
        E entity = find(id);
        if (entity == null)
            throw notFoundException();
        return entity;
    }

    protected abstract DomainException notFoundException();
}
