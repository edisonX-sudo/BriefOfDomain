package org.xsk.domain.device;

import lombok.AllArgsConstructor;
import org.xsk.domain.common.DomainPolicy;
import org.xsk.domain.device.event.DeviceBecameOffline;

import java.util.List;

@AllArgsConstructor
public class SubDeviceOfflineCascadeUpdatePolicy extends DomainPolicy<DeviceBecameOffline> {
    DeviceRepo deviceRepo;

    @Override
    public void subscribe(DeviceBecameOffline event) {
        DeviceId deviceId = event.id();
        List<Device> subDevices = deviceRepo.findSubDevices(deviceId);
        subDevices.forEach(Device::offline);
        deviceRepo.saveAll(subDevices);
    }

    @Override
    protected Class<DeviceBecameOffline> eventClass() {
        return DeviceBecameOffline.class;
    }
}
