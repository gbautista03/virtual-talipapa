package ph.virtual.talipapa.web.rest;

import ph.virtual.talipapa.VirtualTalipapaApp;
import ph.virtual.talipapa.domain.LineItem;
import ph.virtual.talipapa.repository.LineItemRepository;
import ph.virtual.talipapa.service.LineItemService;

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
 * Integration tests for the {@link LineItemResource} REST controller.
 */
@SpringBootTest(classes = VirtualTalipapaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class LineItemResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private LineItemService lineItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLineItemMockMvc;

    private LineItem lineItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LineItem createEntity(EntityManager em) {
        LineItem lineItem = new LineItem()
            .quantity(DEFAULT_QUANTITY);
        return lineItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LineItem createUpdatedEntity(EntityManager em) {
        LineItem lineItem = new LineItem()
            .quantity(UPDATED_QUANTITY);
        return lineItem;
    }

    @BeforeEach
    public void initTest() {
        lineItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createLineItem() throws Exception {
        int databaseSizeBeforeCreate = lineItemRepository.findAll().size();

        // Create the LineItem
        restLineItemMockMvc.perform(post("/api/line-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lineItem)))
            .andExpect(status().isCreated());

        // Validate the LineItem in the database
        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeCreate + 1);
        LineItem testLineItem = lineItemList.get(lineItemList.size() - 1);
        assertThat(testLineItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createLineItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lineItemRepository.findAll().size();

        // Create the LineItem with an existing ID
        lineItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLineItemMockMvc.perform(post("/api/line-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lineItem)))
            .andExpect(status().isBadRequest());

        // Validate the LineItem in the database
        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = lineItemRepository.findAll().size();
        // set the field null
        lineItem.setQuantity(null);

        // Create the LineItem, which fails.

        restLineItemMockMvc.perform(post("/api/line-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lineItem)))
            .andExpect(status().isBadRequest());

        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLineItems() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);

        // Get all the lineItemList
        restLineItemMockMvc.perform(get("/api/line-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getLineItem() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);

        // Get the lineItem
        restLineItemMockMvc.perform(get("/api/line-items/{id}", lineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lineItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingLineItem() throws Exception {
        // Get the lineItem
        restLineItemMockMvc.perform(get("/api/line-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLineItem() throws Exception {
        // Initialize the database
        lineItemService.save(lineItem);

        int databaseSizeBeforeUpdate = lineItemRepository.findAll().size();

        // Update the lineItem
        LineItem updatedLineItem = lineItemRepository.findById(lineItem.getId()).get();
        // Disconnect from session so that the updates on updatedLineItem are not directly saved in db
        em.detach(updatedLineItem);
        updatedLineItem
            .quantity(UPDATED_QUANTITY);

        restLineItemMockMvc.perform(put("/api/line-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLineItem)))
            .andExpect(status().isOk());

        // Validate the LineItem in the database
        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeUpdate);
        LineItem testLineItem = lineItemList.get(lineItemList.size() - 1);
        assertThat(testLineItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingLineItem() throws Exception {
        int databaseSizeBeforeUpdate = lineItemRepository.findAll().size();

        // Create the LineItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLineItemMockMvc.perform(put("/api/line-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lineItem)))
            .andExpect(status().isBadRequest());

        // Validate the LineItem in the database
        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLineItem() throws Exception {
        // Initialize the database
        lineItemService.save(lineItem);

        int databaseSizeBeforeDelete = lineItemRepository.findAll().size();

        // Delete the lineItem
        restLineItemMockMvc.perform(delete("/api/line-items/{id}", lineItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
