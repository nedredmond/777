package io.zcw.zipmint.web.rest;

import io.zcw.zipmint.Application;

import io.zcw.zipmint.domain.BudgetItem;
import io.zcw.zipmint.repository.BudgetItemRepository;
import io.zcw.zipmint.repository.TransactionRepository;
import io.zcw.zipmint.service.BudgetItemService;
import io.zcw.zipmint.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.zcw.zipmint.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.zcw.zipmint.domain.enumeration.Category;
/**
 * Test class for the BudgetItemResource REST controller.
 *
 * @see BudgetItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BudgetItemResourceIntTest {

    private static final Double DEFAULT_EXPECTED_SPENDING = 1.0;
    private static final Double UPDATED_EXPECTED_SPENDING = 2.0;

    private static final Double DEFAULT_ACTUAL_SPENDING = 1.0;
    private static final Double UPDATED_ACTUAL_SPENDING = 2.0;

    private static final Category DEFAULT_CATEGORY = Category.RENT;
    private static final Category UPDATED_CATEGORY = Category.FOOD;

    @Autowired
    private BudgetItemService budgetItemService;

    @Autowired
    private BudgetItemRepository budgetItemRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator defaultValidator;

    private MockMvc restBudgetItemMockMvc;

    private BudgetItem budgetItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BudgetItemResource budgetItemResource = new BudgetItemResource(budgetItemService);
        this.restBudgetItemMockMvc = MockMvcBuilders.standaloneSetup(budgetItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(defaultValidator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BudgetItem createEntity(EntityManager em) {
        BudgetItem budgetItem = new BudgetItem()
            .expectedSpending(DEFAULT_EXPECTED_SPENDING)
            .actualSpending(DEFAULT_ACTUAL_SPENDING)
            .category(DEFAULT_CATEGORY);
        return budgetItem;
    }

    @Before
    public void initTest() {
        budgetItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createBudgetItem() throws Exception {
        int databaseSizeBeforeCreate = budgetItemRepository.findAll().size();

        // Create the BudgetItem
        restBudgetItemMockMvc.perform(post("/api/budget-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetItem)))
            .andExpect(status().isCreated());

        // Validate the BudgetItem in the database
        List<BudgetItem> budgetItemList = budgetItemRepository.findAll();
        assertThat(budgetItemList).hasSize(databaseSizeBeforeCreate + 1);
        BudgetItem testBudgetItem = budgetItemList.get(budgetItemList.size() - 1);
        assertThat(testBudgetItem.getExpectedSpending()).isEqualTo(DEFAULT_EXPECTED_SPENDING);
        assertThat(testBudgetItem.getActualSpending()).isEqualTo(DEFAULT_ACTUAL_SPENDING);
        assertThat(testBudgetItem.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    public void createBudgetItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = budgetItemRepository.findAll().size();

        // Create the BudgetItem with an existing ID
        budgetItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBudgetItemMockMvc.perform(post("/api/budget-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetItem)))
            .andExpect(status().isBadRequest());

        // Validate the BudgetItem in the database
        List<BudgetItem> budgetItemList = budgetItemRepository.findAll();
        assertThat(budgetItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBudgetItems() throws Exception {
        // Initialize the database
        budgetItemRepository.saveAndFlush(budgetItem);

        // Get all the budgetItemList
        restBudgetItemMockMvc.perform(get("/api/budget-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(budgetItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].expectedSpending").value(hasItem(DEFAULT_EXPECTED_SPENDING.intValue())))
            .andExpect(jsonPath("$.[*].actualSpending").value(hasItem(DEFAULT_ACTUAL_SPENDING.intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }
    
    @Test
    @Transactional
    public void getBudgetItem() throws Exception {
        // Initialize the database
        budgetItemRepository.saveAndFlush(budgetItem);

        // Get the budgetItem
        restBudgetItemMockMvc.perform(get("/api/budget-items/{id}", budgetItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(budgetItem.getId().intValue()))
            .andExpect(jsonPath("$.expectedSpending").value(DEFAULT_EXPECTED_SPENDING.intValue()))
            .andExpect(jsonPath("$.actualSpending").value(DEFAULT_ACTUAL_SPENDING.intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBudgetItem() throws Exception {
        // Get the budgetItem
        restBudgetItemMockMvc.perform(get("/api/budget-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBudgetItem() throws Exception {
        // Initialize the database
        budgetItemRepository.saveAndFlush(budgetItem);

        int databaseSizeBeforeUpdate = budgetItemRepository.findAll().size();

        // Update the budgetItem
        BudgetItem updatedBudgetItem = budgetItemRepository.findById(budgetItem.getId()).get();
        // Disconnect from session so that the updates on updatedBudgetItem are not directly saved in db
        em.detach(updatedBudgetItem);
        updatedBudgetItem
            .expectedSpending(UPDATED_EXPECTED_SPENDING)
            .actualSpending(UPDATED_ACTUAL_SPENDING)
            .category(UPDATED_CATEGORY);

        restBudgetItemMockMvc.perform(put("/api/budget-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBudgetItem)))
            .andExpect(status().isOk());

        // Validate the BudgetItem in the database
        List<BudgetItem> budgetItemList = budgetItemRepository.findAll();
        assertThat(budgetItemList).hasSize(databaseSizeBeforeUpdate);
        BudgetItem testBudgetItem = budgetItemList.get(budgetItemList.size() - 1);
        assertThat(testBudgetItem.getExpectedSpending()).isEqualTo(UPDATED_EXPECTED_SPENDING);
        assertThat(testBudgetItem.getActualSpending()).isEqualTo(UPDATED_ACTUAL_SPENDING);
        assertThat(testBudgetItem.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingBudgetItem() throws Exception {
        int databaseSizeBeforeUpdate = budgetItemRepository.findAll().size();

        // Create the BudgetItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBudgetItemMockMvc.perform(put("/api/budget-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetItem)))
            .andExpect(status().isBadRequest());

        // Validate the BudgetItem in the database
        List<BudgetItem> budgetItemList = budgetItemRepository.findAll();
        assertThat(budgetItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBudgetItem() throws Exception {
        // Initialize the database
        budgetItemRepository.saveAndFlush(budgetItem);

        int databaseSizeBeforeDelete = budgetItemRepository.findAll().size();

        // Get the budgetItem
        restBudgetItemMockMvc.perform(delete("/api/budget-items/{id}", budgetItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BudgetItem> budgetItemList = budgetItemRepository.findAll();
        assertThat(budgetItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BudgetItem.class);
        BudgetItem budgetItem1 = new BudgetItem();
        budgetItem1.setId(1L);
        BudgetItem budgetItem2 = new BudgetItem();
        budgetItem2.setId(budgetItem1.getId());
        assertThat(budgetItem1).isEqualTo(budgetItem2);
        budgetItem2.setId(2L);
        assertThat(budgetItem1).isNotEqualTo(budgetItem2);
        budgetItem1.setId(null);
        assertThat(budgetItem1).isNotEqualTo(budgetItem2);
    }

}
