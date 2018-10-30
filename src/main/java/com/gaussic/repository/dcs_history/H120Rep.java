package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H120Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H120Rep extends JpaRepository<H120Pojo,Long> {
   List<H120Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
