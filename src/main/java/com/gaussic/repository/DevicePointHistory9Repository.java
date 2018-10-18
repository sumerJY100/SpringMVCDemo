package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory4Pojo;
import com.gaussic.model.dcs.DevicePointHistory9Pojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicePointHistory9Repository extends JpaRepository<DevicePointHistory9Pojo,Long> {
}
