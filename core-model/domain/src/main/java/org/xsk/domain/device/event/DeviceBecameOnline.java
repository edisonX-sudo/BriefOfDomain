package org.xsk.domain.device.event;

import org.xsk.domain.common.DomainEvent;
import org.xsk.domain.device.DeviceId;

public class DeviceBecameOnline extends DomainEvent {
    private final DeviceId id;
    private final DeviceId parentId;

    public DeviceBecameOnline(DeviceId id, DeviceId parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public DeviceId id() {
        return id;
    }

    public DeviceId parentId() {
        return parentId;
    }
}
