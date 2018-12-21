package io.zcw.zipmint.web.rest;

import io.zcw.zipmint.Application;

import io.zcw.zipmint.domain.BillItem;
import io.zcw.zipmint.repository.BillItemRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static io.zcw.zipmint.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BillItemResource REST controller.
 *
 * @see BillItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BillItemResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_PAYMENT_AMOUNT = 1L;
    private static final Long UPDATED_PAYMENT_AMOUNT = 2L;

    private static final Boolean DEFAULT_AUTO_PAY = false;
    private static final Boolean UPDATED_AUTO_PAY = true;

    @Autowired
    private BillItemRepository billItemRepository;

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

    private MockMvc restBillItemMockMvc;

    private BillItem billItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillItemResource billItemResource = new BillItemResource(billItemRepository);
        this.restBillItemMockMvc = MockMvcBuilders.standaloneSetup(billItemResource)
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
    public static BillItem createEntity(EntityManager em) {
        BillItem billItem = new BillItem()
            .companyName(DEFAULT_COMPANY_NAME)
            .dueDate(DEFAULT_DUE_DATE)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .autoPay(DEFAULT_AUTO_PAY);
        return billItem;
    }

    @Before
    public void initTest() {
        billItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillItem() throws Exception {
        int databaseSizeBeforeCreate = billItemRepository.findAll().size();

        // Create the BillItem
        restBillItemMockMvc.perform(post("/api/bill-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billItem)))
            .andExpect(status().isCreated());

        // Validate the BillItem in the database
        List<BillItem> billItemList = billItemRepository.findAll();
        assertThat(billItemList).hasSize(databaseSizeBeforeCreate + 1);
        BillItem testBillItem = billItemList.get(billItemList.size() - 1);
        assertThat(testBillItem.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testBillItem.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testBillItem.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testBillItem.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testBillItem.isAutoPay()).isEqualTo(DEFAULT_AUTO_PAY);
    }

    @Test
    @Transactional
    public void createBillItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billItemRepository.findAll().size();

        // Create the BillItem with an existing ID
        billItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillItemMockMvc.perform(post("/api/bill-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billItem)))
            .andExpect(status().isBadRequest());

        // Validate the BillItem in the database
        List<BillItem> billItemList = billItemRepository.findAll();
        assertThat(billItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBillItems() throws Exception {
        // Initialize the database
        billItemRepository.saveAndFlush(billItem);

        // Get all the billItemList
        restBillItemMockMvc.perform(get("/api/bill-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].autoPay").value(hasItem(DEFAULT_AUTO_PAY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBillItem() throws Exception {
        // Initialize the database
        billItemRepository.saveAndFlush(billItem);

        // Get the billItem
        restBillItemMockMvc.perform(get("/api/bill-items/{id}", billItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billItem.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.autoPay").value(DEFAULT_AUTO_PAY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBillItem() throws Exception {
        // Get the billItem
        restBillItemMockMvc.perform(get("/api/bill-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillItem() throws Exception {
        // Initialize the database
        billItemRepository.saveAndFlush(billItem);

        int databaseSizeBeforeUpdate = billItemRepository.findAll().size();

        // Update the billItem
        BillItem updatedBillItem = billItemRepository.findById(billItem.getId()).get();
        // Disconnect from session so that the updates on updatedBillItem are not directly saved in db
        em.detach(updatedBillItem);
        updatedBillItem
            .companyName(UPDATED_COMPANY_NAME)
            .dueDate(UPDATED_DUE_DATE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .autoPay(UPDATED_AUTO_PAY);

        restBillItemMockMvc.perform(put("/api/bill-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillItem)))
            .andExpect(status().isOk());

        // Validate the BillItem in the database
        List<BillItem> billItemList = billItemRepository.findAll();
        assertThat(billItemList).hasSize(databaseSizeBeforeUpdate);
        BillItem testBillItem = billItemList.get(billItemList.size() - 1);
        assertThat(testBillItem.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testBillItem.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testBillItem.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testBillItem.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testBillItem.isAutoPay()).isEqualTo(UPDATED_AUTO_PAY);
    }

    @Test
    @Transactional
    public void updateNonExistingBillItem() throws Exception {
        int databaseSizeBeforeUpdate = billItemRepository.findAll().size();

        // Create the BillItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillItemMockMvc.perform(put("/api/bill-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billItem)))
            .andExpect(status().isBadRequest());

        // Validate the BillItem in the database
        List<BillItem> billItemList = billItemRepository.findAll();
        assertThat(billItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillItem() throws Exception {
        // Initialize the database
        billItemRepository.saveAndFlush(billItem);

        int databaseSizeBeforeDelete = billItemRepository.findAll().size();

        // Get the billItem
        restBillItemMockMvc.perform(delete("/api/bill-items/{id}", billItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BillItem> billItemList = billItemRepository.findAll();
        assertThat(billItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillItem.class);
        BillItem billItem1 = new BillItem();
        billItem1.setId(1L);
        BillItem billItem2 = new BillItem();
        billItem2.setId(billItem1.getId());
        assertThat(billItem1).isEqualTo(billItem2);
        billItem2.setId(2L);
        assertThat(billItem1).isNotEqualTo(billItem2);
        billItem1.setId(null);
        assertThat(billItem1).isNotEqualTo(billItem2);
    }
}
