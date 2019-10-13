package com.flag42.ibs.repository;

import com.flag42.ibs.domain.PolicyBenefit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PolicyBenefit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyBenefitRepository extends JpaRepository<PolicyBenefit, Long> {

}
