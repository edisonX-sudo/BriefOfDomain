package org.xsk.domain.device;

import org.xsk.domain.common.DomainPolicy;
import org.xsk.domain.device.event.DeviceBecameOffline;

import java.util.List;

public class SubDeviceOfflineCascadeUpdatePolicy extends DomainPolicy<DeviceBecameOffline> {
    DeviceRepo deviceRepo;

    @Override
    public void subscribe(DeviceBecameOffline event) {
        DeviceId deviceId = event.id();
        List<Device> subDevices = deviceRepo.findSubDevices(deviceId);
        subDevices.forEach(Device::offline);
        deviceRepo.saveAll(subDevices);
    }
}
