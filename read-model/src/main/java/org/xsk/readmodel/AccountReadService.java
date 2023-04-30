package org.xsk.readmodel;

import cn.hutool.core.bean.BeanUtil;
import org.xsk.infra.endpoint.dto.ListAccountDto;
import org.xsk.infra.endpoint.query.ListAccountQuery;
import org.xsk.infra.db.po.AccountPo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountReadService {
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
