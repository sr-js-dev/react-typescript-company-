package com.flag42.ibs.repository;

import com.flag42.ibs.domain.IdType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IdType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdTypeRepository extends JpaRepository<IdType, Long> {

}
