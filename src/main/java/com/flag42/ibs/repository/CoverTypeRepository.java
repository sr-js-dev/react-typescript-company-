package com.flag42.ibs.repository;

import com.flag42.ibs.domain.CoverType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CoverType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoverTypeRepository extends JpaRepository<CoverType, Long> {

}
