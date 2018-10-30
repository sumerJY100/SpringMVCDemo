package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H019Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H019Rep extends JpaRepository<H019Pojo,Long> {
   List<H019Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
