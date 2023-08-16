package org.xsk.domain.account;

import cn.hutool.core.util.StrUtil;
import org.xsk.domain.account.event.AccountCreated;
import org.xsk.domain.account.exception.AccountPasswordNotCorrect;
import org.xsk.domain.account.exception.OnlyMainAccountOperate;
import org.xsk.domain.common.AggregateComponent;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Entity;
import org.xsk.domain.common.EventBus;

public class Account extends Entity<AccountId> {
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
        this.status = status;
        this.name = name;
        this.loginName = loginName;
        this.password = password;
        this.contact = contact;
        this.address = address;
        this.parentAccountId = parentAccountId;
        validSpecification();
    }

    Boolean isMainAccount() {
        return parentAccountId == null;
    }

    public Account createSubAccount(String name, String loginName, String password, Contact contact, PhysicalAddress address) {
        //业务知识: 主账号才能创建账号
        if (!isMainAccount()) {
            throw new OnlyMainAccountOperate();
        }
        //业务知识: 账号创建默认是disable的
        Account subAccount = new Account(AccountStatus.DISABLE, name, loginName, password, contact, address, this.parentAccountId);
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
    ){
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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected DomainSpecificationValidator<? extends AggregateComponent> specificationValidator() {
        return new DomainSpecificationValidator<Account>(this) {
            @Override
            protected void validSpecification() {
                throwIllegalStateException(StrUtil.length(object.name) > 50, "account name length cant be greater than 50");
                throwIllegalStateException(object.status != null, "account status cant be null");
            }
        };
    }
}
