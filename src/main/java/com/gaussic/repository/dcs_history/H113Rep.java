package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H113Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H113Rep extends JpaRepository<H113Pojo,Long> {
   List<H113Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
