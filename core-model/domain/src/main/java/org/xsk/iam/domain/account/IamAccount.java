package org.xsk.iam.domain.account;

import org.xsk.domain.common.Code;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Entity;
import org.xsk.domain.common.EventBus;
import org.xsk.iam.domain.account.event.SubAcctPasswordRefreshed;
import org.xsk.iam.domain.account.exception.AcctStatusIllegalException;
import org.xsk.iam.domain.account.exception.OnlyMainAcctCanOperateException;
import org.xsk.iam.domain.account.exception.OnlySubAcctCanOperateException;
import org.xsk.iam.domain.app.TenantCode;
import org.xsk.iam.domain.role.RoleCode;
import org.xsk.iam.domain.site.SiteCode;

import java.util.Map;
import java.util.Set;

/**
 * iam账号
 */
public class IamAccount extends Entity<AppUidUniqueKey> {
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
    Set<AcctSiteProfile> acctSiteProfiles;

    IamAccount(AppUidUniqueKey appUidKey, TenantCode tenantCode, Uid parentUid, String domain, Set<SiteCode> siteScope, Credential credential, AcctStatus acctStatus, String nickname, Avatar avatar, Region region, Boolean needResetPassword, Map<String, Object> extraProps, AcctActivityRecord activityRecord, Set<AcctSiteProfile> acctSiteProfiles) {
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
        this.acctSiteProfiles = acctSiteProfiles;
        validSpecification();
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public void changePassword(String password, String ticket, CredentialModificationValidateService credentialModificationValidateService) {
        credentialModificationValidateService.changePassword(this, password, ticket);
    }

    public void changeEmail(String email, String ticket, CredentialModificationValidateService credentialModificationValidateService) {
        credentialModificationValidateService.changeEmail(this, email, ticket);
    }

    public void changeMobile(String mobile, String ticket, CredentialModificationValidateService credentialModificationValidateService) {
        credentialModificationValidateService.changeMobile(this, mobile, ticket);
    }

    public void forceLogout() {
        activityRecord = activityRecord.recordForceLogout();
    }

    public void loginByValidationCode(String validationCode, AcctLoginService acctLoginService) {
        acctLoginService.loginViaValidationCode(this, validationCode);
    }

    public void loginByPassword(String plaintextPass, AcctLoginService acctLoginService) {
        acctLoginService.loginInByPassword(this, plaintextPass);
    }

    public void cancelMainAcct() {
        if (!isMainAcct()) {
            throw new OnlyMainAcctCanOperateException();
        }
        this.acctStatus = AcctStatus.CLOSING;
        activityRecord = this.activityRecord.recordCancelAcct();
    }

    public void interruptCancelMainAcct() {
        if (!isMainAcct()) {
            throw new OnlyMainAcctCanOperateException();
        }
        this.acctStatus = AcctStatus.NORMAL;
        activityRecord = this.activityRecord.recordInterruptCancelAcct();
    }

    public void assignMainAcctSiteScope(Set<SiteCode> siteCodes, Lang lang, AcctSiteScopeAssignService acctSiteScopeAssignService) {
        acctSiteScopeAssignService.assignSiteScope(this, siteCodes, lang);
    }

    public void removeMainAcctSiteScope(Set<SiteCode> siteCodes, AcctSiteScopeAssignService acctSiteScopeAssignService) {
        acctSiteScopeAssignService.removeSiteScope(this, siteCodes);
    }

    public IamAccount createSubAcct(
            SiteCode curSite, Uid subAcctUid, Credential credential,
            String nickname, Avatar avatar, Region region, Map<String, Object> extraProps,
            Set<RoleCode> roles, Lang lang, SubAcctService subAcctService
    ) {
        return subAcctService.createSubAcct(
                this, curSite, subAcctUid, credential, nickname,
                avatar, region, extraProps, roles, lang
        );
    }

    public void deleteSubAcct(IamAccount subAcct, SubAcctService subAcctService) {
        subAcctService.deleteSubAcct(this, subAcct);
    }

    public void refreshPassword4NotActiveSubAcct() {
        if (isMainAcct()) {
            throw new OnlySubAcctCanOperateException();
        }
        if (this.acctStatus != AcctStatus.NOT_ACTIVE) {
            throw new AcctStatusIllegalException();
        }
        credential = credential.resetPassword();
        activityRecord = activityRecord.recordPasswordChange();
        EventBus.fire(this, new SubAcctPasswordRefreshed(id(), credential));
    }

    public void enableSubAcct() {
        if (isMainAcct()) {
            throw new OnlySubAcctCanOperateException();
        }
        if (this.acctStatus != AcctStatus.DISABLED) {
            throw new AcctStatusIllegalException();
        }
        this.acctStatus = AcctStatus.NORMAL;
    }

    public void disableSubAcct() {
        if (isMainAcct()) {
            throw new OnlySubAcctCanOperateException();
        }
        if (this.acctStatus != AcctStatus.NORMAL) {
            throw new AcctStatusIllegalException();
        }
        this.acctStatus = AcctStatus.DISABLED;
    }

    boolean isMainAcct() {
        return Code.isEmptyVal(parentUid);
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnNull(needResetPassword, "illegal");
                throwOnNull(siteScope, "illegal");
                throwOnNull(extraProps, "illegal");
                throwOnNull(acctSiteProfiles, "illegal");
                throwOnNull(domain, "illegal");
                throwOnGt(domain, 32, "illegal");
                throwOnGt(nickname, 32, "illegal");
            }
        };
    }

    @Override
    public AppUidUniqueKey id() {
        return appUidKey;
    }
}
