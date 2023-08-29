package org.xsk.domain.common;

public abstract class DomainAbility {
    protected void throwOnCondition(boolean condition, DomainException e) {
        if (condition) {
            throw e;
        }
    }
}
