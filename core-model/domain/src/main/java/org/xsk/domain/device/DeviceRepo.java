package org.xsk.domain.device;

import org.xsk.domain.common.Repository;

import java.util.List;

public abstract class DeviceRepo extends Repository<Device, DeviceId> {
    public abstract List<Device> findSubDevices(DeviceId parentId);

    public abstract void saveAll(List<Device> subDevices);
}
