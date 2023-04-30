package org.xsk.domain.device;

import org.xsk.domain.common.DomainPolicy;
import org.xsk.domain.device.event.DeviceBecameOnline;

public class ParentDeviceOnlineCascadeUpdatePolicy extends DomainPolicy {
    DeviceRepo deviceRepo;

    public void subscribe(DeviceBecameOnline event) {
        DeviceId parentDeviceId = event.parentId();
        Device parentDevice = deviceRepo.find(parentDeviceId);
        parentDevice.online();
        deviceRepo.save(parentDevice);
    }
}
