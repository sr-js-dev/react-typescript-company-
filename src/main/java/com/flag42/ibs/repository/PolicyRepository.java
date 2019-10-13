package com.flag42.ibs.repository;

import com.flag42.ibs.domain.Policy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Policy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

}
