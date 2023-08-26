package org.xsk.iam.domain.account;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

import java.util.Date;

public class AcctActivityRecord extends ValueObject {
    Integer loginFailedTimes;
    Long recentLoginFailedAt;
    Long lastLoginAt;

    Long forceLogoutAt;

    Long passwordChangeAt;
    Long cancelAccountAt;

    public AcctActivityRecord() {
        this(0, 0L, 0L, 0L, 0L, 0L);
    }

    public AcctActivityRecord(Integer loginFailedTimes, Long recentLoginFailedAt, Long lastLoginAt, Long forceLogoutAt, Long passwordChangeAt, Long cancelAccountAt) {
        this.loginFailedTimes = loginFailedTimes;
        this.recentLoginFailedAt = recentLoginFailedAt;
        this.lastLoginAt = lastLoginAt;
        this.forceLogoutAt = forceLogoutAt;
        this.passwordChangeAt = passwordChangeAt;
        this.cancelAccountAt = cancelAccountAt;
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }

    public void recordForceLogout() {
        this.forceLogoutAt = System.currentTimeMillis();
    }

    public void recordPasswordChange() {
        this.passwordChangeAt = System.currentTimeMillis();
    }

    public void recordCancelAcct() {
        this.cancelAccountAt = System.currentTimeMillis();
    }

    public void recordInterruptCancelAcct() {
        this.cancelAccountAt = 0L;
    }

    public void recordLoginSuccess() {
        this.lastLoginAt = System.currentTimeMillis();
        this.loginFailedTimes = 0;
        this.recentLoginFailedAt = 0L;
    }

    public void recordLoginFailed() {
        this.loginFailedTimes = this.loginFailedTimes + 1;
        this.recentLoginFailedAt = System.currentTimeMillis();
    }

    public boolean isUnavailableDue2LoginFailed() {
        boolean loginFailedTsExpired = DateUtil.between(new Date(this.recentLoginFailedAt), new Date(), DateUnit.MINUTE) > 10;
        return !loginFailedTsExpired && this.loginFailedTimes > 10;
    }
}
