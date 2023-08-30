package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainService;
import org.xsk.iam.domain.account.exception.AcctCantLoginException;
import org.xsk.iam.domain.account.exception.AcctLoginFailedOverTimesException;
import org.xsk.iam.domain.validation.ValidationService;

import java.util.function.Predicate;

public class AcctLoginService extends DomainService {
    ValidationService validationService;

    void loginViaValidationCode(IamAccount iamAccount, String validationCode) {
        loginIn(iamAccount, account1 -> validationService.checkCode(validationCode));
    }

    void loginInByPassword(IamAccount iamAccount, String plaintextPass) {
        loginIn(iamAccount, account1 -> account1.credential.comparePassword(plaintextPass));
    }

    void loginIn(IamAccount iamAccount, Predicate<IamAccount> loginSuccessCondition) {
        if (!iamAccount.acctStatus.loginAvailable) {
            throw new AcctCantLoginException();
        }
        if (iamAccount.activityRecord.isUnavailableDue2LoginFailed()) {
            throw new AcctLoginFailedOverTimesException();
        }
        if (loginSuccessCondition.test(iamAccount)) {
            iamAccount.activityRecord = iamAccount.activityRecord.recordLoginSuccess();
        } else {
            iamAccount.activityRecord = iamAccount.activityRecord.recordLoginFailed();
        }
    }
}
