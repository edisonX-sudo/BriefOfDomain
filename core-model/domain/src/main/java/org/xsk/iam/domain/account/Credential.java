package org.xsk.iam.domain.account;

import cn.hutool.core.lang.id.NanoId;
import cn.hutool.crypto.digest.DigestUtil;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

public class Credential extends ValueObject {
    String loginName;
    String mobile;
    String email;
    String cryptPassword;

    Credential(String loginName, String mobile, String email, String cryptPassword) {
        this.loginName = loginName;
        this.mobile = mobile;
        this.email = email;
        this.cryptPassword = cryptPassword;
    }

    public static Credential buildRandPassCredential(String loginName, String mobile, String email) {
        String randPass = NanoId.randomNanoId(10);
        return buildCredential(loginName, mobile, email, randPass);
    }

    public static Credential buildCredential(String loginName, String mobile, String email, String plainTextPass) {
        return new Credential(loginName, mobile, email, DigestUtil.sha1Hex(plainTextPass));
    }

    public Credential changePassword(String plaintextPass) {
        return new Credential(loginName, mobile, email, DigestUtil.sha1Hex(plaintextPass));
    }

    public Credential changeEmail(String email) {
        return new Credential(loginName, mobile, email, cryptPassword);
    }

    public Credential changeMobile(String mobile) {
        return new Credential(loginName, mobile, email, cryptPassword);
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }

    public boolean comparePassword(String plaintextPass) {
        return DigestUtil.sha1Hex(plaintextPass).equals(plaintextPass);
    }
}
