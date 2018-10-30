package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H009Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H009Rep extends JpaRepository<H009Pojo,Long> {
   List<H009Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
