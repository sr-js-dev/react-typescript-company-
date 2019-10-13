package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.NameTitleService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.NameTitleDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.NameTitle}.
 */
@RestController
@RequestMapping("/api")
public class NameTitleResource {

    private final Logger log = LoggerFactory.getLogger(NameTitleResource.class);

    private static final String ENTITY_NAME = "nameTitle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NameTitleService nameTitleService;

    public NameTitleResource(NameTitleService nameTitleService) {
        this.nameTitleService = nameTitleService;
    }

    /**
     * {@code POST  /name-titles} : Create a new nameTitle.
     *
     * @param nameTitleDTO the nameTitleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nameTitleDTO, or with status {@code 400 (Bad Request)} if the nameTitle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/name-titles")
    public ResponseEntity<NameTitleDTO> createNameTitle(@Valid @RequestBody NameTitleDTO nameTitleDTO) throws URISyntaxException {
        log.debug("REST request to save NameTitle : {}", nameTitleDTO);
        if (nameTitleDTO.getId() != null) {
            throw new BadRequestAlertException("A new nameTitle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NameTitleDTO result = nameTitleService.save(nameTitleDTO);
        return ResponseEntity.created(new URI("/api/name-titles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /name-titles} : Updates an existing nameTitle.
     *
     * @param nameTitleDTO the nameTitleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nameTitleDTO,
     * or with status {@code 400 (Bad Request)} if the nameTitleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nameTitleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/name-titles")
    public ResponseEntity<NameTitleDTO> updateNameTitle(@Valid @RequestBody NameTitleDTO nameTitleDTO) throws URISyntaxException {
        log.debug("REST request to update NameTitle : {}", nameTitleDTO);
        if (nameTitleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NameTitleDTO result = nameTitleService.save(nameTitleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nameTitleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /name-titles} : get all the nameTitles.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nameTitles in body.
     */
    @GetMapping("/name-titles")
    public ResponseEntity<List<NameTitleDTO>> getAllNameTitles(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of NameTitles");
        Page<NameTitleDTO> page = nameTitleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /name-titles/:id} : get the "id" nameTitle.
     *
     * @param id the id of the nameTitleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nameTitleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/name-titles/{id}")
    public ResponseEntity<NameTitleDTO> getNameTitle(@PathVariable Long id) {
        log.debug("REST request to get NameTitle : {}", id);
        Optional<NameTitleDTO> nameTitleDTO = nameTitleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nameTitleDTO);
    }

    /**
     * {@code DELETE  /name-titles/:id} : delete the "id" nameTitle.
     *
     * @param id the id of the nameTitleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/name-titles/{id}")
    public ResponseEntity<Void> deleteNameTitle(@PathVariable Long id) {
        log.debug("REST request to delete NameTitle : {}", id);
        nameTitleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
