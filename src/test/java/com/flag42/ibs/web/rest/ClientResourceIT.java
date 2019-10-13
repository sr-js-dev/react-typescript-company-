package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.Client;
import com.flag42.ibs.domain.ClientCategory;
import com.flag42.ibs.domain.IdType;
import com.flag42.ibs.repository.ClientRepository;
import com.flag42.ibs.service.ClientService;
import com.flag42.ibs.service.dto.ClientDTO;
import com.flag42.ibs.service.mapper.ClientMapper;
import com.flag42.ibs.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.flag42.ibs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.flag42.ibs.domain.enumeration.Status;
/**
 * Integration tests for the {@Link ClientResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class ClientResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LEDGER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEDGER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_PRINT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_PRINT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PERSION = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Long DEFAULT_PIN_NUMBER = 1L;
    private static final Long UPDATED_PIN_NUMBER = 2L;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restClientMockMvc;

    private Client client;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientResource clientResource = new ClientResource(clientService);
        this.restClientMockMvc = MockMvcBuilders.standaloneSetup(clientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .clientName(DEFAULT_CLIENT_NAME)
            .ledgerName(DEFAULT_LEDGER_NAME)
            .clientPrintName(DEFAULT_CLIENT_PRINT_NAME)
            .idNumber(DEFAULT_ID_NUMBER)
            .contactPersion(DEFAULT_CONTACT_PERSION)
            .telephone(DEFAULT_TELEPHONE)
            .mobile(DEFAULT_MOBILE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .county(DEFAULT_COUNTY)
            .country(DEFAULT_COUNTRY)
            .pinNumber(DEFAULT_PIN_NUMBER)
            .notes(DEFAULT_NOTES)
            .status(DEFAULT_STATUS);
        // Add required entity
        ClientCategory clientCategory;
        if (TestUtil.findAll(em, ClientCategory.class).isEmpty()) {
            clientCategory = ClientCategoryResourceIT.createEntity(em);
            em.persist(clientCategory);
            em.flush();
        } else {
            clientCategory = TestUtil.findAll(em, ClientCategory.class).get(0);
        }
        client.setCategory(clientCategory);
        // Add required entity
        IdType idType;
        if (TestUtil.findAll(em, IdType.class).isEmpty()) {
            idType = IdTypeResourceIT.createEntity(em);
            em.persist(idType);
            em.flush();
        } else {
            idType = TestUtil.findAll(em, IdType.class).get(0);
        }
        client.setIdType(idType);
        return client;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .clientName(UPDATED_CLIENT_NAME)
            .ledgerName(UPDATED_LEDGER_NAME)
            .clientPrintName(UPDATED_CLIENT_PRINT_NAME)
            .idNumber(UPDATED_ID_NUMBER)
            .contactPersion(UPDATED_CONTACT_PERSION)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .pinNumber(UPDATED_PIN_NUMBER)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);
        // Add required entity
        ClientCategory clientCategory;
        if (TestUtil.findAll(em, ClientCategory.class).isEmpty()) {
            clientCategory = ClientCategoryResourceIT.createUpdatedEntity(em);
            em.persist(clientCategory);
            em.flush();
        } else {
            clientCategory = TestUtil.findAll(em, ClientCategory.class).get(0);
        }
        client.setCategory(clientCategory);
        // Add required entity
        IdType idType;
        if (TestUtil.findAll(em, IdType.class).isEmpty()) {
            idType = IdTypeResourceIT.createUpdatedEntity(em);
            em.persist(idType);
            em.flush();
        } else {
            idType = TestUtil.findAll(em, IdType.class).get(0);
        }
        client.setIdType(idType);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testClient.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testClient.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testClient.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testClient.getLedgerName()).isEqualTo(DEFAULT_LEDGER_NAME);
        assertThat(testClient.getClientPrintName()).isEqualTo(DEFAULT_CLIENT_PRINT_NAME);
        assertThat(testClient.getIdNumber()).isEqualTo(DEFAULT_ID_NUMBER);
        assertThat(testClient.getContactPersion()).isEqualTo(DEFAULT_CONTACT_PERSION);
        assertThat(testClient.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testClient.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClient.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testClient.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testClient.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testClient.getPinNumber()).isEqualTo(DEFAULT_PIN_NUMBER);
        assertThat(testClient.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testClient.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);
        ClientDTO clientDTO = clientMapper.toDto(client);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ledgerName").value(hasItem(DEFAULT_LEDGER_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientPrintName").value(hasItem(DEFAULT_CLIENT_PRINT_NAME.toString())))
            .andExpect(jsonPath("$.[*].idNumber").value(hasItem(DEFAULT_ID_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].contactPersion").value(hasItem(DEFAULT_CONTACT_PERSION.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].pinNumber").value(hasItem(DEFAULT_PIN_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME.toString()))
            .andExpect(jsonPath("$.ledgerName").value(DEFAULT_LEDGER_NAME.toString()))
            .andExpect(jsonPath("$.clientPrintName").value(DEFAULT_CLIENT_PRINT_NAME.toString()))
            .andExpect(jsonPath("$.idNumber").value(DEFAULT_ID_NUMBER.toString()))
            .andExpect(jsonPath("$.contactPersion").value(DEFAULT_CONTACT_PERSION.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.pinNumber").value(DEFAULT_PIN_NUMBER.intValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .clientName(UPDATED_CLIENT_NAME)
            .ledgerName(UPDATED_LEDGER_NAME)
            .clientPrintName(UPDATED_CLIENT_PRINT_NAME)
            .idNumber(UPDATED_ID_NUMBER)
            .contactPersion(UPDATED_CONTACT_PERSION)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .pinNumber(UPDATED_PIN_NUMBER)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS);
        ClientDTO clientDTO = clientMapper.toDto(updatedClient);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testClient.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testClient.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testClient.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testClient.getLedgerName()).isEqualTo(UPDATED_LEDGER_NAME);
        assertThat(testClient.getClientPrintName()).isEqualTo(UPDATED_CLIENT_PRINT_NAME);
        assertThat(testClient.getIdNumber()).isEqualTo(UPDATED_ID_NUMBER);
        assertThat(testClient.getContactPersion()).isEqualTo(UPDATED_CONTACT_PERSION);
        assertThat(testClient.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testClient.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClient.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testClient.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testClient.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testClient.getPinNumber()).isEqualTo(UPDATED_PIN_NUMBER);
        assertThat(testClient.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testClient.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = new Client();
        client1.setId(1L);
        Client client2 = new Client();
        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);
        client2.setId(2L);
        assertThat(client1).isNotEqualTo(client2);
        client1.setId(null);
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientDTO.class);
        ClientDTO clientDTO1 = new ClientDTO();
        clientDTO1.setId(1L);
        ClientDTO clientDTO2 = new ClientDTO();
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
        clientDTO2.setId(clientDTO1.getId());
        assertThat(clientDTO1).isEqualTo(clientDTO2);
        clientDTO2.setId(2L);
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
        clientDTO1.setId(null);
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientMapper.fromId(null)).isNull();
    }
}
