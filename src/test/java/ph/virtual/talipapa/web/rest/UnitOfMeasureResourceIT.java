package ph.virtual.talipapa.web.rest;

import ph.virtual.talipapa.VirtualTalipapaApp;
import ph.virtual.talipapa.domain.UnitOfMeasure;
import ph.virtual.talipapa.repository.UnitOfMeasureRepository;
import ph.virtual.talipapa.service.UnitOfMeasureService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UnitOfMeasureResource} REST controller.
 */
@SpringBootTest(classes = VirtualTalipapaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class UnitOfMeasureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    private UnitOfMeasureService unitOfMeasureService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnitOfMeasureMockMvc;

    private UnitOfMeasure unitOfMeasure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnitOfMeasure createEntity(EntityManager em) {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure()
            .name(DEFAULT_NAME)
            .abbreviation(DEFAULT_ABBREVIATION);
        return unitOfMeasure;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnitOfMeasure createUpdatedEntity(EntityManager em) {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure()
            .name(UPDATED_NAME)
            .abbreviation(UPDATED_ABBREVIATION);
        return unitOfMeasure;
    }

    @BeforeEach
    public void initTest() {
        unitOfMeasure = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnitOfMeasure() throws Exception {
        int databaseSizeBeforeCreate = unitOfMeasureRepository.findAll().size();

        // Create the UnitOfMeasure
        restUnitOfMeasureMockMvc.perform(post("/api/unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitOfMeasure)))
            .andExpect(status().isCreated());

        // Validate the UnitOfMeasure in the database
        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeCreate + 1);
        UnitOfMeasure testUnitOfMeasure = unitOfMeasureList.get(unitOfMeasureList.size() - 1);
        assertThat(testUnitOfMeasure.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUnitOfMeasure.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
    }

    @Test
    @Transactional
    public void createUnitOfMeasureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitOfMeasureRepository.findAll().size();

        // Create the UnitOfMeasure with an existing ID
        unitOfMeasure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitOfMeasureMockMvc.perform(post("/api/unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitOfMeasure)))
            .andExpect(status().isBadRequest());

        // Validate the UnitOfMeasure in the database
        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitOfMeasureRepository.findAll().size();
        // set the field null
        unitOfMeasure.setName(null);

        // Create the UnitOfMeasure, which fails.

        restUnitOfMeasureMockMvc.perform(post("/api/unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitOfMeasure)))
            .andExpect(status().isBadRequest());

        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAbbreviationIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitOfMeasureRepository.findAll().size();
        // set the field null
        unitOfMeasure.setAbbreviation(null);

        // Create the UnitOfMeasure, which fails.

        restUnitOfMeasureMockMvc.perform(post("/api/unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitOfMeasure)))
            .andExpect(status().isBadRequest());

        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnitOfMeasures() throws Exception {
        // Initialize the database
        unitOfMeasureRepository.saveAndFlush(unitOfMeasure);

        // Get all the unitOfMeasureList
        restUnitOfMeasureMockMvc.perform(get("/api/unit-of-measures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitOfMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));
    }
    
    @Test
    @Transactional
    public void getUnitOfMeasure() throws Exception {
        // Initialize the database
        unitOfMeasureRepository.saveAndFlush(unitOfMeasure);

        // Get the unitOfMeasure
        restUnitOfMeasureMockMvc.perform(get("/api/unit-of-measures/{id}", unitOfMeasure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unitOfMeasure.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION));
    }

    @Test
    @Transactional
    public void getNonExistingUnitOfMeasure() throws Exception {
        // Get the unitOfMeasure
        restUnitOfMeasureMockMvc.perform(get("/api/unit-of-measures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnitOfMeasure() throws Exception {
        // Initialize the database
        unitOfMeasureService.save(unitOfMeasure);

        int databaseSizeBeforeUpdate = unitOfMeasureRepository.findAll().size();

        // Update the unitOfMeasure
        UnitOfMeasure updatedUnitOfMeasure = unitOfMeasureRepository.findById(unitOfMeasure.getId()).get();
        // Disconnect from session so that the updates on updatedUnitOfMeasure are not directly saved in db
        em.detach(updatedUnitOfMeasure);
        updatedUnitOfMeasure
            .name(UPDATED_NAME)
            .abbreviation(UPDATED_ABBREVIATION);

        restUnitOfMeasureMockMvc.perform(put("/api/unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnitOfMeasure)))
            .andExpect(status().isOk());

        // Validate the UnitOfMeasure in the database
        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeUpdate);
        UnitOfMeasure testUnitOfMeasure = unitOfMeasureList.get(unitOfMeasureList.size() - 1);
        assertThat(testUnitOfMeasure.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUnitOfMeasure.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void updateNonExistingUnitOfMeasure() throws Exception {
        int databaseSizeBeforeUpdate = unitOfMeasureRepository.findAll().size();

        // Create the UnitOfMeasure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitOfMeasureMockMvc.perform(put("/api/unit-of-measures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitOfMeasure)))
            .andExpect(status().isBadRequest());

        // Validate the UnitOfMeasure in the database
        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnitOfMeasure() throws Exception {
        // Initialize the database
        unitOfMeasureService.save(unitOfMeasure);

        int databaseSizeBeforeDelete = unitOfMeasureRepository.findAll().size();

        // Delete the unitOfMeasure
        restUnitOfMeasureMockMvc.perform(delete("/api/unit-of-measures/{id}", unitOfMeasure.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
