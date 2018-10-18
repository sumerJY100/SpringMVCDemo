package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TCm2CoalPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TCm2CoalRepository extends JpaRepository<TCm2CoalPojo,Long> {
}
