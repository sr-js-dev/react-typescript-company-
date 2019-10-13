package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.ClientPolicyPaymentService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.ClientPolicyPaymentDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.ClientPolicyPayment}.
 */
@RestController
@RequestMapping("/api")
public class ClientPolicyPaymentResource {

    private final Logger log = LoggerFactory.getLogger(ClientPolicyPaymentResource.class);

    private static final String ENTITY_NAME = "clientPolicyPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientPolicyPaymentService clientPolicyPaymentService;

    public ClientPolicyPaymentResource(ClientPolicyPaymentService clientPolicyPaymentService) {
        this.clientPolicyPaymentService = clientPolicyPaymentService;
    }

    /**
     * {@code POST  /client-policy-payments} : Create a new clientPolicyPayment.
     *
     * @param clientPolicyPaymentDTO the clientPolicyPaymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientPolicyPaymentDTO, or with status {@code 400 (Bad Request)} if the clientPolicyPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-policy-payments")
    public ResponseEntity<ClientPolicyPaymentDTO> createClientPolicyPayment(@Valid @RequestBody ClientPolicyPaymentDTO clientPolicyPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save ClientPolicyPayment : {}", clientPolicyPaymentDTO);
        if (clientPolicyPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientPolicyPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientPolicyPaymentDTO result = clientPolicyPaymentService.save(clientPolicyPaymentDTO);
        return ResponseEntity.created(new URI("/api/client-policy-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-policy-payments} : Updates an existing clientPolicyPayment.
     *
     * @param clientPolicyPaymentDTO the clientPolicyPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientPolicyPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the clientPolicyPaymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientPolicyPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-policy-payments")
    public ResponseEntity<ClientPolicyPaymentDTO> updateClientPolicyPayment(@Valid @RequestBody ClientPolicyPaymentDTO clientPolicyPaymentDTO) throws URISyntaxException {
        log.debug("REST request to update ClientPolicyPayment : {}", clientPolicyPaymentDTO);
        if (clientPolicyPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientPolicyPaymentDTO result = clientPolicyPaymentService.save(clientPolicyPaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientPolicyPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /client-policy-payments} : get all the clientPolicyPayments.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientPolicyPayments in body.
     */
    @GetMapping("/client-policy-payments")
    public ResponseEntity<List<ClientPolicyPaymentDTO>> getAllClientPolicyPayments(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ClientPolicyPayments");
        Page<ClientPolicyPaymentDTO> page = clientPolicyPaymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-policy-payments/:id} : get the "id" clientPolicyPayment.
     *
     * @param id the id of the clientPolicyPaymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientPolicyPaymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-policy-payments/{id}")
    public ResponseEntity<ClientPolicyPaymentDTO> getClientPolicyPayment(@PathVariable Long id) {
        log.debug("REST request to get ClientPolicyPayment : {}", id);
        Optional<ClientPolicyPaymentDTO> clientPolicyPaymentDTO = clientPolicyPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientPolicyPaymentDTO);
    }

    /**
     * {@code DELETE  /client-policy-payments/:id} : delete the "id" clientPolicyPayment.
     *
     * @param id the id of the clientPolicyPaymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-policy-payments/{id}")
    public ResponseEntity<Void> deleteClientPolicyPayment(@PathVariable Long id) {
        log.debug("REST request to delete ClientPolicyPayment : {}", id);
        clientPolicyPaymentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
