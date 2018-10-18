package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory10Pojo;
import com.gaussic.model.dcs.DevicePointHistory4Pojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicePointHistory10Repository extends JpaRepository<DevicePointHistory10Pojo,Long> {
}
