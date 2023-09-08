package org.xsk.domain.account;

import lombok.AllArgsConstructor;
import org.xsk.domain.account.exception.OnlyMainAccountOperate;
import org.xsk.domain.common.DomainService;

import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
public class AccountPrivilegeService extends DomainService {
    AccountRepo accountRepo;

    void handoverMainPrivilege(Account mainAccount, Account subAccount) {
        if (!mainAccount.isMainAccount()) {
            throw new OnlyMainAccountOperate();
        }
        AccountId mainAccountId = mainAccount.accountId;
        AccountId subAccountId = subAccount.accountId;
        accountRepo.validExistence(subAccountId);
        List<Account> accountGroup = accountRepo.findAccountGroup(mainAccountId);
        accountGroup.forEach(account -> {
            if (account.equals(subAccount)) {
                account.parentAccountId = null;
                subAccount.parentAccountId = null;
            } else {
                account.parentAccountId = subAccountId;
            }
        });
        accountRepo.saveAll(new HashSet<>(accountGroup));
    }
}
