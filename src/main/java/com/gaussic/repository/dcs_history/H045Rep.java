package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H045Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H045Rep extends JpaRepository<H045Pojo,Long> {
   List<H045Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
