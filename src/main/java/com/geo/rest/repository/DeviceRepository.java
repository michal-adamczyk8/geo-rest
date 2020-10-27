package com.geo.rest.repository;

import com.geo.rest.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device getByDeviceId(Long deviceId);
}
