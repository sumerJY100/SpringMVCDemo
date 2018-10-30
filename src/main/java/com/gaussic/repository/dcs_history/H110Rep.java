package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H110Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H110Rep extends JpaRepository<H110Pojo,Long> {
   List<H110Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
