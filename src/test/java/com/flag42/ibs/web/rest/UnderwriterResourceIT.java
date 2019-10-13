package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.Underwriter;
import com.flag42.ibs.repository.UnderwriterRepository;
import com.flag42.ibs.service.UnderwriterService;
import com.flag42.ibs.service.dto.UnderwriterDTO;
import com.flag42.ibs.service.mapper.UnderwriterMapper;
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
 * Integration tests for the {@Link UnderwriterResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class UnderwriterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_BINDER = "AAAAAAAAAA";
    private static final String UPDATED_BINDER = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

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

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private UnderwriterRepository underwriterRepository;

    @Autowired
    private UnderwriterMapper underwriterMapper;

    @Autowired
    private UnderwriterService underwriterService;

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

    private MockMvc restUnderwriterMockMvc;

    private Underwriter underwriter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnderwriterResource underwriterResource = new UnderwriterResource(underwriterService);
        this.restUnderwriterMockMvc = MockMvcBuilders.standaloneSetup(underwriterResource)
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
    public static Underwriter createEntity(EntityManager em) {
        Underwriter underwriter = new Underwriter()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .logo(DEFAULT_LOGO)
            .binder(DEFAULT_BINDER)
            .website(DEFAULT_WEBSITE)
            .contactPersion(DEFAULT_CONTACT_PERSION)
            .telephone(DEFAULT_TELEPHONE)
            .mobile(DEFAULT_MOBILE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .county(DEFAULT_COUNTY)
            .country(DEFAULT_COUNTRY)
            .pinNumber(DEFAULT_PIN_NUMBER)
            .status(DEFAULT_STATUS);
        return underwriter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Underwriter createUpdatedEntity(EntityManager em) {
        Underwriter underwriter = new Underwriter()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .logo(UPDATED_LOGO)
            .binder(UPDATED_BINDER)
            .website(UPDATED_WEBSITE)
            .contactPersion(UPDATED_CONTACT_PERSION)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .pinNumber(UPDATED_PIN_NUMBER)
            .status(UPDATED_STATUS);
        return underwriter;
    }

    @BeforeEach
    public void initTest() {
        underwriter = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnderwriter() throws Exception {
        int databaseSizeBeforeCreate = underwriterRepository.findAll().size();

        // Create the Underwriter
        UnderwriterDTO underwriterDTO = underwriterMapper.toDto(underwriter);
        restUnderwriterMockMvc.perform(post("/api/underwriters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(underwriterDTO)))
            .andExpect(status().isCreated());

        // Validate the Underwriter in the database
        List<Underwriter> underwriterList = underwriterRepository.findAll();
        assertThat(underwriterList).hasSize(databaseSizeBeforeCreate + 1);
        Underwriter testUnderwriter = underwriterList.get(underwriterList.size() - 1);
        assertThat(testUnderwriter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUnderwriter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUnderwriter.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testUnderwriter.getBinder()).isEqualTo(DEFAULT_BINDER);
        assertThat(testUnderwriter.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testUnderwriter.getContactPersion()).isEqualTo(DEFAULT_CONTACT_PERSION);
        assertThat(testUnderwriter.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testUnderwriter.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testUnderwriter.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUnderwriter.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUnderwriter.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testUnderwriter.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testUnderwriter.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testUnderwriter.getPinNumber()).isEqualTo(DEFAULT_PIN_NUMBER);
        assertThat(testUnderwriter.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createUnderwriterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = underwriterRepository.findAll().size();

        // Create the Underwriter with an existing ID
        underwriter.setId(1L);
        UnderwriterDTO underwriterDTO = underwriterMapper.toDto(underwriter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnderwriterMockMvc.perform(post("/api/underwriters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(underwriterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Underwriter in the database
        List<Underwriter> underwriterList = underwriterRepository.findAll();
        assertThat(underwriterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = underwriterRepository.findAll().size();
        // set the field null
        underwriter.setName(null);

        // Create the Underwriter, which fails.
        UnderwriterDTO underwriterDTO = underwriterMapper.toDto(underwriter);

        restUnderwriterMockMvc.perform(post("/api/underwriters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(underwriterDTO)))
            .andExpect(status().isBadRequest());

        List<Underwriter> underwriterList = underwriterRepository.findAll();
        assertThat(underwriterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnderwriters() throws Exception {
        // Initialize the database
        underwriterRepository.saveAndFlush(underwriter);

        // Get all the underwriterList
        restUnderwriterMockMvc.perform(get("/api/underwriters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(underwriter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())))
            .andExpect(jsonPath("$.[*].binder").value(hasItem(DEFAULT_BINDER.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].contactPersion").value(hasItem(DEFAULT_CONTACT_PERSION.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].pinNumber").value(hasItem(DEFAULT_PIN_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getUnderwriter() throws Exception {
        // Initialize the database
        underwriterRepository.saveAndFlush(underwriter);

        // Get the underwriter
        restUnderwriterMockMvc.perform(get("/api/underwriters/{id}", underwriter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(underwriter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()))
            .andExpect(jsonPath("$.binder").value(DEFAULT_BINDER.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.contactPersion").value(DEFAULT_CONTACT_PERSION.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.pinNumber").value(DEFAULT_PIN_NUMBER.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnderwriter() throws Exception {
        // Get the underwriter
        restUnderwriterMockMvc.perform(get("/api/underwriters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnderwriter() throws Exception {
        // Initialize the database
        underwriterRepository.saveAndFlush(underwriter);

        int databaseSizeBeforeUpdate = underwriterRepository.findAll().size();

        // Update the underwriter
        Underwriter updatedUnderwriter = underwriterRepository.findById(underwriter.getId()).get();
        // Disconnect from session so that the updates on updatedUnderwriter are not directly saved in db
        em.detach(updatedUnderwriter);
        updatedUnderwriter
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .logo(UPDATED_LOGO)
            .binder(UPDATED_BINDER)
            .website(UPDATED_WEBSITE)
            .contactPersion(UPDATED_CONTACT_PERSION)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .pinNumber(UPDATED_PIN_NUMBER)
            .status(UPDATED_STATUS);
        UnderwriterDTO underwriterDTO = underwriterMapper.toDto(updatedUnderwriter);

        restUnderwriterMockMvc.perform(put("/api/underwriters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(underwriterDTO)))
            .andExpect(status().isOk());

        // Validate the Underwriter in the database
        List<Underwriter> underwriterList = underwriterRepository.findAll();
        assertThat(underwriterList).hasSize(databaseSizeBeforeUpdate);
        Underwriter testUnderwriter = underwriterList.get(underwriterList.size() - 1);
        assertThat(testUnderwriter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUnderwriter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUnderwriter.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testUnderwriter.getBinder()).isEqualTo(UPDATED_BINDER);
        assertThat(testUnderwriter.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testUnderwriter.getContactPersion()).isEqualTo(UPDATED_CONTACT_PERSION);
        assertThat(testUnderwriter.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testUnderwriter.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUnderwriter.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUnderwriter.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUnderwriter.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testUnderwriter.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testUnderwriter.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testUnderwriter.getPinNumber()).isEqualTo(UPDATED_PIN_NUMBER);
        assertThat(testUnderwriter.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingUnderwriter() throws Exception {
        int databaseSizeBeforeUpdate = underwriterRepository.findAll().size();

        // Create the Underwriter
        UnderwriterDTO underwriterDTO = underwriterMapper.toDto(underwriter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnderwriterMockMvc.perform(put("/api/underwriters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(underwriterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Underwriter in the database
        List<Underwriter> underwriterList = underwriterRepository.findAll();
        assertThat(underwriterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnderwriter() throws Exception {
        // Initialize the database
        underwriterRepository.saveAndFlush(underwriter);

        int databaseSizeBeforeDelete = underwriterRepository.findAll().size();

        // Delete the underwriter
        restUnderwriterMockMvc.perform(delete("/api/underwriters/{id}", underwriter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Underwriter> underwriterList = underwriterRepository.findAll();
        assertThat(underwriterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Underwriter.class);
        Underwriter underwriter1 = new Underwriter();
        underwriter1.setId(1L);
        Underwriter underwriter2 = new Underwriter();
        underwriter2.setId(underwriter1.getId());
        assertThat(underwriter1).isEqualTo(underwriter2);
        underwriter2.setId(2L);
        assertThat(underwriter1).isNotEqualTo(underwriter2);
        underwriter1.setId(null);
        assertThat(underwriter1).isNotEqualTo(underwriter2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnderwriterDTO.class);
        UnderwriterDTO underwriterDTO1 = new UnderwriterDTO();
        underwriterDTO1.setId(1L);
        UnderwriterDTO underwriterDTO2 = new UnderwriterDTO();
        assertThat(underwriterDTO1).isNotEqualTo(underwriterDTO2);
        underwriterDTO2.setId(underwriterDTO1.getId());
        assertThat(underwriterDTO1).isEqualTo(underwriterDTO2);
        underwriterDTO2.setId(2L);
        assertThat(underwriterDTO1).isNotEqualTo(underwriterDTO2);
        underwriterDTO1.setId(null);
        assertThat(underwriterDTO1).isNotEqualTo(underwriterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(underwriterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(underwriterMapper.fromId(null)).isNull();
    }
}
