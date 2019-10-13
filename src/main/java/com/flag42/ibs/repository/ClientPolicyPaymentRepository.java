package com.flag42.ibs.repository;

import com.flag42.ibs.domain.ClientPolicyPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClientPolicyPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientPolicyPaymentRepository extends JpaRepository<ClientPolicyPayment, Long> {

}
