package org.xsk.iam.domain.account;

public enum AcctStatus {
    NotActive(false),
    Normal(true),
    Disabled(false),
    Locked(false),
    Closing(false),
    Closed(false);

    final boolean loginAvailable;

    AcctStatus(boolean loginAvailable) {
        this.loginAvailable = loginAvailable;
    }
}
