package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TAirLeftPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TAirLeftRepository extends JpaRepository<TAirLeftPojo,Long> {
}
