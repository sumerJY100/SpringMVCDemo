package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H153Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H153Rep extends JpaRepository<H153Pojo,Long> {
   List<H153Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
