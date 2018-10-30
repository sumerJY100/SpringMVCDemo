package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H060Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H060Rep extends JpaRepository<H060Pojo,Long> {
   List<H060Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
