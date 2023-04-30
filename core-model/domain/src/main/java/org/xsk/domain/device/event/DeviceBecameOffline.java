package org.xsk.domain.device.event;

import org.xsk.domain.common.DomainEvent;
import org.xsk.domain.device.DeviceId;

public class DeviceBecameOffline extends DomainEvent {

    private final DeviceId id;

    public DeviceBecameOffline(DeviceId id) {
        this.id = id;
    }

    public DeviceId id() {
        return id;
    }
}
