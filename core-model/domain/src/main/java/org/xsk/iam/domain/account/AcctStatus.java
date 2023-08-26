package org.xsk.iam.domain.account;

public enum AcctStatus {
    NOT_ACTIVE(false),
    NORMAL(true),
    DISABLED(false),
    LOCKED(false),
    CLOSING(false),
    CLOSED(false);

    final boolean loginAvailable;

    AcctStatus(boolean loginAvailable) {
        this.loginAvailable = loginAvailable;
    }
}
