package org.xsk.infra.db.po;

import lombok.Data;

@Data
public class AccountPo {
    Long id;
    String status;
    String name;
    String loginName;
    String password;
    String phone;
    String email;
    String country;
    String city;
    String street;
    Long parentAccountId;
    Long createAt;
    Long modifiedAt;
}
