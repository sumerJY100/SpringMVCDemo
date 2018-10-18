package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory4Pojo;
import com.gaussic.model.dcs.DevicePointHistory8Pojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicePointHistory8Repository extends JpaRepository<DevicePointHistory8Pojo,Long> {
}
