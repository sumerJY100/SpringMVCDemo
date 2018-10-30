package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H000Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H000Rep extends JpaRepository<H000Pojo,Long> {
   List<H000Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
