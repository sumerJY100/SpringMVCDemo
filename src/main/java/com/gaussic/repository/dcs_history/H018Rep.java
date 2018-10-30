package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H018Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H018Rep extends JpaRepository<H018Pojo,Long> {
   List<H018Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
