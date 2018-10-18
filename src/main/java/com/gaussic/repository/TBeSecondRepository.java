package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TBeTotalSecondWindPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TBeSecondRepository extends JpaRepository<TBeTotalSecondWindPojo,Long> {
}
