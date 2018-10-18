package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory12Pojo;
import com.gaussic.model.dcs.DevicePointHistory4Pojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicePointHistory12Repository extends JpaRepository<DevicePointHistory12Pojo,Long> {
}
