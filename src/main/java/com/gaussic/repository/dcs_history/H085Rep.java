package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H085Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H085Rep extends JpaRepository<H085Pojo,Long> {
   List<H085Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
