package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.DcsHistoryCashePojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface DcsHistoryCasheRep extends JpaRepository<DcsHistoryCashePojo,Long> {


     List<DcsHistoryCashePojo> findAllByOrderByVTimeDesc();

    List<DcsHistoryCashePojo> findByVTimeAfter(Timestamp afterTime);

    List<DcsHistoryCashePojo> findByVTimeBetween(Timestamp begin,Timestamp end);
}
