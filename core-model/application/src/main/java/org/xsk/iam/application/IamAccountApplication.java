package org.xsk.iam.application;

import lombok.RequiredArgsConstructor;
import org.xsk.domain.common.DomainApplication;
import org.xsk.iam.domain.account.*;
import org.xsk.iam.domain.role.RoleCode;
import org.xsk.iam.domain.site.SiteCode;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class IamAccountApplication extends DomainApplication {
    final IamAccountRepository iamAccountRepository;
    final SubAcctService subAcctService;

    public void createSubAcct(
            AppUidUniqueKey id, SiteCode curSite, Uid subAcctUid, Credential credential,
            String nickname, Avatar avatar, Region region, Map<String, Object> extraProps,
            Set<RoleCode> roles, Lang lang
    ) {
        tx(() -> {
            IamAccount mainAcct = iamAccountRepository.findNotNone(id);
            IamAccount subAcct = mainAcct.createSubAcct(
                    curSite, subAcctUid, credential, nickname, avatar,
                    region, extraProps, roles, lang, subAcctService
            );
            iamAccountRepository.save(subAcct);
        });
    }

}
