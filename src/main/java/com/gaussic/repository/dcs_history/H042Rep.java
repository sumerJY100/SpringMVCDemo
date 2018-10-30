package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H042Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H042Rep extends JpaRepository<H042Pojo,Long> {
   List<H042Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
