package com.flag42.ibs.repository;

import com.flag42.ibs.domain.ClientPolicy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClientPolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientPolicyRepository extends JpaRepository<ClientPolicy, Long> {

}
