package org.xsk.domain.common;

public class EntityTsRefresher {

    static <E extends Entity<I>, I extends Id<?>> void refreshTs(E entity) {
        if (entity.isNew()) {
            entity.markAsCreate();
        } else {
            entity.markAsModified();
        }
    }
}
