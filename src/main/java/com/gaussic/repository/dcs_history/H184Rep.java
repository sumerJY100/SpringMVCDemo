package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H184Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H184Rep extends JpaRepository<H184Pojo,Long> {
   List<H184Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
