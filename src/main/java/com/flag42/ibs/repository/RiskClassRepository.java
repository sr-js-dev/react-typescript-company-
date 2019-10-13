package com.flag42.ibs.repository;

import com.flag42.ibs.domain.RiskClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RiskClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskClassRepository extends JpaRepository<RiskClass, Long> {

}
