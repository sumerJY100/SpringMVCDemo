package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H104Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H104Rep extends JpaRepository<H104Pojo,Long> {
   List<H104Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
