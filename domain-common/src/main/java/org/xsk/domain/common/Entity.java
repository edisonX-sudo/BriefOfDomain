package org.xsk.domain.common;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public abstract class Entity<T extends Id<?>> extends AggregateComponent {
    protected Long createAt;
    protected Long modifiedAt;

    @EqualsAndHashCode.Include
    public abstract T id();

    void markAsCreate() {
        //may be auto in jpa
        createAt = System.currentTimeMillis();
    }

    public Long createAt() {
        return createAt;
    }

    void markAsModified() {
        //may be auto in jpa
        modifiedAt = System.currentTimeMillis();
    }

    public Long modifiedAt() {
        return modifiedAt;
    }


    protected Boolean isNew() {
        return id() == null;
    }

    protected void markAsNotNew() {

    }
//    boolean newEntity = false;//by default
//    //invoke in factory maybe
//    void markAsNew() {
//        newEntity = true;
//    }

}
