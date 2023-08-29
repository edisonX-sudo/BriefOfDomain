package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainService;
import org.xsk.iam.domain.account.exception.AcctCantLoginException;
import org.xsk.iam.domain.account.exception.AcctLoginFailedOverTimesException;
import org.xsk.iam.domain.validation.ValidationService;

import java.util.function.Predicate;

public class AcctLoginService extends DomainService {
    ValidationService validationService;

    void loginViaValidationCode(Account account, String validationCode) {
        loginIn(account, account1 -> validationService.checkCode(validationCode));
    }

    void loginInByPassword(Account account, String plaintextPass) {
        loginIn(account, account1 -> account1.credential.comparePassword(plaintextPass));
    }

    void loginIn(Account account, Predicate<Account> loginSuccessCondition) {
        if (!account.acctStatus.loginAvailable) {
            throw new AcctCantLoginException();
        }
        if (account.activityRecord.isUnavailableDue2LoginFailed()) {
            throw new AcctLoginFailedOverTimesException();
        }
        if (loginSuccessCondition.test(account)) {
            account.activityRecord = account.activityRecord.recordLoginSuccess();
        } else {
            account.activityRecord = account.activityRecord.recordLoginFailed();
        }
    }
}
