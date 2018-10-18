package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TBeTotalCoalPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TBeToalCoalRepository extends JpaRepository<TBeTotalCoalPojo,Long> {
}
