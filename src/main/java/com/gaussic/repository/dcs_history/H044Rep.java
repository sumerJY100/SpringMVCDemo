package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H044Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H044Rep extends JpaRepository<H044Pojo,Long> {
   List<H044Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
