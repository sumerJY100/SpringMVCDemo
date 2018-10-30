package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H057Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H057Rep extends JpaRepository<H057Pojo,Long> {
   List<H057Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
