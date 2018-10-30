package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H062Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H062Rep extends JpaRepository<H062Pojo,Long> {
   List<H062Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
