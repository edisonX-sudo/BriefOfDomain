package org.xsk.domain.account;

import cn.hutool.core.map.BiMap;
import lombok.AllArgsConstructor;
import org.xsk.domain.account.exception.AccountNotFound;
import org.xsk.domain.common.NotFoundEntityDomainException;
import org.xsk.infra.db.po.AccountPo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AccountRepoImpl extends AccountRepo {
    final static BiMap<AccountStatus, String> ACCOUNT_STATUS_2_FILED_MAP = new BiMap<>(new HashMap<AccountStatus, String>() {
        {
            put(AccountStatus.DISABLE, "DISABLE");
            put(AccountStatus.ENABLE, "ENABLE");
        }
    });

    @Override
    public Account find(String loginName) {
        AccountPo byLoginName = findPoByLoginName(loginName);
        if (byLoginName == null)
            return null;
        return convert2Entity(byLoginName);
    }

    private AccountPo findPoByLoginName(String loginName) {
        // TODO: 2023/4/14 sql find by loginName
        return null;
    }

    @Override
    public List<Account> findAccountGroup(AccountId mainAccountId) {
        List<AccountPo> byLoginName = findPoByMainAccountId(mainAccountId);
        return byLoginName.stream()
                .map(this::convert2Entity)
                .collect(Collectors.toList());
    }

    private List<AccountPo> findPoByMainAccountId(AccountId mainAccountId) {
        // TODO: 2023/4/14 sql find by parentAccountId
        return Collections.emptyList();
    }

    @Override
    protected Account findInternal(AccountId id) {
        AccountPo byId = findPoById(id);
        if (byId == null)
            return null;
        return convert2Entity(byId);
    }

    private AccountPo findPoById(AccountId id) {
        // TODO: 2023/4/14 sql find by id
        return null;
    }

    @Override
    protected void saveAllInternal(Set<Account> entities) {
        // TODO: 2023/6/25
    }

    @Override
    public void saveInternal(Account entity) {
        AccountPo accountPo = convert2po(entity);
        if (isNewEntity(entity)) {
            insertPo(accountPo);
            entity.accountId = new AccountId(accountPo.getId());
        } else {
            updatePo(accountPo);
        }
    }

    private void insertPo(AccountPo accountPo) {
        // TODO: 2023/4/14 sql insert
        System.out.println(accountPo);
        accountPo.setId(1L);
    }

    private void updatePo(AccountPo accountPo) {
        // TODO: 2023/4/14 sql update
        System.out.println(accountPo);
    }

    @Override
    protected NotFoundEntityDomainException notFoundException() {
        return new AccountNotFound();
    }

    private Account convert2Entity(AccountPo po) {
        return new Account(
                new AccountId(po.getId()),
                ACCOUNT_STATUS_2_FILED_MAP.getInverse().get(po.getStatus()),
                po.getName(),
                po.getLoginName(),
                po.getPassword(),
                new Contact(po.getPhone(), po.getEmail()),
                new PhysicalAddress(po.getCountry(), po.getCity(), po.getStreet()),
                new AccountId(po.getParentAccountId()),
                po.getCreateAt(),
                po.getModifiedAt()
        );
    }

    private AccountPo convert2po(Account entity) {
        AccountPo po = new AccountPo();
        po.setId(isNewEntity(entity) ? null : entity.accountId.value());
        po.setStatus(ACCOUNT_STATUS_2_FILED_MAP.get(entity.status));
        po.setName(entity.name);
        po.setLoginName(entity.loginName);
        po.setPassword(entity.password);
        po.setPhone(entity.contact.phone);
        po.setEmail(entity.contact.email);
        po.setCountry(entity.address.country);
        po.setCity(entity.address.city);
        po.setStreet(entity.address.street);
        po.setParentAccountId(entity.parentAccountId.value());
        po.setCreateAt(entity.createAt());
        po.setModifiedAt(entity.modifiedAt());
        return po;
    }
}
