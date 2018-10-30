package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H001Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H001Rep extends JpaRepository<H001Pojo,Long> {
   List<H001Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
