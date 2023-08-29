package org.xsk.domain.account;

import org.xsk.domain.common.DomainService;

public abstract class AuthService extends DomainService {
    public abstract String validateAuth(String token);
}
