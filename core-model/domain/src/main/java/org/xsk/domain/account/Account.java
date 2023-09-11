package org.xsk.domain.account;

import cn.hutool.core.util.StrUtil;
import lombok.EqualsAndHashCode;
import org.xsk.domain.account.event.AccountCreated;
import org.xsk.domain.account.exception.AccountPasswordNotCorrect;
import org.xsk.domain.account.exception.OnlyMainAccountOperate;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Entity;
import org.xsk.domain.common.EventBus;
import org.xsk.domain.common.Id;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Account extends Entity<AccountId> {
    @EqualsAndHashCode.Include
    AccountId accountId;
    AccountStatus status;
    String name;
    String loginName;
    String password;
    Contact contact;
    PhysicalAddress address;
    AccountId parentAccountId;

    Account(AccountId accountId, AccountStatus status, String name, String loginName, String password, Contact contact,
            PhysicalAddress address, AccountId parentAccountId, Long createAt, Long modifiedAt) {
        this.accountId = accountId;
        this.status = status;
        this.name = name;
        this.loginName = loginName;
        this.password = password;
        this.contact = contact;
        this.address = address;
        this.parentAccountId = parentAccountId;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
        validSpecification();
    }

    Account(AccountStatus status, String name, String loginName, String password, Contact contact, PhysicalAddress address, AccountId parentAccountId) {
        this(null, status, name, loginName, password, contact, address, parentAccountId, null, null);
    }

    Boolean isMainAccount() {
        return Id.isNullVal(parentAccountId);
    }

    public Account createSubAccount(String name, String loginName, String password, Contact contact, PhysicalAddress address) {
        //业务知识: 主账号才能创建账号
        if (!isMainAccount()) {
            throw new OnlyMainAccountOperate();
        }
        //业务知识: 账号创建默认是disable的
        Account subAccount = new Account(AccountStatus.DISABLE, name, loginName, password, contact, address, accountId);
        EventBus.fire(new AccountCreated(() -> subAccount.accountId, contact));
        return subAccount;
    }

    public void handoverMainPrivilege(Account subAccount, AccountPrivilegeService accountPrivilegeService) {
        accountPrivilegeService.handoverMainPrivilege(this, subAccount);
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!oldPassword.equals(password)) {
            throw new AccountPasswordNotCorrect();
        }
        password = newPassword;
        validSpecification();
    }

    public void update(
            AccountStatus status, String name, String loginName,
            Contact contact, PhysicalAddress address, AccountId parentAccountId
    ) {
        this.status = status;
        this.name = name;
        this.loginName = loginName;
        this.contact = contact;
        this.address = address;
        this.parentAccountId = parentAccountId;
        validSpecification();
    }

    @Override
    public AccountId id() {
        return accountId;
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnCondition(StrUtil.length(Account.this.name) > 50, "account name length cant be greater than 50");
                throwOnCondition(Account.this.status == null, "account status cant be null");
            }
        };
    }
}
