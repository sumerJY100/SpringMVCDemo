package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TCm4CoalPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TCm4CoalRepository extends JpaRepository<TCm4CoalPojo,Long> {
}
