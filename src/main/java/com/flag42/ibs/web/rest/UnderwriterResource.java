package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.UnderwriterService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.UnderwriterDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.Underwriter}.
 */
@RestController
@RequestMapping("/api")
public class UnderwriterResource {

    private final Logger log = LoggerFactory.getLogger(UnderwriterResource.class);

    private static final String ENTITY_NAME = "underwriter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnderwriterService underwriterService;

    public UnderwriterResource(UnderwriterService underwriterService) {
        this.underwriterService = underwriterService;
    }

    /**
     * {@code POST  /underwriters} : Create a new underwriter.
     *
     * @param underwriterDTO the underwriterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new underwriterDTO, or with status {@code 400 (Bad Request)} if the underwriter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/underwriters")
    public ResponseEntity<UnderwriterDTO> createUnderwriter(@Valid @RequestBody UnderwriterDTO underwriterDTO) throws URISyntaxException {
        log.debug("REST request to save Underwriter : {}", underwriterDTO);
        if (underwriterDTO.getId() != null) {
            throw new BadRequestAlertException("A new underwriter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnderwriterDTO result = underwriterService.save(underwriterDTO);
        return ResponseEntity.created(new URI("/api/underwriters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /underwriters} : Updates an existing underwriter.
     *
     * @param underwriterDTO the underwriterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated underwriterDTO,
     * or with status {@code 400 (Bad Request)} if the underwriterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the underwriterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/underwriters")
    public ResponseEntity<UnderwriterDTO> updateUnderwriter(@Valid @RequestBody UnderwriterDTO underwriterDTO) throws URISyntaxException {
        log.debug("REST request to update Underwriter : {}", underwriterDTO);
        if (underwriterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnderwriterDTO result = underwriterService.save(underwriterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, underwriterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /underwriters} : get all the underwriters.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of underwriters in body.
     */
    @GetMapping("/underwriters")
    public ResponseEntity<List<UnderwriterDTO>> getAllUnderwriters(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Underwriters");
        Page<UnderwriterDTO> page = underwriterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /underwriters/:id} : get the "id" underwriter.
     *
     * @param id the id of the underwriterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the underwriterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/underwriters/{id}")
    public ResponseEntity<UnderwriterDTO> getUnderwriter(@PathVariable Long id) {
        log.debug("REST request to get Underwriter : {}", id);
        Optional<UnderwriterDTO> underwriterDTO = underwriterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(underwriterDTO);
    }

    /**
     * {@code DELETE  /underwriters/:id} : delete the "id" underwriter.
     *
     * @param id the id of the underwriterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/underwriters/{id}")
    public ResponseEntity<Void> deleteUnderwriter(@PathVariable Long id) {
        log.debug("REST request to delete Underwriter : {}", id);
        underwriterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
