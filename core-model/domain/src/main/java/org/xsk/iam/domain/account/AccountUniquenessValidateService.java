package org.xsk.iam.domain.account;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.xsk.domain.common.DomainService;
import org.xsk.iam.domain.account.exception.AcctEmailExistException;
import org.xsk.iam.domain.account.exception.AcctLoginNameExistException;
import org.xsk.iam.domain.account.exception.AcctMobileExistException;
import org.xsk.iam.domain.account.exception.AcctUidExistException;
import org.xsk.iam.domain.app.TenantCode;

@AllArgsConstructor
public class AccountUniquenessValidateService extends DomainService {

    private AccountRepository accountRepository;

    void validateAccountUniqueness(AppUidUniqueKey mainAcctUniqKey, TenantCode tenantCode, Uid parentUid, String mainAcctDomain, Credential credential) {
        if (accountRepository.existUid(mainAcctUniqKey, tenantCode))
            throw new AcctUidExistException();
        if (StrUtil.isNotEmpty(credential.loginName)
                && accountRepository.existLoginName(mainAcctUniqKey, tenantCode, parentUid, mainAcctDomain, credential.loginName))
            throw new AcctLoginNameExistException();
        if (StrUtil.isNotEmpty(credential.email)
                && accountRepository.existEmail(mainAcctUniqKey, tenantCode, parentUid, mainAcctDomain, credential.email))
            throw new AcctEmailExistException();
        if (StrUtil.isNotEmpty(credential.mobile)
                && accountRepository.existMobile(mainAcctUniqKey, tenantCode, parentUid, mainAcctDomain, credential.mobile))
            throw new AcctMobileExistException();
    }
}
