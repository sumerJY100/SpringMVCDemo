package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TCm2WindPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TCm2WindRepository extends JpaRepository<TCm2WindPojo,Long> {
}
