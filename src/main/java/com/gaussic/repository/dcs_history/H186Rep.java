package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H186Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H186Rep extends JpaRepository<H186Pojo,Long> {
   List<H186Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
