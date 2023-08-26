package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainService;
import org.xsk.iam.domain.validation.ValidationService;

public class AccountLoginService extends DomainService {
    ValidationService validationService;

    void loginViaValidationCode(Account account, String validationCode) {
        account.loginIn(account1 -> validationService.checkCode(validationCode));
    }
}
