package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H121Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H121Rep extends JpaRepository<H121Pojo,Long> {
   List<H121Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
