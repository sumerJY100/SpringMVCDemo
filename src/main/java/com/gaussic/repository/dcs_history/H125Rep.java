package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H125Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H125Rep extends JpaRepository<H125Pojo,Long> {
   List<H125Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
