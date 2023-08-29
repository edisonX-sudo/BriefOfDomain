package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainService;
import org.xsk.iam.domain.validation.ValidationService;

public class AccountCredentialValidateModificationService extends DomainService {
    ValidationService validationService;

    void changeEmail(Account account, String email, String ticket) {
        validationService.validateTicket(ticket);
        account.credential = account.credential.changeEmail(email);
    }

    void changeMobile(Account account, String mobile, String ticket) {
        validationService.validateTicket(ticket);
        account.credential = account.credential.changeMobile(mobile);
    }

    void changePassword(Account account, String plaintextPass, String ticket) {
        validationService.validateTicket(ticket);
        account.credential = account.credential.changePassword(plaintextPass);
        account.activityRecord = account.activityRecord.recordPasswordChange();
    }
}
