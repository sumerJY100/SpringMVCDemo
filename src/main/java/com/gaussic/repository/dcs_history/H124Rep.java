package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H124Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H124Rep extends JpaRepository<H124Pojo,Long> {
   List<H124Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
