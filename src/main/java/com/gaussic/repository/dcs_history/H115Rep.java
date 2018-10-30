package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H115Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H115Rep extends JpaRepository<H115Pojo,Long> {
   List<H115Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
