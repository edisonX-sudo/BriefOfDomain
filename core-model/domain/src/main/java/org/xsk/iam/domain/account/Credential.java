package org.xsk.iam.domain.account;

import cn.hutool.crypto.digest.DigestUtil;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

/**
 * 认证登录信息
 */
public class Credential extends ValueObject {
    String loginName;
    String mobile;
    String email;
    String cryptPassword;
    String plainTextPass;

    Credential(String loginName, String mobile, String email, String cryptPassword) {
        this.loginName = loginName;
        this.mobile = mobile;
        this.email = email;
        this.cryptPassword = cryptPassword;
    }

    public static Credential restore(String loginName, String mobile, String email, String cryptPassword) {
        return new Credential(loginName, mobile, email, cryptPassword);
    }

    public static Credential buildCredential(String loginName, String mobile, String email, String plainTextPass) {
        Credential credential = new Credential(loginName, mobile, email, DigestUtil.sha1Hex(plainTextPass));
        credential.plainTextPass = plainTextPass;
        return credential;
    }

    public Credential changePassword(String plaintextPass) {
        Credential credential = cloneObject(this);
        credential.cryptPassword = DigestUtil.sha1Hex(plaintextPass);
        return credential;
    }

    public Credential changeEmail(String email) {
        Credential credential = cloneObject(this);
        credential.email = email;
        return credential;
    }

    public Credential changeMobile(String mobile) {
        Credential credential = cloneObject(this);
        credential.mobile = mobile;
        return credential;
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }

    public boolean comparePassword(String plaintextPass) {
        return DigestUtil.sha1Hex(plaintextPass).equals(plaintextPass);
    }
}
