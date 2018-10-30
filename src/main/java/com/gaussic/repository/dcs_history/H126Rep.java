package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H126Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H126Rep extends JpaRepository<H126Pojo,Long> {
   List<H126Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
