package org.xsk.application;

import org.springframework.stereotype.Service;
import org.xsk.domain.common.DomainApplication;
import org.xsk.domain.device.Device;
import org.xsk.domain.device.DeviceId;
import org.xsk.domain.device.DeviceRepo;

@Service
public class DeviceApplication extends DomainApplication {
    DeviceRepo deviceRepo;

    public void online(DeviceId deviceId) {
        Device device = deviceRepo.find(deviceId);
        device.online();
        deviceRepo.save(device);
    }

}
