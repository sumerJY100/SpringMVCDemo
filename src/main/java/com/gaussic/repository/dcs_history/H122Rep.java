package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H122Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H122Rep extends JpaRepository<H122Pojo,Long> {
   List<H122Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
