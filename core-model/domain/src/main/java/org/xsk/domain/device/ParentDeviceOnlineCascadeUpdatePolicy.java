package org.xsk.domain.device;

import lombok.AllArgsConstructor;
import org.xsk.domain.common.DomainPolicy;
import org.xsk.domain.device.event.DeviceBecameOnline;

@AllArgsConstructor
public class ParentDeviceOnlineCascadeUpdatePolicy extends DomainPolicy<DeviceBecameOnline> {
    DeviceRepo deviceRepo;

    @Override
    public void subscribe(DeviceBecameOnline event) {
        DeviceId parentDeviceId = event.parentId();
        Device parentDevice = deviceRepo.find(parentDeviceId);
        parentDevice.online();
        deviceRepo.save(parentDevice);
    }

    protected Class<DeviceBecameOnline> eventClass() {
        return DeviceBecameOnline.class;
    }
}
