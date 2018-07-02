package com.gaussic.repository;

import com.gaussic.model.history.AcoalPipingHistoryEntity;
import com.gaussic.model.history.DcoalPipingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoalPipingHistoryRepositoryD extends JpaRepository<DcoalPipingHistoryEntity,Long> {
}
