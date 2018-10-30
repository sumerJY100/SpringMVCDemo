package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H136Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H136Rep extends JpaRepository<H136Pojo,Long> {
   List<H136Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
