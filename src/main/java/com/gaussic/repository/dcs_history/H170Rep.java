package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.H170Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
    @Repository
 public interface H170Rep extends JpaRepository<H170Pojo,Long> {
   List<H170Pojo> findByVTimeBetween(Timestamp begin, Timestamp end);
 }
