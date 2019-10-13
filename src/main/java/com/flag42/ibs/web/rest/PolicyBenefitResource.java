package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.PolicyBenefitService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.PolicyBenefitDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.flag42.ibs.domain.PolicyBenefit}.
 */
@RestController
@RequestMapping("/api")
public class PolicyBenefitResource {

    private final Logger log = LoggerFactory.getLogger(PolicyBenefitResource.class);

    private static final String ENTITY_NAME = "policyBenefit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyBenefitService policyBenefitService;

    public PolicyBenefitResource(PolicyBenefitService policyBenefitService) {
        this.policyBenefitService = policyBenefitService;
    }

    /**
     * {@code POST  /policy-benefits} : Create a new policyBenefit.
     *
     * @param policyBenefitDTO the policyBenefitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policyBenefitDTO, or with status {@code 400 (Bad Request)} if the policyBenefit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policy-benefits")
    public ResponseEntity<PolicyBenefitDTO> createPolicyBenefit(@Valid @RequestBody PolicyBenefitDTO policyBenefitDTO) throws URISyntaxException {
        log.debug("REST request to save PolicyBenefit : {}", policyBenefitDTO);
        if (policyBenefitDTO.getId() != null) {
            throw new BadRequestAlertException("A new policyBenefit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PolicyBenefitDTO result = policyBenefitService.save(policyBenefitDTO);
        return ResponseEntity.created(new URI("/api/policy-benefits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policy-benefits} : Updates an existing policyBenefit.
     *
     * @param policyBenefitDTO the policyBenefitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyBenefitDTO,
     * or with status {@code 400 (Bad Request)} if the policyBenefitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policyBenefitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policy-benefits")
    public ResponseEntity<PolicyBenefitDTO> updatePolicyBenefit(@Valid @RequestBody PolicyBenefitDTO policyBenefitDTO) throws URISyntaxException {
        log.debug("REST request to update PolicyBenefit : {}", policyBenefitDTO);
        if (policyBenefitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PolicyBenefitDTO result = policyBenefitService.save(policyBenefitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, policyBenefitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /policy-benefits} : get all the policyBenefits.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policyBenefits in body.
     */
    @GetMapping("/policy-benefits")
    public ResponseEntity<List<PolicyBenefitDTO>> getAllPolicyBenefits(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of PolicyBenefits");
        Page<PolicyBenefitDTO> page = policyBenefitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /policy-benefits/:id} : get the "id" policyBenefit.
     *
     * @param id the id of the policyBenefitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policyBenefitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/policy-benefits/{id}")
    public ResponseEntity<PolicyBenefitDTO> getPolicyBenefit(@PathVariable Long id) {
        log.debug("REST request to get PolicyBenefit : {}", id);
        Optional<PolicyBenefitDTO> policyBenefitDTO = policyBenefitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(policyBenefitDTO);
    }

    /**
     * {@code DELETE  /policy-benefits/:id} : delete the "id" policyBenefit.
     *
     * @param id the id of the policyBenefitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policy-benefits/{id}")
    public ResponseEntity<Void> deletePolicyBenefit(@PathVariable Long id) {
        log.debug("REST request to delete PolicyBenefit : {}", id);
        policyBenefitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
