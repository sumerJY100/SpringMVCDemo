package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H082Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H082Rep extends JpaRepository<H082Pojo,Long> {
   List<H082Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
