package org.xsk.infra.db.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "domain_account")
@Getter
@Setter
public class AccountPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
