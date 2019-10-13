package com.flag42.ibs.repository;

import com.flag42.ibs.domain.Underwriter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Underwriter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnderwriterRepository extends JpaRepository<Underwriter, Long> {

}
