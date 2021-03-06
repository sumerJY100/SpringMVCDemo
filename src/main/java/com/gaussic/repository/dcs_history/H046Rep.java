package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H046Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H046Rep extends JpaRepository<H046Pojo,Long> {
   List<H046Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
