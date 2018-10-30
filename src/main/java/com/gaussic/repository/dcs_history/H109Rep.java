package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H109Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H109Rep extends JpaRepository<H109Pojo,Long> {
   List<H109Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
