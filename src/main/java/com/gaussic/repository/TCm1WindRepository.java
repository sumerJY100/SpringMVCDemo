package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TCm1WindPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TCm1WindRepository extends JpaRepository<TCm1WindPojo,Long> {
}
