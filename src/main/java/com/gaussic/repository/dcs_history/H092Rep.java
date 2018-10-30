package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H092Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H092Rep extends JpaRepository<H092Pojo,Long> {
   List<H092Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
