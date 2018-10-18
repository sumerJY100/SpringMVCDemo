package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory5Pojo;
import com.gaussic.model.dcsTemp.TBePojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TBeRepository extends JpaRepository<TBePojo,Long> {
}
