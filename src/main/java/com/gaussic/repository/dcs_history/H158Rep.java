package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H158Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H158Rep extends JpaRepository<H158Pojo,Long> {
   List<H158Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
