package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainService;
import org.xsk.iam.domain.validation.ValidationService;

public class CredentialModificationValidateService extends DomainService {
    ValidationService validationService;

    void changeEmail(IamAccount iamAccount, String email, String ticket) {
        validationService.validateTicket(ticket);
        iamAccount.credential = iamAccount.credential.changeEmail(email);
    }

    void changeMobile(IamAccount iamAccount, String mobile, String ticket) {
        validationService.validateTicket(ticket);
        iamAccount.credential = iamAccount.credential.changeMobile(mobile);
    }

    void changePassword(IamAccount iamAccount, String plaintextPass, String ticket) {
        validationService.validateTicket(ticket);
        iamAccount.credential = iamAccount.credential.changePassword(plaintextPass);
        iamAccount.activityRecord = iamAccount.activityRecord.recordPasswordChange();
    }
}
