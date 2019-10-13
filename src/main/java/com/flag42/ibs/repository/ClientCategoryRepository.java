package com.flag42.ibs.repository;

import com.flag42.ibs.domain.ClientCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClientCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientCategoryRepository extends JpaRepository<ClientCategory, Long> {

}
