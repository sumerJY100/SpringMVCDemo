package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TBeTotalFirstWindPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TBeFirstWindRepository extends JpaRepository<TBeTotalFirstWindPojo,Long> {
}
