package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.PolicyBenefit;
import com.flag42.ibs.domain.Benefit;
import com.flag42.ibs.domain.Policy;
import com.flag42.ibs.repository.PolicyBenefitRepository;
import com.flag42.ibs.service.PolicyBenefitService;
import com.flag42.ibs.service.dto.PolicyBenefitDTO;
import com.flag42.ibs.service.mapper.PolicyBenefitMapper;
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

import com.flag42.ibs.domain.enumeration.BenefitRate;
/**
 * Integration tests for the {@Link PolicyBenefitResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class PolicyBenefitResourceIT {

    private static final BenefitRate DEFAULT_BENEFIT_RATE = BenefitRate.FIXED;
    private static final BenefitRate UPDATED_BENEFIT_RATE = BenefitRate.PERCENTAGE;

    private static final String DEFAULT_BENEFIT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_BENEFIT_VALUE = "BBBBBBBBBB";

    private static final Double DEFAULT_BENEFIT_MIN_VALUE = 1D;
    private static final Double UPDATED_BENEFIT_MIN_VALUE = 2D;

    @Autowired
    private PolicyBenefitRepository policyBenefitRepository;

    @Autowired
    private PolicyBenefitMapper policyBenefitMapper;

    @Autowired
    private PolicyBenefitService policyBenefitService;

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

    private MockMvc restPolicyBenefitMockMvc;

    private PolicyBenefit policyBenefit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PolicyBenefitResource policyBenefitResource = new PolicyBenefitResource(policyBenefitService);
        this.restPolicyBenefitMockMvc = MockMvcBuilders.standaloneSetup(policyBenefitResource)
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
    public static PolicyBenefit createEntity(EntityManager em) {
        PolicyBenefit policyBenefit = new PolicyBenefit()
            .benefitRate(DEFAULT_BENEFIT_RATE)
            .benefitValue(DEFAULT_BENEFIT_VALUE)
            .benefitMinValue(DEFAULT_BENEFIT_MIN_VALUE);
        // Add required entity
        Benefit benefit;
        if (TestUtil.findAll(em, Benefit.class).isEmpty()) {
            benefit = BenefitResourceIT.createEntity(em);
            em.persist(benefit);
            em.flush();
        } else {
            benefit = TestUtil.findAll(em, Benefit.class).get(0);
        }
        policyBenefit.setBenefit(benefit);
        // Add required entity
        Policy policy;
        if (TestUtil.findAll(em, Policy.class).isEmpty()) {
            policy = PolicyResourceIT.createEntity(em);
            em.persist(policy);
            em.flush();
        } else {
            policy = TestUtil.findAll(em, Policy.class).get(0);
        }
        policyBenefit.setPolicy(policy);
        return policyBenefit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolicyBenefit createUpdatedEntity(EntityManager em) {
        PolicyBenefit policyBenefit = new PolicyBenefit()
            .benefitRate(UPDATED_BENEFIT_RATE)
            .benefitValue(UPDATED_BENEFIT_VALUE)
            .benefitMinValue(UPDATED_BENEFIT_MIN_VALUE);
        // Add required entity
        Benefit benefit;
        if (TestUtil.findAll(em, Benefit.class).isEmpty()) {
            benefit = BenefitResourceIT.createUpdatedEntity(em);
            em.persist(benefit);
            em.flush();
        } else {
            benefit = TestUtil.findAll(em, Benefit.class).get(0);
        }
        policyBenefit.setBenefit(benefit);
        // Add required entity
        Policy policy;
        if (TestUtil.findAll(em, Policy.class).isEmpty()) {
            policy = PolicyResourceIT.createUpdatedEntity(em);
            em.persist(policy);
            em.flush();
        } else {
            policy = TestUtil.findAll(em, Policy.class).get(0);
        }
        policyBenefit.setPolicy(policy);
        return policyBenefit;
    }

    @BeforeEach
    public void initTest() {
        policyBenefit = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicyBenefit() throws Exception {
        int databaseSizeBeforeCreate = policyBenefitRepository.findAll().size();

        // Create the PolicyBenefit
        PolicyBenefitDTO policyBenefitDTO = policyBenefitMapper.toDto(policyBenefit);
        restPolicyBenefitMockMvc.perform(post("/api/policy-benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyBenefitDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyBenefit in the database
        List<PolicyBenefit> policyBenefitList = policyBenefitRepository.findAll();
        assertThat(policyBenefitList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyBenefit testPolicyBenefit = policyBenefitList.get(policyBenefitList.size() - 1);
        assertThat(testPolicyBenefit.getBenefitRate()).isEqualTo(DEFAULT_BENEFIT_RATE);
        assertThat(testPolicyBenefit.getBenefitValue()).isEqualTo(DEFAULT_BENEFIT_VALUE);
        assertThat(testPolicyBenefit.getBenefitMinValue()).isEqualTo(DEFAULT_BENEFIT_MIN_VALUE);
    }

    @Test
    @Transactional
    public void createPolicyBenefitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyBenefitRepository.findAll().size();

        // Create the PolicyBenefit with an existing ID
        policyBenefit.setId(1L);
        PolicyBenefitDTO policyBenefitDTO = policyBenefitMapper.toDto(policyBenefit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyBenefitMockMvc.perform(post("/api/policy-benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyBenefitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PolicyBenefit in the database
        List<PolicyBenefit> policyBenefitList = policyBenefitRepository.findAll();
        assertThat(policyBenefitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPolicyBenefits() throws Exception {
        // Initialize the database
        policyBenefitRepository.saveAndFlush(policyBenefit);

        // Get all the policyBenefitList
        restPolicyBenefitMockMvc.perform(get("/api/policy-benefits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyBenefit.getId().intValue())))
            .andExpect(jsonPath("$.[*].benefitRate").value(hasItem(DEFAULT_BENEFIT_RATE.toString())))
            .andExpect(jsonPath("$.[*].benefitValue").value(hasItem(DEFAULT_BENEFIT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].benefitMinValue").value(hasItem(DEFAULT_BENEFIT_MIN_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPolicyBenefit() throws Exception {
        // Initialize the database
        policyBenefitRepository.saveAndFlush(policyBenefit);

        // Get the policyBenefit
        restPolicyBenefitMockMvc.perform(get("/api/policy-benefits/{id}", policyBenefit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policyBenefit.getId().intValue()))
            .andExpect(jsonPath("$.benefitRate").value(DEFAULT_BENEFIT_RATE.toString()))
            .andExpect(jsonPath("$.benefitValue").value(DEFAULT_BENEFIT_VALUE.toString()))
            .andExpect(jsonPath("$.benefitMinValue").value(DEFAULT_BENEFIT_MIN_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicyBenefit() throws Exception {
        // Get the policyBenefit
        restPolicyBenefitMockMvc.perform(get("/api/policy-benefits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicyBenefit() throws Exception {
        // Initialize the database
        policyBenefitRepository.saveAndFlush(policyBenefit);

        int databaseSizeBeforeUpdate = policyBenefitRepository.findAll().size();

        // Update the policyBenefit
        PolicyBenefit updatedPolicyBenefit = policyBenefitRepository.findById(policyBenefit.getId()).get();
        // Disconnect from session so that the updates on updatedPolicyBenefit are not directly saved in db
        em.detach(updatedPolicyBenefit);
        updatedPolicyBenefit
            .benefitRate(UPDATED_BENEFIT_RATE)
            .benefitValue(UPDATED_BENEFIT_VALUE)
            .benefitMinValue(UPDATED_BENEFIT_MIN_VALUE);
        PolicyBenefitDTO policyBenefitDTO = policyBenefitMapper.toDto(updatedPolicyBenefit);

        restPolicyBenefitMockMvc.perform(put("/api/policy-benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyBenefitDTO)))
            .andExpect(status().isOk());

        // Validate the PolicyBenefit in the database
        List<PolicyBenefit> policyBenefitList = policyBenefitRepository.findAll();
        assertThat(policyBenefitList).hasSize(databaseSizeBeforeUpdate);
        PolicyBenefit testPolicyBenefit = policyBenefitList.get(policyBenefitList.size() - 1);
        assertThat(testPolicyBenefit.getBenefitRate()).isEqualTo(UPDATED_BENEFIT_RATE);
        assertThat(testPolicyBenefit.getBenefitValue()).isEqualTo(UPDATED_BENEFIT_VALUE);
        assertThat(testPolicyBenefit.getBenefitMinValue()).isEqualTo(UPDATED_BENEFIT_MIN_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicyBenefit() throws Exception {
        int databaseSizeBeforeUpdate = policyBenefitRepository.findAll().size();

        // Create the PolicyBenefit
        PolicyBenefitDTO policyBenefitDTO = policyBenefitMapper.toDto(policyBenefit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyBenefitMockMvc.perform(put("/api/policy-benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyBenefitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PolicyBenefit in the database
        List<PolicyBenefit> policyBenefitList = policyBenefitRepository.findAll();
        assertThat(policyBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePolicyBenefit() throws Exception {
        // Initialize the database
        policyBenefitRepository.saveAndFlush(policyBenefit);

        int databaseSizeBeforeDelete = policyBenefitRepository.findAll().size();

        // Delete the policyBenefit
        restPolicyBenefitMockMvc.perform(delete("/api/policy-benefits/{id}", policyBenefit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PolicyBenefit> policyBenefitList = policyBenefitRepository.findAll();
        assertThat(policyBenefitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyBenefit.class);
        PolicyBenefit policyBenefit1 = new PolicyBenefit();
        policyBenefit1.setId(1L);
        PolicyBenefit policyBenefit2 = new PolicyBenefit();
        policyBenefit2.setId(policyBenefit1.getId());
        assertThat(policyBenefit1).isEqualTo(policyBenefit2);
        policyBenefit2.setId(2L);
        assertThat(policyBenefit1).isNotEqualTo(policyBenefit2);
        policyBenefit1.setId(null);
        assertThat(policyBenefit1).isNotEqualTo(policyBenefit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyBenefitDTO.class);
        PolicyBenefitDTO policyBenefitDTO1 = new PolicyBenefitDTO();
        policyBenefitDTO1.setId(1L);
        PolicyBenefitDTO policyBenefitDTO2 = new PolicyBenefitDTO();
        assertThat(policyBenefitDTO1).isNotEqualTo(policyBenefitDTO2);
        policyBenefitDTO2.setId(policyBenefitDTO1.getId());
        assertThat(policyBenefitDTO1).isEqualTo(policyBenefitDTO2);
        policyBenefitDTO2.setId(2L);
        assertThat(policyBenefitDTO1).isNotEqualTo(policyBenefitDTO2);
        policyBenefitDTO1.setId(null);
        assertThat(policyBenefitDTO1).isNotEqualTo(policyBenefitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(policyBenefitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(policyBenefitMapper.fromId(null)).isNull();
    }
}
