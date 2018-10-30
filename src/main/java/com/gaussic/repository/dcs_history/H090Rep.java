package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H090Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H090Rep extends JpaRepository<H090Pojo,Long> {
   List<H090Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
