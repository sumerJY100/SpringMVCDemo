package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H133Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H133Rep extends JpaRepository<H133Pojo,Long> {
   List<H133Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
