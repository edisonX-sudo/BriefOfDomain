package org.xsk.iam.domain.account;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.UniqueKey;
import org.xsk.domain.common.ValueObject;
import org.xsk.iam.domain.app.AppCode;

public class AppUidUniqueKey extends UniqueKey<AppUidUniqueKey.AppUid> {

    public AppUidUniqueKey(AppUid id) {
        super(id);
    }

    public AppUidUniqueKey(AppCode appCode, Uid uid) {
        super(new AppUid(appCode, uid));
    }


    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }

    public static class AppUid extends ValueObject {
        AppCode appCode;
        Uid uid;

        public AppUid(AppCode appCode, Uid uid) {
            this.appCode = appCode;
            this.uid = uid;
        }

        @Override
        protected DomainSpecificationValidator specificationValidator() {
            return null;
        }

        public AppCode appCode() {
            return appCode;
        }

        public Uid uid() {
            return uid;
        }
    }
}