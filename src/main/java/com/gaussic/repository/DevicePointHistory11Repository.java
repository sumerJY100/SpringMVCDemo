package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory11Pojo;
import com.gaussic.model.dcs.DevicePointHistory4Pojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicePointHistory11Repository extends JpaRepository<DevicePointHistory11Pojo,Long> {
}
