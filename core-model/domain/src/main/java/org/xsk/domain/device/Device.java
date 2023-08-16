package org.xsk.domain.device;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Entity;
import org.xsk.domain.common.EventBus;
import org.xsk.domain.device.event.DeviceBecameOffline;
import org.xsk.domain.device.event.DeviceBecameOnline;

public class Device extends Entity<DeviceId> {
    DeviceId id;
    DeviceStatus status;
    DeviceId parentId;

    public void online() {
        status = DeviceStatus.ON_LINE;
        EventBus.fire(new DeviceBecameOnline(id, parentId));
    }

    public void offline() {
        status = DeviceStatus.OFF_LINE;
        EventBus.fire(new DeviceBecameOffline(id));
    }

    @Override
    public DeviceId id() {
        return id;
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }
}
