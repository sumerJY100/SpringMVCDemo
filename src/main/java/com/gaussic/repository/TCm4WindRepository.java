package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TCm4WindPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TCm4WindRepository extends JpaRepository<TCm4WindPojo,Long> {
}
