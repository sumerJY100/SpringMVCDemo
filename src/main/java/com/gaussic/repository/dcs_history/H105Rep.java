package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H105Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H105Rep extends JpaRepository<H105Pojo,Long> {
   List<H105Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
