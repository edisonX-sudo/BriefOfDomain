package org.xsk.infra.endpoint.cmd;

import lombok.Data;

@Data
public class CreateAccountCmd {
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
}
