package com.flag42.ibs.repository;

import com.flag42.ibs.domain.NameTitle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NameTitle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NameTitleRepository extends JpaRepository<NameTitle, Long> {

}
