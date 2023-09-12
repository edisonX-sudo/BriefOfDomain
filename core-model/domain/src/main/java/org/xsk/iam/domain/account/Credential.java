package org.xsk.iam.domain.account;

import cn.hutool.core.util.RandomUtil;
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

    static Credential restore(String loginName, String mobile, String email, String cryptPassword) {
        return new Credential(loginName, mobile, email, cryptPassword);
    }

    static Credential buildCredential(String loginName, String mobile, String email, String plainTextPass) {
        Credential credential = new Credential(loginName, mobile, email, DigestUtil.sha1Hex(plainTextPass));
        credential.plainTextPass = plainTextPass;
        return credential;
    }

    Credential changePassword(String plaintextPass) {
        Credential credential = beginUpdate(this);
        credential.cryptPassword = DigestUtil.sha1Hex(plaintextPass);
        return credential;
    }

    Credential changeEmail(String email) {
        Credential credential = beginUpdate(this);
        credential.email = email;
        return credential;
    }

    Credential changeMobile(String mobile) {
        Credential credential = beginUpdate(this);
        credential.mobile = mobile;
        return credential;
    }

    boolean comparePassword(String plaintextPass) {
        return DigestUtil.sha1Hex(plaintextPass).equals(plaintextPass);
    }

    Credential resetPassword() {
        Credential credential = beginUpdate(this);
        String plainPass = RandomUtil.randomString(10);
        credential.plainTextPass = plainPass;
        credential.cryptPassword = DigestUtil.sha1Hex(plainPass);
        return credential;
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }


}
