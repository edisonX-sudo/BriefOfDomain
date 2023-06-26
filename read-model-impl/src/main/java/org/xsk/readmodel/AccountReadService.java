package org.xsk.readmodel;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;
import org.xsk.infra.db.po.AccountPo;
import org.xsk.infra.endpoint.dto.ListAccountDto;
import org.xsk.infra.endpoint.query.ListAccountQuery;
import org.xsk.readmodel.common.account.AcctCommonReadService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountReadService implements IAccountReadService {
    AcctCommonReadService acctCommonReadService;
    @Override
    public List<ListAccountDto> listAccount(ListAccountQuery query) {
        List<AccountPo> accountPos = readFromDb(query);
        return accountPos.stream()
                .map(accountPo -> BeanUtil.copyProperties(accountPo, ListAccountDto.class))
                .collect(Collectors.toList());
    }

    private List<AccountPo> readFromDb(ListAccountQuery query) {
        // TODO: 2023/4/14 query db directly
        return Collections.emptyList();
    }
}
