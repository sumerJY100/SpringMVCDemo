package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H155Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H155Rep extends JpaRepository<H155Pojo,Long> {
   List<H155Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
