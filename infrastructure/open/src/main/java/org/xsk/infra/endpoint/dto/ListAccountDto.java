package org.xsk.infra.endpoint.dto;

import lombok.Data;

@Data
public class ListAccountDto {
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
}
