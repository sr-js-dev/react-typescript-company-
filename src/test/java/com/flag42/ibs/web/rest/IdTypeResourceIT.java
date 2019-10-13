package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.IdType;
import com.flag42.ibs.repository.IdTypeRepository;
import com.flag42.ibs.service.IdTypeService;
import com.flag42.ibs.service.dto.IdTypeDTO;
import com.flag42.ibs.service.mapper.IdTypeMapper;
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

/**
 * Integration tests for the {@Link IdTypeResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class IdTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private IdTypeRepository idTypeRepository;

    @Autowired
    private IdTypeMapper idTypeMapper;

    @Autowired
    private IdTypeService idTypeService;

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

    private MockMvc restIdTypeMockMvc;

    private IdType idType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IdTypeResource idTypeResource = new IdTypeResource(idTypeService);
        this.restIdTypeMockMvc = MockMvcBuilders.standaloneSetup(idTypeResource)
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
    public static IdType createEntity(EntityManager em) {
        IdType idType = new IdType()
            .name(DEFAULT_NAME);
        return idType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdType createUpdatedEntity(EntityManager em) {
        IdType idType = new IdType()
            .name(UPDATED_NAME);
        return idType;
    }

    @BeforeEach
    public void initTest() {
        idType = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdType() throws Exception {
        int databaseSizeBeforeCreate = idTypeRepository.findAll().size();

        // Create the IdType
        IdTypeDTO idTypeDTO = idTypeMapper.toDto(idType);
        restIdTypeMockMvc.perform(post("/api/id-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the IdType in the database
        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IdType testIdType = idTypeList.get(idTypeList.size() - 1);
        assertThat(testIdType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createIdTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = idTypeRepository.findAll().size();

        // Create the IdType with an existing ID
        idType.setId(1L);
        IdTypeDTO idTypeDTO = idTypeMapper.toDto(idType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdTypeMockMvc.perform(post("/api/id-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IdType in the database
        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = idTypeRepository.findAll().size();
        // set the field null
        idType.setName(null);

        // Create the IdType, which fails.
        IdTypeDTO idTypeDTO = idTypeMapper.toDto(idType);

        restIdTypeMockMvc.perform(post("/api/id-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idTypeDTO)))
            .andExpect(status().isBadRequest());

        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIdTypes() throws Exception {
        // Initialize the database
        idTypeRepository.saveAndFlush(idType);

        // Get all the idTypeList
        restIdTypeMockMvc.perform(get("/api/id-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getIdType() throws Exception {
        // Initialize the database
        idTypeRepository.saveAndFlush(idType);

        // Get the idType
        restIdTypeMockMvc.perform(get("/api/id-types/{id}", idType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(idType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIdType() throws Exception {
        // Get the idType
        restIdTypeMockMvc.perform(get("/api/id-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdType() throws Exception {
        // Initialize the database
        idTypeRepository.saveAndFlush(idType);

        int databaseSizeBeforeUpdate = idTypeRepository.findAll().size();

        // Update the idType
        IdType updatedIdType = idTypeRepository.findById(idType.getId()).get();
        // Disconnect from session so that the updates on updatedIdType are not directly saved in db
        em.detach(updatedIdType);
        updatedIdType
            .name(UPDATED_NAME);
        IdTypeDTO idTypeDTO = idTypeMapper.toDto(updatedIdType);

        restIdTypeMockMvc.perform(put("/api/id-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idTypeDTO)))
            .andExpect(status().isOk());

        // Validate the IdType in the database
        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeUpdate);
        IdType testIdType = idTypeList.get(idTypeList.size() - 1);
        assertThat(testIdType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingIdType() throws Exception {
        int databaseSizeBeforeUpdate = idTypeRepository.findAll().size();

        // Create the IdType
        IdTypeDTO idTypeDTO = idTypeMapper.toDto(idType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdTypeMockMvc.perform(put("/api/id-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IdType in the database
        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdType() throws Exception {
        // Initialize the database
        idTypeRepository.saveAndFlush(idType);

        int databaseSizeBeforeDelete = idTypeRepository.findAll().size();

        // Delete the idType
        restIdTypeMockMvc.perform(delete("/api/id-types/{id}", idType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdType.class);
        IdType idType1 = new IdType();
        idType1.setId(1L);
        IdType idType2 = new IdType();
        idType2.setId(idType1.getId());
        assertThat(idType1).isEqualTo(idType2);
        idType2.setId(2L);
        assertThat(idType1).isNotEqualTo(idType2);
        idType1.setId(null);
        assertThat(idType1).isNotEqualTo(idType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdTypeDTO.class);
        IdTypeDTO idTypeDTO1 = new IdTypeDTO();
        idTypeDTO1.setId(1L);
        IdTypeDTO idTypeDTO2 = new IdTypeDTO();
        assertThat(idTypeDTO1).isNotEqualTo(idTypeDTO2);
        idTypeDTO2.setId(idTypeDTO1.getId());
        assertThat(idTypeDTO1).isEqualTo(idTypeDTO2);
        idTypeDTO2.setId(2L);
        assertThat(idTypeDTO1).isNotEqualTo(idTypeDTO2);
        idTypeDTO1.setId(null);
        assertThat(idTypeDTO1).isNotEqualTo(idTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(idTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(idTypeMapper.fromId(null)).isNull();
    }
}
