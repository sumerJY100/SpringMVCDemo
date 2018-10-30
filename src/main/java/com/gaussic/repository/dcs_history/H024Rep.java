package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H024Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H024Rep extends JpaRepository<H024Pojo,Long> {
   List<H024Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
