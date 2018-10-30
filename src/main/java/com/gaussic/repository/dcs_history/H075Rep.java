package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H075Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H075Rep extends JpaRepository<H075Pojo,Long> {
   List<H075Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
