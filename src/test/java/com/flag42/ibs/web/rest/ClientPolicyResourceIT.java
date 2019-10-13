package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.ClientPolicy;
import com.flag42.ibs.domain.Client;
import com.flag42.ibs.domain.Policy;
import com.flag42.ibs.repository.ClientPolicyRepository;
import com.flag42.ibs.service.ClientPolicyService;
import com.flag42.ibs.service.dto.ClientPolicyDTO;
import com.flag42.ibs.service.mapper.ClientPolicyMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.flag42.ibs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.flag42.ibs.domain.enumeration.PaymentStatus;
/**
 * Integration tests for the {@Link ClientPolicyResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class ClientPolicyResourceIT {

    private static final LocalDate DEFAULT_POLICY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POLICY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INVOICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PRIMIUM_PAYABLE = 1D;
    private static final Double UPDATED_PRIMIUM_PAYABLE = 2D;

    private static final Double DEFAULT_OPEN_PAYABLE = 1D;
    private static final Double UPDATED_OPEN_PAYABLE = 2D;

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.UNPAID;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.PARTIAL;

    @Autowired
    private ClientPolicyRepository clientPolicyRepository;

    @Autowired
    private ClientPolicyMapper clientPolicyMapper;

    @Autowired
    private ClientPolicyService clientPolicyService;

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

    private MockMvc restClientPolicyMockMvc;

    private ClientPolicy clientPolicy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientPolicyResource clientPolicyResource = new ClientPolicyResource(clientPolicyService);
        this.restClientPolicyMockMvc = MockMvcBuilders.standaloneSetup(clientPolicyResource)
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
    public static ClientPolicy createEntity(EntityManager em) {
        ClientPolicy clientPolicy = new ClientPolicy()
            .policyDate(DEFAULT_POLICY_DATE)
            .invoiceNo(DEFAULT_INVOICE_NO)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .primiumPayable(DEFAULT_PRIMIUM_PAYABLE)
            .openPayable(DEFAULT_OPEN_PAYABLE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        clientPolicy.setClient(client);
        // Add required entity
        Policy policy;
        if (TestUtil.findAll(em, Policy.class).isEmpty()) {
            policy = PolicyResourceIT.createEntity(em);
            em.persist(policy);
            em.flush();
        } else {
            policy = TestUtil.findAll(em, Policy.class).get(0);
        }
        clientPolicy.setPolicy(policy);
        return clientPolicy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientPolicy createUpdatedEntity(EntityManager em) {
        ClientPolicy clientPolicy = new ClientPolicy()
            .policyDate(UPDATED_POLICY_DATE)
            .invoiceNo(UPDATED_INVOICE_NO)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .primiumPayable(UPDATED_PRIMIUM_PAYABLE)
            .openPayable(UPDATED_OPEN_PAYABLE)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createUpdatedEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        clientPolicy.setClient(client);
        // Add required entity
        Policy policy;
        if (TestUtil.findAll(em, Policy.class).isEmpty()) {
            policy = PolicyResourceIT.createUpdatedEntity(em);
            em.persist(policy);
            em.flush();
        } else {
            policy = TestUtil.findAll(em, Policy.class).get(0);
        }
        clientPolicy.setPolicy(policy);
        return clientPolicy;
    }

    @BeforeEach
    public void initTest() {
        clientPolicy = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientPolicy() throws Exception {
        int databaseSizeBeforeCreate = clientPolicyRepository.findAll().size();

        // Create the ClientPolicy
        ClientPolicyDTO clientPolicyDTO = clientPolicyMapper.toDto(clientPolicy);
        restClientPolicyMockMvc.perform(post("/api/client-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientPolicy in the database
        List<ClientPolicy> clientPolicyList = clientPolicyRepository.findAll();
        assertThat(clientPolicyList).hasSize(databaseSizeBeforeCreate + 1);
        ClientPolicy testClientPolicy = clientPolicyList.get(clientPolicyList.size() - 1);
        assertThat(testClientPolicy.getPolicyDate()).isEqualTo(DEFAULT_POLICY_DATE);
        assertThat(testClientPolicy.getInvoiceNo()).isEqualTo(DEFAULT_INVOICE_NO);
        assertThat(testClientPolicy.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testClientPolicy.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testClientPolicy.getPrimiumPayable()).isEqualTo(DEFAULT_PRIMIUM_PAYABLE);
        assertThat(testClientPolicy.getOpenPayable()).isEqualTo(DEFAULT_OPEN_PAYABLE);
        assertThat(testClientPolicy.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void createClientPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientPolicyRepository.findAll().size();

        // Create the ClientPolicy with an existing ID
        clientPolicy.setId(1L);
        ClientPolicyDTO clientPolicyDTO = clientPolicyMapper.toDto(clientPolicy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientPolicyMockMvc.perform(post("/api/client-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientPolicy in the database
        List<ClientPolicy> clientPolicyList = clientPolicyRepository.findAll();
        assertThat(clientPolicyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPolicyDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientPolicyRepository.findAll().size();
        // set the field null
        clientPolicy.setPolicyDate(null);

        // Create the ClientPolicy, which fails.
        ClientPolicyDTO clientPolicyDTO = clientPolicyMapper.toDto(clientPolicy);

        restClientPolicyMockMvc.perform(post("/api/client-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyDTO)))
            .andExpect(status().isBadRequest());

        List<ClientPolicy> clientPolicyList = clientPolicyRepository.findAll();
        assertThat(clientPolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimiumPayableIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientPolicyRepository.findAll().size();
        // set the field null
        clientPolicy.setPrimiumPayable(null);

        // Create the ClientPolicy, which fails.
        ClientPolicyDTO clientPolicyDTO = clientPolicyMapper.toDto(clientPolicy);

        restClientPolicyMockMvc.perform(post("/api/client-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyDTO)))
            .andExpect(status().isBadRequest());

        List<ClientPolicy> clientPolicyList = clientPolicyRepository.findAll();
        assertThat(clientPolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpenPayableIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientPolicyRepository.findAll().size();
        // set the field null
        clientPolicy.setOpenPayable(null);

        // Create the ClientPolicy, which fails.
        ClientPolicyDTO clientPolicyDTO = clientPolicyMapper.toDto(clientPolicy);

        restClientPolicyMockMvc.perform(post("/api/client-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyDTO)))
            .andExpect(status().isBadRequest());

        List<ClientPolicy> clientPolicyList = clientPolicyRepository.findAll();
        assertThat(clientPolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientPolicies() throws Exception {
        // Initialize the database
        clientPolicyRepository.saveAndFlush(clientPolicy);

        // Get all the clientPolicyList
        restClientPolicyMockMvc.perform(get("/api/client-policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientPolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyDate").value(hasItem(DEFAULT_POLICY_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].primiumPayable").value(hasItem(DEFAULT_PRIMIUM_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].openPayable").value(hasItem(DEFAULT_OPEN_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getClientPolicy() throws Exception {
        // Initialize the database
        clientPolicyRepository.saveAndFlush(clientPolicy);

        // Get the clientPolicy
        restClientPolicyMockMvc.perform(get("/api/client-policies/{id}", clientPolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientPolicy.getId().intValue()))
            .andExpect(jsonPath("$.policyDate").value(DEFAULT_POLICY_DATE.toString()))
            .andExpect(jsonPath("$.invoiceNo").value(DEFAULT_INVOICE_NO.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.primiumPayable").value(DEFAULT_PRIMIUM_PAYABLE.doubleValue()))
            .andExpect(jsonPath("$.openPayable").value(DEFAULT_OPEN_PAYABLE.doubleValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClientPolicy() throws Exception {
        // Get the clientPolicy
        restClientPolicyMockMvc.perform(get("/api/client-policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientPolicy() throws Exception {
        // Initialize the database
        clientPolicyRepository.saveAndFlush(clientPolicy);

        int databaseSizeBeforeUpdate = clientPolicyRepository.findAll().size();

        // Update the clientPolicy
        ClientPolicy updatedClientPolicy = clientPolicyRepository.findById(clientPolicy.getId()).get();
        // Disconnect from session so that the updates on updatedClientPolicy are not directly saved in db
        em.detach(updatedClientPolicy);
        updatedClientPolicy
            .policyDate(UPDATED_POLICY_DATE)
            .invoiceNo(UPDATED_INVOICE_NO)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .primiumPayable(UPDATED_PRIMIUM_PAYABLE)
            .openPayable(UPDATED_OPEN_PAYABLE)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        ClientPolicyDTO clientPolicyDTO = clientPolicyMapper.toDto(updatedClientPolicy);

        restClientPolicyMockMvc.perform(put("/api/client-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyDTO)))
            .andExpect(status().isOk());

        // Validate the ClientPolicy in the database
        List<ClientPolicy> clientPolicyList = clientPolicyRepository.findAll();
        assertThat(clientPolicyList).hasSize(databaseSizeBeforeUpdate);
        ClientPolicy testClientPolicy = clientPolicyList.get(clientPolicyList.size() - 1);
        assertThat(testClientPolicy.getPolicyDate()).isEqualTo(UPDATED_POLICY_DATE);
        assertThat(testClientPolicy.getInvoiceNo()).isEqualTo(UPDATED_INVOICE_NO);
        assertThat(testClientPolicy.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testClientPolicy.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testClientPolicy.getPrimiumPayable()).isEqualTo(UPDATED_PRIMIUM_PAYABLE);
        assertThat(testClientPolicy.getOpenPayable()).isEqualTo(UPDATED_OPEN_PAYABLE);
        assertThat(testClientPolicy.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingClientPolicy() throws Exception {
        int databaseSizeBeforeUpdate = clientPolicyRepository.findAll().size();

        // Create the ClientPolicy
        ClientPolicyDTO clientPolicyDTO = clientPolicyMapper.toDto(clientPolicy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientPolicyMockMvc.perform(put("/api/client-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientPolicy in the database
        List<ClientPolicy> clientPolicyList = clientPolicyRepository.findAll();
        assertThat(clientPolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientPolicy() throws Exception {
        // Initialize the database
        clientPolicyRepository.saveAndFlush(clientPolicy);

        int databaseSizeBeforeDelete = clientPolicyRepository.findAll().size();

        // Delete the clientPolicy
        restClientPolicyMockMvc.perform(delete("/api/client-policies/{id}", clientPolicy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientPolicy> clientPolicyList = clientPolicyRepository.findAll();
        assertThat(clientPolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientPolicy.class);
        ClientPolicy clientPolicy1 = new ClientPolicy();
        clientPolicy1.setId(1L);
        ClientPolicy clientPolicy2 = new ClientPolicy();
        clientPolicy2.setId(clientPolicy1.getId());
        assertThat(clientPolicy1).isEqualTo(clientPolicy2);
        clientPolicy2.setId(2L);
        assertThat(clientPolicy1).isNotEqualTo(clientPolicy2);
        clientPolicy1.setId(null);
        assertThat(clientPolicy1).isNotEqualTo(clientPolicy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientPolicyDTO.class);
        ClientPolicyDTO clientPolicyDTO1 = new ClientPolicyDTO();
        clientPolicyDTO1.setId(1L);
        ClientPolicyDTO clientPolicyDTO2 = new ClientPolicyDTO();
        assertThat(clientPolicyDTO1).isNotEqualTo(clientPolicyDTO2);
        clientPolicyDTO2.setId(clientPolicyDTO1.getId());
        assertThat(clientPolicyDTO1).isEqualTo(clientPolicyDTO2);
        clientPolicyDTO2.setId(2L);
        assertThat(clientPolicyDTO1).isNotEqualTo(clientPolicyDTO2);
        clientPolicyDTO1.setId(null);
        assertThat(clientPolicyDTO1).isNotEqualTo(clientPolicyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientPolicyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientPolicyMapper.fromId(null)).isNull();
    }
}
