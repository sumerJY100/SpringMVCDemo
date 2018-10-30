package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H070Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H070Rep extends JpaRepository<H070Pojo,Long> {
   List<H070Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
