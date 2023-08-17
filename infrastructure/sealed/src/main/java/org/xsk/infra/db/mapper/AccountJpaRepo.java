package org.xsk.infra.db.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.xsk.infra.db.po.AccountPo;

import java.util.List;

public interface AccountJpaRepo extends JpaRepository<AccountPo, Long>, JpaSpecificationExecutor<AccountPo> {
    List<AccountPo> findByParentAccountId(Long parentAccountId);
    AccountPo findByLoginName(String loginName);
    AccountPo findByName(String name);
}
