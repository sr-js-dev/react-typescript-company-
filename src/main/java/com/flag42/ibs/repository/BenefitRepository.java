package com.flag42.ibs.repository;

import com.flag42.ibs.domain.Benefit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Benefit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {

}
