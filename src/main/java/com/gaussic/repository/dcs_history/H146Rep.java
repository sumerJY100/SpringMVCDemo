package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H146Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H146Rep extends JpaRepository<H146Pojo,Long> {
   List<H146Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
