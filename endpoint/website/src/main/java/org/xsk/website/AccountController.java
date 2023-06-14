package org.xsk.website;

import cn.hutool.core.map.BiMap;
import org.xsk.application.AccountApplication;
import org.xsk.infra.db.po.AccountPo;
import org.xsk.infra.endpoint.cmd.CreateAccountCmd;
import org.xsk.infra.endpoint.cmd.CreateSubAccountCmd;
import org.xsk.infra.endpoint.dto.ListAccountDto;
import org.xsk.infra.endpoint.query.ListAccountQuery;
import org.xsk.domain.account.AccountId;
import org.xsk.domain.account.AccountStatus;
import org.xsk.domain.account.Contact;
import org.xsk.domain.account.PhysicalAddress;
import org.xsk.readmodel.AccountReadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {
    final static BiMap<AccountStatus, String> ACCOUNT_STATUS_2_FILED_MAP = new BiMap<>(new HashMap<AccountStatus, String>() {
        {
            put(AccountStatus.DISABLE, "DISABLE");
            put(AccountStatus.ENABLE, "ENABLE");
        }
    });
    AccountApplication accountApplication;
    AccountReadService accountReadService;

    //cmd below
    @PostMapping
    public long createAccount(CreateAccountCmd cmd) {
        AccountPo accountPo = new AccountPo();
        AccountId accountId = accountApplication.create(
                ACCOUNT_STATUS_2_FILED_MAP.getInverse().get(cmd.getStatus()),
                cmd.getName(),
                cmd.getLoginName(),
                cmd.getPassword(),
                new Contact(cmd.getPhone(), cmd.getEmail()),
                new PhysicalAddress(cmd.getCountry(), cmd.getCity(), cmd.getStreet()),
                new AccountId(cmd.getParentAccountId())
        );
        return accountId.value();
    }

    @PostMapping("subaccount")
    public long createSubAccount(CreateSubAccountCmd cmd) {
        AccountId accountId = accountApplication.createdSubAccount(
                new AccountId(cmd.getAccountId()),
                cmd.getName(),
                cmd.getLoginName(),
                cmd.getPassword(),
                new Contact(cmd.getPhone(), cmd.getEmail()),
                new PhysicalAddress(cmd.getCountry(), cmd.getCity(), cmd.getStreet())
        );
        return accountId.value();
    }

    //query below
    @GetMapping("account")
    public List<ListAccountDto> listAccount(ListAccountQuery query) {
        return accountReadService.listAccount(query);
    }
}
