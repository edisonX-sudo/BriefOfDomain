package org.xsk.readmodel;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xsk.infra.db.mapper.AccountJpaRepo;
import org.xsk.infra.db.po.AccountPo;
import org.xsk.infra.endpoint.dto.ListAccountDto;
import org.xsk.infra.endpoint.query.ListAccountQuery;
import org.xsk.readmodel.common.account.AcctCommonReadService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountReadService implements IAccountReadService {
    final AcctCommonReadService acctCommonReadService;
    final AccountJpaRepo accountJpaRepo;

    @Override
    public List<ListAccountDto> listAccount(ListAccountQuery query) {
        List<AccountPo> accountPos = readFromDb(query);
        return accountPos.stream()
                .map(accountPo -> BeanUtil.copyProperties(accountPo, ListAccountDto.class))
                .collect(Collectors.toList());
    }

    private List<AccountPo> readFromDb(ListAccountQuery query) {
        return accountJpaRepo.findAll();
    }
}
