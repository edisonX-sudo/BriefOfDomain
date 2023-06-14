package org.xsk.readmodel;

import org.xsk.infra.endpoint.dto.ListAccountDto;
import org.xsk.infra.endpoint.query.ListAccountQuery;

import java.util.List;

public interface IAccountReadService {
    List<ListAccountDto> listAccount(ListAccountQuery query);
}
