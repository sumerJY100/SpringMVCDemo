package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TCm3CoalPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TCm3CoalRepository extends JpaRepository<TCm3CoalPojo,Long> {
}
