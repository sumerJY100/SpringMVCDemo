package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H006Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H006Rep extends JpaRepository<H006Pojo,Long> {
   List<H006Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
