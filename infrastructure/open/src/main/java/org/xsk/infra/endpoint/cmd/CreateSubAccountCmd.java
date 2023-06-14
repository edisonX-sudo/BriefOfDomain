package org.xsk.infra.endpoint.cmd;

import lombok.Data;

@Data
public class CreateSubAccountCmd {
    Long accountId;
    String status;
    String name;
    String loginName;
    String password;
    String phone;
    String email;
    String country;
    String city;
    String street;
}
