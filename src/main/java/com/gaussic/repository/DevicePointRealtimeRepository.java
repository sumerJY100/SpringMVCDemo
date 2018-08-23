package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory1Pojo;
import com.gaussic.model.dcs.DevicePointPojo;
import com.gaussic.model.dcs.DevicePointRealtimePojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevicePointRealtimeRepository extends JpaRepository<DevicePointRealtimePojo,Long> {
}
