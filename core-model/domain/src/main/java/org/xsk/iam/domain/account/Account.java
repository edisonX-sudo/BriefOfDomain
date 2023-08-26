package org.xsk.iam.domain.account;

import org.xsk.domain.common.Code;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Entity;
import org.xsk.iam.domain.account.exception.AcctCantLoginException;
import org.xsk.iam.domain.account.exception.AcctLoginFailedOverTimesException;
import org.xsk.iam.domain.app.TenantCode;
import org.xsk.iam.domain.role.RoleCode;
import org.xsk.iam.domain.site.SiteCode;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class Account extends Entity<AppUidUniqueKey> {
    AppUidUniqueKey appUidKey;
    TenantCode tenantCode;
    Uid parentUid;
    String domain;
    Set<SiteCode> siteScope;
    Credential credential;
    AcctStatus acctStatus;

    String nickname;
    Avatar avatar;
    Region region;
    Boolean needResetPassword;
    Map<String, Object> extraProps;

    AcctActivityRecord activityRecord;
    Set<AccountSiteProfile> accountSiteProfiles;

    Account(AppUidUniqueKey appUidKey, TenantCode tenantCode, Uid parentUid, String domain, Set<SiteCode> siteScope, Credential credential, AcctStatus acctStatus, String nickname, Avatar avatar, Region region, Boolean needResetPassword, Map<String, Object> extraProps, AcctActivityRecord activityRecord, Set<AccountSiteProfile> accountSiteProfiles) {
        this.appUidKey = appUidKey;
        this.tenantCode = tenantCode;
        this.parentUid = parentUid;
        this.domain = domain;
        this.siteScope = siteScope;
        this.credential = credential;
        this.acctStatus = acctStatus;
        this.nickname = nickname;
        this.avatar = avatar;
        this.region = region;
        this.needResetPassword = needResetPassword;
        this.extraProps = extraProps;
        this.activityRecord = activityRecord;
        this.accountSiteProfiles = accountSiteProfiles;
        validSpecification();
    }

    public Account createSubAcct(
            SiteCode curSite, Uid subAcctUid, Credential credential,
            String nickname, Avatar avatar, Region region, Map<String, Object> extraProps,
            Set<RoleCode> roles, Lang lang, SubAcctCreateService subAcctCreateService
    ) {
        return subAcctCreateService.createSubAcct(
                this, curSite, subAcctUid, credential, nickname,
                avatar, region, extraProps, roles, lang
        );
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public void changePassword(String password, String ticket, AccountCredentialModifyService accountCredentialModifyService) {
        accountCredentialModifyService.changePassword(this, password, ticket);
    }

    public void changeEmail(String email, String ticket, AccountCredentialModifyService accountCredentialModifyService) {
        accountCredentialModifyService.changeEmail(this, email, ticket);
    }

    public void changeMobile(String mobile, String ticket, AccountCredentialModifyService accountCredentialModifyService) {
        accountCredentialModifyService.changeMobile(this, mobile, ticket);
    }

    public void forceLogout() {
        activityRecord.recordForceLogout();
    }

    public void loginInByPassword(String plaintextPass) {
        loginIn(account -> account.credential.comparePassword(plaintextPass));
    }

    void loginIn(Predicate<Account> loginSuccessCondition) {
        if (!acctStatus.loginAvailable) {
            throw new AcctCantLoginException();
        }
        if (activityRecord.isUnavailableDue2LoginFailed()) {
            throw new AcctLoginFailedOverTimesException();
        }
        if (loginSuccessCondition.test(this)) {
            activityRecord.recordLoginSuccess();
        } else {
            activityRecord.recordLoginFailed();
        }
    }

    public void loginInByValidationCode(String validationCode, AccountLoginService accountLoginService) {
        accountLoginService.loginViaValidationCode(this, validationCode);
    }

    public void cancelAcct() {
        this.activityRecord.recordCancelAcct();
        this.acctStatus = AcctStatus.CLOSING;
    }

    public void interruptCancelAcct() {
        this.activityRecord.recordInterruptCancelAcct();
        this.acctStatus = AcctStatus.NORMAL;
    }

    public void assignSiteScope(Set<SiteCode> siteCodes, Lang lang, AcctSiteScopeAssignService acctSiteScopeAssignService) {
        acctSiteScopeAssignService.assignSiteScope(this, siteCodes, lang);
    }

    public void removeSiteScope(Set<SiteCode> siteCodes, AcctSiteScopeAssignService acctSiteScopeAssignService) {
        acctSiteScopeAssignService.removeSiteScope(this, siteCodes);
    }

    boolean isMainAcct() {
        return Code.isEmptyVal(parentUid);
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return throwIllegalStateException -> {
        };
    }

    @Override
    public AppUidUniqueKey id() {
        return appUidKey;
    }
}
