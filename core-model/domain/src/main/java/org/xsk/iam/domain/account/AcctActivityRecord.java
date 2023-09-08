package org.xsk.iam.domain.account;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

import java.util.Date;

/**
 * 账号活动记录
 */
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

    public AcctActivityRecord recordForceLogout() {
        AcctActivityRecord acctActivityRecord = cloneObject(this);
        acctActivityRecord.forceLogoutAt = System.currentTimeMillis();
        return acctActivityRecord;
    }

    public AcctActivityRecord recordPasswordChange() {
        AcctActivityRecord acctActivityRecord = cloneObject(this);
        acctActivityRecord.passwordChangeAt = System.currentTimeMillis();
        return acctActivityRecord;
    }

    public AcctActivityRecord recordCancelAcct() {
        AcctActivityRecord acctActivityRecord = cloneObject(this);
        acctActivityRecord.cancelAccountAt = System.currentTimeMillis();
        return acctActivityRecord;
    }

    public AcctActivityRecord recordInterruptCancelAcct() {
        AcctActivityRecord acctActivityRecord = cloneObject(this);
        acctActivityRecord.cancelAccountAt = 0L;
        return acctActivityRecord;
    }

    public AcctActivityRecord recordLoginSuccess() {
        AcctActivityRecord acctActivityRecord = cloneObject(this);
        acctActivityRecord.lastLoginAt = System.currentTimeMillis();
        acctActivityRecord.loginFailedTimes = 0;
        acctActivityRecord.recentLoginFailedAt = 0L;
        return acctActivityRecord;
    }

    public AcctActivityRecord recordLoginFailed() {
        AcctActivityRecord acctActivityRecord = cloneObject(this);
        acctActivityRecord.loginFailedTimes = this.loginFailedTimes + 1;
        acctActivityRecord.recentLoginFailedAt = System.currentTimeMillis();
        return acctActivityRecord;
    }

    public boolean isUnavailableDue2LoginFailed() {
        boolean loginFailedTsExpired = DateUtil.between(new Date(this.recentLoginFailedAt), new Date(), DateUnit.MINUTE) > 10;
        return !loginFailedTsExpired && this.loginFailedTimes > 10;
    }

//    public static void main(String[] args) {
//        AcctActivityRecord acctActivityRecord1 = new AcctActivityRecord();
//        AcctActivityRecord acctActivityRecord = acctActivityRecord1.recordLoginFailed();
//        System.out.println();
//    }
}
