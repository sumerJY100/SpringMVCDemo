package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H086Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H086Rep extends JpaRepository<H086Pojo,Long> {
   List<H086Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
