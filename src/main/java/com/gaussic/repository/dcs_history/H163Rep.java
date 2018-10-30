package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H163Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H163Rep extends JpaRepository<H163Pojo,Long> {
   List<H163Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
