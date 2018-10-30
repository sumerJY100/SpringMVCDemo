package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H183Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H183Rep extends JpaRepository<H183Pojo,Long> {
   List<H183Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
