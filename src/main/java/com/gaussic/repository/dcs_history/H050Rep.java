package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H050Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H050Rep extends JpaRepository<H050Pojo,Long> {
   List<H050Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
