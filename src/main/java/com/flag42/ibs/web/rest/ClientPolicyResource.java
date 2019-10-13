package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.ClientPolicyService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.ClientPolicyDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.ClientPolicy}.
 */
@RestController
@RequestMapping("/api")
public class ClientPolicyResource {

    private final Logger log = LoggerFactory.getLogger(ClientPolicyResource.class);

    private static final String ENTITY_NAME = "clientPolicy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientPolicyService clientPolicyService;

    public ClientPolicyResource(ClientPolicyService clientPolicyService) {
        this.clientPolicyService = clientPolicyService;
    }

    /**
     * {@code POST  /client-policies} : Create a new clientPolicy.
     *
     * @param clientPolicyDTO the clientPolicyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientPolicyDTO, or with status {@code 400 (Bad Request)} if the clientPolicy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-policies")
    public ResponseEntity<ClientPolicyDTO> createClientPolicy(@Valid @RequestBody ClientPolicyDTO clientPolicyDTO) throws URISyntaxException {
        log.debug("REST request to save ClientPolicy : {}", clientPolicyDTO);
        if (clientPolicyDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientPolicy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientPolicyDTO result = clientPolicyService.save(clientPolicyDTO);
        return ResponseEntity.created(new URI("/api/client-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-policies} : Updates an existing clientPolicy.
     *
     * @param clientPolicyDTO the clientPolicyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientPolicyDTO,
     * or with status {@code 400 (Bad Request)} if the clientPolicyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientPolicyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-policies")
    public ResponseEntity<ClientPolicyDTO> updateClientPolicy(@Valid @RequestBody ClientPolicyDTO clientPolicyDTO) throws URISyntaxException {
        log.debug("REST request to update ClientPolicy : {}", clientPolicyDTO);
        if (clientPolicyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientPolicyDTO result = clientPolicyService.save(clientPolicyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientPolicyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /client-policies} : get all the clientPolicies.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientPolicies in body.
     */
    @GetMapping("/client-policies")
    public ResponseEntity<List<ClientPolicyDTO>> getAllClientPolicies(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ClientPolicies");
        Page<ClientPolicyDTO> page = clientPolicyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-policies/:id} : get the "id" clientPolicy.
     *
     * @param id the id of the clientPolicyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientPolicyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-policies/{id}")
    public ResponseEntity<ClientPolicyDTO> getClientPolicy(@PathVariable Long id) {
        log.debug("REST request to get ClientPolicy : {}", id);
        Optional<ClientPolicyDTO> clientPolicyDTO = clientPolicyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientPolicyDTO);
    }

    /**
     * {@code DELETE  /client-policies/:id} : delete the "id" clientPolicy.
     *
     * @param id the id of the clientPolicyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-policies/{id}")
    public ResponseEntity<Void> deleteClientPolicy(@PathVariable Long id) {
        log.debug("REST request to delete ClientPolicy : {}", id);
        clientPolicyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
