package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H078Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H078Rep extends JpaRepository<H078Pojo,Long> {
   List<H078Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
