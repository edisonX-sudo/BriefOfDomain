package org.xsk.domain.common;

public abstract class NotFoundEntityDomainException extends DomainException {
    private final Id<?> id;

    protected NotFoundEntityDomainException(Id<?> id, String message) {
        this(id, message, true);
    }

    protected NotFoundEntityDomainException(Id<?> id, String message, boolean msgWithId) {
        super(msgWithId ? message + ": " + id.value() : message);
        this.id = id;
    }

}
