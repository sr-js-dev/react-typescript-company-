package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.Policy;
import com.flag42.ibs.domain.CoverType;
import com.flag42.ibs.domain.Underwriter;
import com.flag42.ibs.repository.PolicyRepository;
import com.flag42.ibs.service.PolicyService;
import com.flag42.ibs.service.dto.PolicyDTO;
import com.flag42.ibs.service.mapper.PolicyMapper;
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
 * Integration tests for the {@Link PolicyResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class PolicyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIMIUM_PAYABLE = 1D;
    private static final Double UPDATED_PRIMIUM_PAYABLE = 2D;

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyMapper policyMapper;

    @Autowired
    private PolicyService policyService;

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

    private MockMvc restPolicyMockMvc;

    private Policy policy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PolicyResource policyResource = new PolicyResource(policyService);
        this.restPolicyMockMvc = MockMvcBuilders.standaloneSetup(policyResource)
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
    public static Policy createEntity(EntityManager em) {
        Policy policy = new Policy()
            .name(DEFAULT_NAME)
            .primiumPayable(DEFAULT_PRIMIUM_PAYABLE)
            .status(DEFAULT_STATUS);
        // Add required entity
        CoverType coverType;
        if (TestUtil.findAll(em, CoverType.class).isEmpty()) {
            coverType = CoverTypeResourceIT.createEntity(em);
            em.persist(coverType);
            em.flush();
        } else {
            coverType = TestUtil.findAll(em, CoverType.class).get(0);
        }
        policy.setCoverType(coverType);
        // Add required entity
        Underwriter underwriter;
        if (TestUtil.findAll(em, Underwriter.class).isEmpty()) {
            underwriter = UnderwriterResourceIT.createEntity(em);
            em.persist(underwriter);
            em.flush();
        } else {
            underwriter = TestUtil.findAll(em, Underwriter.class).get(0);
        }
        policy.setUnderwriter(underwriter);
        return policy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy createUpdatedEntity(EntityManager em) {
        Policy policy = new Policy()
            .name(UPDATED_NAME)
            .primiumPayable(UPDATED_PRIMIUM_PAYABLE)
            .status(UPDATED_STATUS);
        // Add required entity
        CoverType coverType;
        if (TestUtil.findAll(em, CoverType.class).isEmpty()) {
            coverType = CoverTypeResourceIT.createUpdatedEntity(em);
            em.persist(coverType);
            em.flush();
        } else {
            coverType = TestUtil.findAll(em, CoverType.class).get(0);
        }
        policy.setCoverType(coverType);
        // Add required entity
        Underwriter underwriter;
        if (TestUtil.findAll(em, Underwriter.class).isEmpty()) {
            underwriter = UnderwriterResourceIT.createUpdatedEntity(em);
            em.persist(underwriter);
            em.flush();
        } else {
            underwriter = TestUtil.findAll(em, Underwriter.class).get(0);
        }
        policy.setUnderwriter(underwriter);
        return policy;
    }

    @BeforeEach
    public void initTest() {
        policy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicy() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);
        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isCreated());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate + 1);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPolicy.getPrimiumPayable()).isEqualTo(DEFAULT_PRIMIUM_PAYABLE);
        assertThat(testPolicy.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // Create the Policy with an existing ID
        policy.setId(1L);
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setName(null);

        // Create the Policy, which fails.
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimiumPayableIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setPrimiumPayable(null);

        // Create the Policy, which fails.
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPolicies() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList
        restPolicyMockMvc.perform(get("/api/policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].primiumPayable").value(hasItem(DEFAULT_PRIMIUM_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get the policy
        restPolicyMockMvc.perform(get("/api/policies/{id}", policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.primiumPayable").value(DEFAULT_PRIMIUM_PAYABLE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicy() throws Exception {
        // Get the policy
        restPolicyMockMvc.perform(get("/api/policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy
        Policy updatedPolicy = policyRepository.findById(policy.getId()).get();
        // Disconnect from session so that the updates on updatedPolicy are not directly saved in db
        em.detach(updatedPolicy);
        updatedPolicy
            .name(UPDATED_NAME)
            .primiumPayable(UPDATED_PRIMIUM_PAYABLE)
            .status(UPDATED_STATUS);
        PolicyDTO policyDTO = policyMapper.toDto(updatedPolicy);

        restPolicyMockMvc.perform(put("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPolicy.getPrimiumPayable()).isEqualTo(UPDATED_PRIMIUM_PAYABLE);
        assertThat(testPolicy.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyMockMvc.perform(put("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeDelete = policyRepository.findAll().size();

        // Delete the policy
        restPolicyMockMvc.perform(delete("/api/policies/{id}", policy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Policy.class);
        Policy policy1 = new Policy();
        policy1.setId(1L);
        Policy policy2 = new Policy();
        policy2.setId(policy1.getId());
        assertThat(policy1).isEqualTo(policy2);
        policy2.setId(2L);
        assertThat(policy1).isNotEqualTo(policy2);
        policy1.setId(null);
        assertThat(policy1).isNotEqualTo(policy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyDTO.class);
        PolicyDTO policyDTO1 = new PolicyDTO();
        policyDTO1.setId(1L);
        PolicyDTO policyDTO2 = new PolicyDTO();
        assertThat(policyDTO1).isNotEqualTo(policyDTO2);
        policyDTO2.setId(policyDTO1.getId());
        assertThat(policyDTO1).isEqualTo(policyDTO2);
        policyDTO2.setId(2L);
        assertThat(policyDTO1).isNotEqualTo(policyDTO2);
        policyDTO1.setId(null);
        assertThat(policyDTO1).isNotEqualTo(policyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(policyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(policyMapper.fromId(null)).isNull();
    }
}
