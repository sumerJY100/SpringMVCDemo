package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H127Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H127Rep extends JpaRepository<H127Pojo,Long> {
   List<H127Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
