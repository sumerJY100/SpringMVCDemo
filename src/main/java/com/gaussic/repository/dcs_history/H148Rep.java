package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H148Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H148Rep extends JpaRepository<H148Pojo,Long> {
   List<H148Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
