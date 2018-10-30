package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H150Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H150Rep extends JpaRepository<H150Pojo,Long> {
   List<H150Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
