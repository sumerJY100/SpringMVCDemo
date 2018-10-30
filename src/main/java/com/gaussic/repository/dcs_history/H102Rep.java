package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H102Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H102Rep extends JpaRepository<H102Pojo,Long> {
   List<H102Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
