package com.flag42.ibs.repository;

import com.flag42.ibs.domain.BenefitType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BenefitType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenefitTypeRepository extends JpaRepository<BenefitType, Long> {

}
