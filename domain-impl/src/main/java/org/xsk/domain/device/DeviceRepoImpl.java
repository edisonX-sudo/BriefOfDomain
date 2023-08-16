package org.xsk.domain.device;

import org.xsk.domain.common.NotFoundEntityDomainException;

import java.util.List;

public class DeviceRepoImpl extends DeviceRepo{
    @Override
    public List<Device> findSubDevices(DeviceId parentId) {
        return null;
    }

    @Override
    public void saveAll(List<Device> subDevices) {

    }

    @Override
    protected Device findInternal(DeviceId id) {
        return null;
    }

    @Override
    protected NotFoundEntityDomainException notFoundException() {
        return null;
    }

    @Override
    protected void saveInternal(Device entity) {

    }
}
