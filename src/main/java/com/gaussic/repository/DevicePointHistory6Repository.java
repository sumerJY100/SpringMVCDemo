package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory4Pojo;
import com.gaussic.model.dcs.DevicePointHistory6Pojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicePointHistory6Repository extends JpaRepository<DevicePointHistory6Pojo,Long> {
}
