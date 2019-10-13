package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.ClientPolicyPayment;
import com.flag42.ibs.domain.ClientPolicy;
import com.flag42.ibs.repository.ClientPolicyPaymentRepository;
import com.flag42.ibs.service.ClientPolicyPaymentService;
import com.flag42.ibs.service.dto.ClientPolicyPaymentDTO;
import com.flag42.ibs.service.mapper.ClientPolicyPaymentMapper;
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

import com.flag42.ibs.domain.enumeration.PaymentMethod;
/**
 * Integration tests for the {@Link ClientPolicyPaymentResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class ClientPolicyPaymentResourceIT {

    private static final LocalDate DEFAULT_PAY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final PaymentMethod DEFAULT_PAYMENT_METHOD = PaymentMethod.CARD;
    private static final PaymentMethod UPDATED_PAYMENT_METHOD = PaymentMethod.MPESA;

    private static final Boolean DEFAULT_IS_IPF = false;
    private static final Boolean UPDATED_IS_IPF = true;

    @Autowired
    private ClientPolicyPaymentRepository clientPolicyPaymentRepository;

    @Autowired
    private ClientPolicyPaymentMapper clientPolicyPaymentMapper;

    @Autowired
    private ClientPolicyPaymentService clientPolicyPaymentService;

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

    private MockMvc restClientPolicyPaymentMockMvc;

    private ClientPolicyPayment clientPolicyPayment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientPolicyPaymentResource clientPolicyPaymentResource = new ClientPolicyPaymentResource(clientPolicyPaymentService);
        this.restClientPolicyPaymentMockMvc = MockMvcBuilders.standaloneSetup(clientPolicyPaymentResource)
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
    public static ClientPolicyPayment createEntity(EntityManager em) {
        ClientPolicyPayment clientPolicyPayment = new ClientPolicyPayment()
            .payDate(DEFAULT_PAY_DATE)
            .amount(DEFAULT_AMOUNT)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .isIPF(DEFAULT_IS_IPF);
        // Add required entity
        ClientPolicy clientPolicy;
        if (TestUtil.findAll(em, ClientPolicy.class).isEmpty()) {
            clientPolicy = ClientPolicyResourceIT.createEntity(em);
            em.persist(clientPolicy);
            em.flush();
        } else {
            clientPolicy = TestUtil.findAll(em, ClientPolicy.class).get(0);
        }
        clientPolicyPayment.setClientPolicy(clientPolicy);
        return clientPolicyPayment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientPolicyPayment createUpdatedEntity(EntityManager em) {
        ClientPolicyPayment clientPolicyPayment = new ClientPolicyPayment()
            .payDate(UPDATED_PAY_DATE)
            .amount(UPDATED_AMOUNT)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .isIPF(UPDATED_IS_IPF);
        // Add required entity
        ClientPolicy clientPolicy;
        if (TestUtil.findAll(em, ClientPolicy.class).isEmpty()) {
            clientPolicy = ClientPolicyResourceIT.createUpdatedEntity(em);
            em.persist(clientPolicy);
            em.flush();
        } else {
            clientPolicy = TestUtil.findAll(em, ClientPolicy.class).get(0);
        }
        clientPolicyPayment.setClientPolicy(clientPolicy);
        return clientPolicyPayment;
    }

    @BeforeEach
    public void initTest() {
        clientPolicyPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientPolicyPayment() throws Exception {
        int databaseSizeBeforeCreate = clientPolicyPaymentRepository.findAll().size();

        // Create the ClientPolicyPayment
        ClientPolicyPaymentDTO clientPolicyPaymentDTO = clientPolicyPaymentMapper.toDto(clientPolicyPayment);
        restClientPolicyPaymentMockMvc.perform(post("/api/client-policy-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientPolicyPayment in the database
        List<ClientPolicyPayment> clientPolicyPaymentList = clientPolicyPaymentRepository.findAll();
        assertThat(clientPolicyPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        ClientPolicyPayment testClientPolicyPayment = clientPolicyPaymentList.get(clientPolicyPaymentList.size() - 1);
        assertThat(testClientPolicyPayment.getPayDate()).isEqualTo(DEFAULT_PAY_DATE);
        assertThat(testClientPolicyPayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testClientPolicyPayment.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testClientPolicyPayment.isIsIPF()).isEqualTo(DEFAULT_IS_IPF);
    }

    @Test
    @Transactional
    public void createClientPolicyPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientPolicyPaymentRepository.findAll().size();

        // Create the ClientPolicyPayment with an existing ID
        clientPolicyPayment.setId(1L);
        ClientPolicyPaymentDTO clientPolicyPaymentDTO = clientPolicyPaymentMapper.toDto(clientPolicyPayment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientPolicyPaymentMockMvc.perform(post("/api/client-policy-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientPolicyPayment in the database
        List<ClientPolicyPayment> clientPolicyPaymentList = clientPolicyPaymentRepository.findAll();
        assertThat(clientPolicyPaymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClientPolicyPayments() throws Exception {
        // Initialize the database
        clientPolicyPaymentRepository.saveAndFlush(clientPolicyPayment);

        // Get all the clientPolicyPaymentList
        restClientPolicyPaymentMockMvc.perform(get("/api/client-policy-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientPolicyPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].payDate").value(hasItem(DEFAULT_PAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].isIPF").value(hasItem(DEFAULT_IS_IPF.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getClientPolicyPayment() throws Exception {
        // Initialize the database
        clientPolicyPaymentRepository.saveAndFlush(clientPolicyPayment);

        // Get the clientPolicyPayment
        restClientPolicyPaymentMockMvc.perform(get("/api/client-policy-payments/{id}", clientPolicyPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientPolicyPayment.getId().intValue()))
            .andExpect(jsonPath("$.payDate").value(DEFAULT_PAY_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.isIPF").value(DEFAULT_IS_IPF.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClientPolicyPayment() throws Exception {
        // Get the clientPolicyPayment
        restClientPolicyPaymentMockMvc.perform(get("/api/client-policy-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientPolicyPayment() throws Exception {
        // Initialize the database
        clientPolicyPaymentRepository.saveAndFlush(clientPolicyPayment);

        int databaseSizeBeforeUpdate = clientPolicyPaymentRepository.findAll().size();

        // Update the clientPolicyPayment
        ClientPolicyPayment updatedClientPolicyPayment = clientPolicyPaymentRepository.findById(clientPolicyPayment.getId()).get();
        // Disconnect from session so that the updates on updatedClientPolicyPayment are not directly saved in db
        em.detach(updatedClientPolicyPayment);
        updatedClientPolicyPayment
            .payDate(UPDATED_PAY_DATE)
            .amount(UPDATED_AMOUNT)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .isIPF(UPDATED_IS_IPF);
        ClientPolicyPaymentDTO clientPolicyPaymentDTO = clientPolicyPaymentMapper.toDto(updatedClientPolicyPayment);

        restClientPolicyPaymentMockMvc.perform(put("/api/client-policy-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyPaymentDTO)))
            .andExpect(status().isOk());

        // Validate the ClientPolicyPayment in the database
        List<ClientPolicyPayment> clientPolicyPaymentList = clientPolicyPaymentRepository.findAll();
        assertThat(clientPolicyPaymentList).hasSize(databaseSizeBeforeUpdate);
        ClientPolicyPayment testClientPolicyPayment = clientPolicyPaymentList.get(clientPolicyPaymentList.size() - 1);
        assertThat(testClientPolicyPayment.getPayDate()).isEqualTo(UPDATED_PAY_DATE);
        assertThat(testClientPolicyPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testClientPolicyPayment.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testClientPolicyPayment.isIsIPF()).isEqualTo(UPDATED_IS_IPF);
    }

    @Test
    @Transactional
    public void updateNonExistingClientPolicyPayment() throws Exception {
        int databaseSizeBeforeUpdate = clientPolicyPaymentRepository.findAll().size();

        // Create the ClientPolicyPayment
        ClientPolicyPaymentDTO clientPolicyPaymentDTO = clientPolicyPaymentMapper.toDto(clientPolicyPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientPolicyPaymentMockMvc.perform(put("/api/client-policy-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPolicyPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientPolicyPayment in the database
        List<ClientPolicyPayment> clientPolicyPaymentList = clientPolicyPaymentRepository.findAll();
        assertThat(clientPolicyPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientPolicyPayment() throws Exception {
        // Initialize the database
        clientPolicyPaymentRepository.saveAndFlush(clientPolicyPayment);

        int databaseSizeBeforeDelete = clientPolicyPaymentRepository.findAll().size();

        // Delete the clientPolicyPayment
        restClientPolicyPaymentMockMvc.perform(delete("/api/client-policy-payments/{id}", clientPolicyPayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientPolicyPayment> clientPolicyPaymentList = clientPolicyPaymentRepository.findAll();
        assertThat(clientPolicyPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientPolicyPayment.class);
        ClientPolicyPayment clientPolicyPayment1 = new ClientPolicyPayment();
        clientPolicyPayment1.setId(1L);
        ClientPolicyPayment clientPolicyPayment2 = new ClientPolicyPayment();
        clientPolicyPayment2.setId(clientPolicyPayment1.getId());
        assertThat(clientPolicyPayment1).isEqualTo(clientPolicyPayment2);
        clientPolicyPayment2.setId(2L);
        assertThat(clientPolicyPayment1).isNotEqualTo(clientPolicyPayment2);
        clientPolicyPayment1.setId(null);
        assertThat(clientPolicyPayment1).isNotEqualTo(clientPolicyPayment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientPolicyPaymentDTO.class);
        ClientPolicyPaymentDTO clientPolicyPaymentDTO1 = new ClientPolicyPaymentDTO();
        clientPolicyPaymentDTO1.setId(1L);
        ClientPolicyPaymentDTO clientPolicyPaymentDTO2 = new ClientPolicyPaymentDTO();
        assertThat(clientPolicyPaymentDTO1).isNotEqualTo(clientPolicyPaymentDTO2);
        clientPolicyPaymentDTO2.setId(clientPolicyPaymentDTO1.getId());
        assertThat(clientPolicyPaymentDTO1).isEqualTo(clientPolicyPaymentDTO2);
        clientPolicyPaymentDTO2.setId(2L);
        assertThat(clientPolicyPaymentDTO1).isNotEqualTo(clientPolicyPaymentDTO2);
        clientPolicyPaymentDTO1.setId(null);
        assertThat(clientPolicyPaymentDTO1).isNotEqualTo(clientPolicyPaymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientPolicyPaymentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientPolicyPaymentMapper.fromId(null)).isNull();
    }
}
