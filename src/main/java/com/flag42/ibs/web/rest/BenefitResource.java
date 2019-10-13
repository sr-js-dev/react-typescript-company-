package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.BenefitService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.BenefitDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.Benefit}.
 */
@RestController
@RequestMapping("/api")
public class BenefitResource {

    private final Logger log = LoggerFactory.getLogger(BenefitResource.class);

    private static final String ENTITY_NAME = "benefit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenefitService benefitService;

    public BenefitResource(BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    /**
     * {@code POST  /benefits} : Create a new benefit.
     *
     * @param benefitDTO the benefitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benefitDTO, or with status {@code 400 (Bad Request)} if the benefit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benefits")
    public ResponseEntity<BenefitDTO> createBenefit(@Valid @RequestBody BenefitDTO benefitDTO) throws URISyntaxException {
        log.debug("REST request to save Benefit : {}", benefitDTO);
        if (benefitDTO.getId() != null) {
            throw new BadRequestAlertException("A new benefit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BenefitDTO result = benefitService.save(benefitDTO);
        return ResponseEntity.created(new URI("/api/benefits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benefits} : Updates an existing benefit.
     *
     * @param benefitDTO the benefitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitDTO,
     * or with status {@code 400 (Bad Request)} if the benefitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benefitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benefits")
    public ResponseEntity<BenefitDTO> updateBenefit(@Valid @RequestBody BenefitDTO benefitDTO) throws URISyntaxException {
        log.debug("REST request to update Benefit : {}", benefitDTO);
        if (benefitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BenefitDTO result = benefitService.save(benefitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, benefitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /benefits} : get all the benefits.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benefits in body.
     */
    @GetMapping("/benefits")
    public ResponseEntity<List<BenefitDTO>> getAllBenefits(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Benefits");
        Page<BenefitDTO> page = benefitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /benefits/:id} : get the "id" benefit.
     *
     * @param id the id of the benefitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benefitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benefits/{id}")
    public ResponseEntity<BenefitDTO> getBenefit(@PathVariable Long id) {
        log.debug("REST request to get Benefit : {}", id);
        Optional<BenefitDTO> benefitDTO = benefitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(benefitDTO);
    }

    /**
     * {@code DELETE  /benefits/:id} : delete the "id" benefit.
     *
     * @param id the id of the benefitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benefits/{id}")
    public ResponseEntity<Void> deleteBenefit(@PathVariable Long id) {
        log.debug("REST request to delete Benefit : {}", id);
        benefitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
