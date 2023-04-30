package org.xsk.domain.device;

import org.xsk.domain.common.DomainRepository;

import java.util.List;

public abstract class DeviceRepo extends DomainRepository<Device, DeviceId> {
    public abstract List<Device> findSubDevices(DeviceId parentId);

    public abstract void saveAll(List<Device> subDevices);
}
