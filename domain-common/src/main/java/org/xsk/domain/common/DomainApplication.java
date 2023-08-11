package org.xsk.domain.common;

import java.util.concurrent.Callable;

public abstract class DomainApplication extends DomainAbility {
    public DomainApplication() {
    }

    protected <T> T tx(Callable<T> callable){
        return FrameworkIntegration.current.tx(callable);
    }

}
