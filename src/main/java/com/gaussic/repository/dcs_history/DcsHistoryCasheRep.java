package com.gaussic.repository.dcs_history;

import com.gaussic.model.dcs_history.DcsHistoryCashePojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DcsHistoryCasheRep extends JpaRepository<DcsHistoryCashePojo,Long> {

}
