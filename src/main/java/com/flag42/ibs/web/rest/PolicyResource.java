package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.PolicyService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.PolicyDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.Policy}.
 */
@RestController
@RequestMapping("/api")
public class PolicyResource {

    private final Logger log = LoggerFactory.getLogger(PolicyResource.class);

    private static final String ENTITY_NAME = "policy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyService policyService;

    public PolicyResource(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * {@code POST  /policies} : Create a new policy.
     *
     * @param policyDTO the policyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policyDTO, or with status {@code 400 (Bad Request)} if the policy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policies")
    public ResponseEntity<PolicyDTO> createPolicy(@Valid @RequestBody PolicyDTO policyDTO) throws URISyntaxException {
        log.debug("REST request to save Policy : {}", policyDTO);
        if (policyDTO.getId() != null) {
            throw new BadRequestAlertException("A new policy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PolicyDTO result = policyService.save(policyDTO);
        return ResponseEntity.created(new URI("/api/policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policies} : Updates an existing policy.
     *
     * @param policyDTO the policyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyDTO,
     * or with status {@code 400 (Bad Request)} if the policyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policies")
    public ResponseEntity<PolicyDTO> updatePolicy(@Valid @RequestBody PolicyDTO policyDTO) throws URISyntaxException {
        log.debug("REST request to update Policy : {}", policyDTO);
        if (policyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PolicyDTO result = policyService.save(policyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, policyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /policies} : get all the policies.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policies in body.
     */
    @GetMapping("/policies")
    public ResponseEntity<List<PolicyDTO>> getAllPolicies(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Policies");
        Page<PolicyDTO> page = policyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /policies/:id} : get the "id" policy.
     *
     * @param id the id of the policyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/policies/{id}")
    public ResponseEntity<PolicyDTO> getPolicy(@PathVariable Long id) {
        log.debug("REST request to get Policy : {}", id);
        Optional<PolicyDTO> policyDTO = policyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(policyDTO);
    }

    /**
     * {@code DELETE  /policies/:id} : delete the "id" policy.
     *
     * @param id the id of the policyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policies/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        log.debug("REST request to delete Policy : {}", id);
        policyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
