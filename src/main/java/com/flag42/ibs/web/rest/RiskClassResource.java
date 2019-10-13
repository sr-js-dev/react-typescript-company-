package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.RiskClassService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.RiskClassDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.RiskClass}.
 */
@RestController
@RequestMapping("/api")
public class RiskClassResource {

    private final Logger log = LoggerFactory.getLogger(RiskClassResource.class);

    private static final String ENTITY_NAME = "riskClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiskClassService riskClassService;

    public RiskClassResource(RiskClassService riskClassService) {
        this.riskClassService = riskClassService;
    }

    /**
     * {@code POST  /risk-classes} : Create a new riskClass.
     *
     * @param riskClassDTO the riskClassDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riskClassDTO, or with status {@code 400 (Bad Request)} if the riskClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/risk-classes")
    public ResponseEntity<RiskClassDTO> createRiskClass(@Valid @RequestBody RiskClassDTO riskClassDTO) throws URISyntaxException {
        log.debug("REST request to save RiskClass : {}", riskClassDTO);
        if (riskClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new riskClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiskClassDTO result = riskClassService.save(riskClassDTO);
        return ResponseEntity.created(new URI("/api/risk-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /risk-classes} : Updates an existing riskClass.
     *
     * @param riskClassDTO the riskClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskClassDTO,
     * or with status {@code 400 (Bad Request)} if the riskClassDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riskClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/risk-classes")
    public ResponseEntity<RiskClassDTO> updateRiskClass(@Valid @RequestBody RiskClassDTO riskClassDTO) throws URISyntaxException {
        log.debug("REST request to update RiskClass : {}", riskClassDTO);
        if (riskClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiskClassDTO result = riskClassService.save(riskClassDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, riskClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /risk-classes} : get all the riskClasses.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riskClasses in body.
     */
    @GetMapping("/risk-classes")
    public ResponseEntity<List<RiskClassDTO>> getAllRiskClasses(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of RiskClasses");
        Page<RiskClassDTO> page = riskClassService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /risk-classes/:id} : get the "id" riskClass.
     *
     * @param id the id of the riskClassDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riskClassDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/risk-classes/{id}")
    public ResponseEntity<RiskClassDTO> getRiskClass(@PathVariable Long id) {
        log.debug("REST request to get RiskClass : {}", id);
        Optional<RiskClassDTO> riskClassDTO = riskClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riskClassDTO);
    }

    /**
     * {@code DELETE  /risk-classes/:id} : delete the "id" riskClass.
     *
     * @param id the id of the riskClassDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/risk-classes/{id}")
    public ResponseEntity<Void> deleteRiskClass(@PathVariable Long id) {
        log.debug("REST request to delete RiskClass : {}", id);
        riskClassService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
