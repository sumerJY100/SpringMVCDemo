package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H103Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H103Rep extends JpaRepository<H103Pojo,Long> {
   List<H103Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
