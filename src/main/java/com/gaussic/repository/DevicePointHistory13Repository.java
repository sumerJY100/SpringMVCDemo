package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory13Pojo;
import com.gaussic.model.dcs.DevicePointHistory4Pojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicePointHistory13Repository extends JpaRepository<DevicePointHistory13Pojo,Long> {
}
