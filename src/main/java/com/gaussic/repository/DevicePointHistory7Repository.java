package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory4Pojo;
import com.gaussic.model.dcs.DevicePointHistory7Pojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicePointHistory7Repository extends JpaRepository<DevicePointHistory7Pojo,Long> {
}
