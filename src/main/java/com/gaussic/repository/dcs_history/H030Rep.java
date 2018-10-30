package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H030Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H030Rep extends JpaRepository<H030Pojo,Long> {
   List<H030Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
