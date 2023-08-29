package org.xsk.iam.application;

import lombok.RequiredArgsConstructor;
import org.xsk.domain.common.DomainApplication;
import org.xsk.iam.domain.account.*;
import org.xsk.iam.domain.role.RoleCode;
import org.xsk.iam.domain.site.SiteCode;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class AccountApplication extends DomainApplication {
    final AccountRepository accountRepository;
    final SubAcctService subAcctService;

    public void createSubAcct(
            AppUidUniqueKey id, SiteCode curSite, Uid subAcctUid, Credential credential,
            String nickname, Avatar avatar, Region region, Map<String, Object> extraProps,
            Set<RoleCode> roles, Lang lang
    ) {
        tx(() -> {
            Account mainAcct = accountRepository.findNotNone(id);
            Account subAcct = mainAcct.createSubAcct(
                    curSite, subAcctUid, credential, nickname, avatar,
                    region, extraProps, roles, lang, subAcctService
            );
            accountRepository.save(subAcct);
        });
    }

}
