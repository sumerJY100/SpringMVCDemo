package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H166Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H166Rep extends JpaRepository<H166Pojo,Long> {
   List<H166Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
