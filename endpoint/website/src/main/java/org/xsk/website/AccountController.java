package org.xsk.website;

import cn.hutool.core.map.BiMap;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xsk.application.AccountApplication;
import org.xsk.domain.account.AccountId;
import org.xsk.domain.account.AccountStatus;
import org.xsk.domain.account.Contact;
import org.xsk.domain.account.PhysicalAddress;
import org.xsk.infra.endpoint.cmd.CreateAccountCmd;
import org.xsk.infra.endpoint.cmd.CreateSubAccountCmd;
import org.xsk.infra.endpoint.dto.ListAccountDto;
import org.xsk.infra.endpoint.query.ListAccountQuery;
import org.xsk.readmodel.IAccountReadService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("account")
@AllArgsConstructor
public class AccountController {
    final static BiMap<AccountStatus, String> ACCOUNT_STATUS_2_FILED_MAP = new BiMap<>(new HashMap<AccountStatus, String>() {
        {
            put(AccountStatus.DISABLE, "DISABLE");
            put(AccountStatus.ENABLE, "ENABLE");
        }
    });
    AccountApplication accountApplication;
    IAccountReadService accountReadService;

    //cmd below
    @PostMapping
    public long createAccount(@RequestBody CreateAccountCmd cmd) {
        AccountId accountId = accountApplication.create(
                ACCOUNT_STATUS_2_FILED_MAP.getInverse().get(cmd.getStatus()),
                cmd.getName(),
                cmd.getLoginName(),
                cmd.getPassword(),
                new Contact(cmd.getPhone(), cmd.getEmail()),
                new PhysicalAddress(cmd.getCountry(), cmd.getCity(), cmd.getStreet())
        );
        return accountId.value();
    }

    @PostMapping("subaccount")
    public long createSubAccount(@RequestBody CreateSubAccountCmd cmd) {
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
    @GetMapping()
    public List<ListAccountDto> listAccount(ListAccountQuery query) {
        return accountReadService.listAccount(query);
    }
}
