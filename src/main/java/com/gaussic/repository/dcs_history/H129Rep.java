package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H129Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H129Rep extends JpaRepository<H129Pojo,Long> {
   List<H129Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
