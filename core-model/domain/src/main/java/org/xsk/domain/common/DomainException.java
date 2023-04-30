package org.xsk.domain.common;

public abstract class DomainException extends RuntimeException{

    public DomainException(String message) {
        super(message);
    }
}
