package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H108Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H108Rep extends JpaRepository<H108Pojo,Long> {
   List<H108Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
