package com.gaussic.repository;

import com.gaussic.model.dcs.DevicePointHistory1Pojo;
import com.gaussic.model.dcs.DevicePointPojo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevicePointRepository extends JpaRepository<DevicePointPojo,Long> , JpaSpecificationExecutor<DevicePointPojo> {
    List<DevicePointPojo> findByPointAddress(String s);
    List<DevicePointPojo> findByPointNameNotNull();
    List<DevicePointPojo> findByPointNameNotLike(String notLike);

//    Page<DevicePointPojo> findAllByPointNameNotLike(Specification<DevicePointPojo> specification, Pageable pageable);
}
