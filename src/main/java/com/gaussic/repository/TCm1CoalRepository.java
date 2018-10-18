package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TCm1CoalPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TCm1CoalRepository extends JpaRepository<TCm1CoalPojo,Long> {
}
