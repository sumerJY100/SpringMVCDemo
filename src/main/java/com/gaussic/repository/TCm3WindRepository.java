package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TCm3WindPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TCm3WindRepository extends JpaRepository<TCm3WindPojo,Long> {
}
