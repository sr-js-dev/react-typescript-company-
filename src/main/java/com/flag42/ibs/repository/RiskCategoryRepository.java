package com.flag42.ibs.repository;

import com.flag42.ibs.domain.RiskCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RiskCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskCategoryRepository extends JpaRepository<RiskCategory, Long> {

}
