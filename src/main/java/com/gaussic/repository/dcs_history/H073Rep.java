package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H073Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H073Rep extends JpaRepository<H073Pojo,Long> {
   List<H073Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
