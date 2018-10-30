package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H099Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H099Rep extends JpaRepository<H099Pojo,Long> {
   List<H099Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
