package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H159Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H159Rep extends JpaRepository<H159Pojo,Long> {
   List<H159Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
