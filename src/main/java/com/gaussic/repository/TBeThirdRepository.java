package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TBeTotalCoalPojo;
import com.gaussic.model.dcsTemp.TBeTotalThirdWindPojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TBeThirdRepository extends JpaRepository<TBeTotalThirdWindPojo,Long> {
}
