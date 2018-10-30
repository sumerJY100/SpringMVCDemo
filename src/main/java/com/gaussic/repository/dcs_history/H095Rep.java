package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H095Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H095Rep extends JpaRepository<H095Pojo,Long> {
   List<H095Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
