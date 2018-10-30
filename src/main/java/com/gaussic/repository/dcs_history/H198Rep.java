package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H198Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H198Rep extends JpaRepository<H198Pojo,Long> {
   List<H198Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
