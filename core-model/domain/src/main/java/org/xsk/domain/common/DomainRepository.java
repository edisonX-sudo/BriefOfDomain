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

    protected void refreshEntityTs(E entity) {
        EntityTsRefresher.refreshTs(entity);
    }

    /**
     * 独占性的查询(如mysql内利用select for update,一般用不到)
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
