package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H013Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H013Rep extends JpaRepository<H013Pojo,Long> {
   List<H013Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
