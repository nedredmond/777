package io.zcw.zipmint.web.rest;

import io.zcw.zipmint.Application;

import io.zcw.zipmint.domain.Bills;
import io.zcw.zipmint.repository.BillsRepository;
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
 * Test class for the BillsResource REST controller.
 *
 * @see BillsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BillsResourceIntTest {

    private static final Long DEFAULT_PAYMENT_TOTAL = 1L;
    private static final Long UPDATED_PAYMENT_TOTAL = 2L;

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_PAYMENT_AMOUNT = 2D;

    private static final Boolean DEFAULT_AUTO_PAY = false;
    private static final Boolean UPDATED_AUTO_PAY = true;

    @Autowired
    private BillsRepository billsRepository;

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

    private MockMvc restBillsMockMvc;

    private Bills bills;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillsResource billsResource = new BillsResource(billsRepository);
        this.restBillsMockMvc = MockMvcBuilders.standaloneSetup(billsResource)
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
    public static Bills createEntity(EntityManager em) {
        Bills bills = new Bills()
            .paymentTotal(DEFAULT_PAYMENT_TOTAL)
            .companyName(DEFAULT_COMPANY_NAME)
            .dueDate(DEFAULT_DUE_DATE)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .autoPay(DEFAULT_AUTO_PAY);
        return bills;
    }

    @Before
    public void initTest() {
        bills = createEntity(em);
    }

    @Test
    @Transactional
    public void createBills() throws Exception {
        int databaseSizeBeforeCreate = billsRepository.findAll().size();

        // Create the Bills
        restBillsMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bills)))
            .andExpect(status().isCreated());

        // Validate the Bills in the database
        List<Bills> billsList = billsRepository.findAll();
        assertThat(billsList).hasSize(databaseSizeBeforeCreate + 1);
        Bills testBills = billsList.get(billsList.size() - 1);
        assertThat(testBills.getPaymentTotal()).isEqualTo(DEFAULT_PAYMENT_TOTAL);
        assertThat(testBills.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testBills.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testBills.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testBills.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testBills.isAutoPay()).isEqualTo(DEFAULT_AUTO_PAY);
    }

    @Test
    @Transactional
    public void createBillsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billsRepository.findAll().size();

        // Create the Bills with an existing ID
        bills.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillsMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bills)))
            .andExpect(status().isBadRequest());

        // Validate the Bills in the database
        List<Bills> billsList = billsRepository.findAll();
        assertThat(billsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBills() throws Exception {
        // Initialize the database
        billsRepository.saveAndFlush(bills);

        // Get all the billsList
        restBillsMockMvc.perform(get("/api/bills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bills.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentTotal").value(hasItem(DEFAULT_PAYMENT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].autoPay").value(hasItem(DEFAULT_AUTO_PAY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBills() throws Exception {
        // Initialize the database
        billsRepository.saveAndFlush(bills);

        // Get the bills
        restBillsMockMvc.perform(get("/api/bills/{id}", bills.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bills.getId().intValue()))
            .andExpect(jsonPath("$.paymentTotal").value(DEFAULT_PAYMENT_TOTAL.intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.autoPay").value(DEFAULT_AUTO_PAY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBills() throws Exception {
        // Get the bills
        restBillsMockMvc.perform(get("/api/bills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBills() throws Exception {
        // Initialize the database
        billsRepository.saveAndFlush(bills);

        int databaseSizeBeforeUpdate = billsRepository.findAll().size();

        // Update the bills
        Bills updatedBills = billsRepository.findById(bills.getId()).get();
        // Disconnect from session so that the updates on updatedBills are not directly saved in db
        em.detach(updatedBills);
        updatedBills
            .paymentTotal(UPDATED_PAYMENT_TOTAL)
            .companyName(UPDATED_COMPANY_NAME)
            .dueDate(UPDATED_DUE_DATE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .autoPay(UPDATED_AUTO_PAY);

        restBillsMockMvc.perform(put("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBills)))
            .andExpect(status().isOk());

        // Validate the Bills in the database
        List<Bills> billsList = billsRepository.findAll();
        assertThat(billsList).hasSize(databaseSizeBeforeUpdate);
        Bills testBills = billsList.get(billsList.size() - 1);
        assertThat(testBills.getPaymentTotal()).isEqualTo(UPDATED_PAYMENT_TOTAL);
        assertThat(testBills.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testBills.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testBills.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testBills.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testBills.isAutoPay()).isEqualTo(UPDATED_AUTO_PAY);
    }

    @Test
    @Transactional
    public void updateNonExistingBills() throws Exception {
        int databaseSizeBeforeUpdate = billsRepository.findAll().size();

        // Create the Bills

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillsMockMvc.perform(put("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bills)))
            .andExpect(status().isBadRequest());

        // Validate the Bills in the database
        List<Bills> billsList = billsRepository.findAll();
        assertThat(billsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBills() throws Exception {
        // Initialize the database
        billsRepository.saveAndFlush(bills);

        int databaseSizeBeforeDelete = billsRepository.findAll().size();

        // Get the bills
        restBillsMockMvc.perform(delete("/api/bills/{id}", bills.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bills> billsList = billsRepository.findAll();
        assertThat(billsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bills.class);
        Bills bills1 = new Bills();
        bills1.setId(1L);
        Bills bills2 = new Bills();
        bills2.setId(bills1.getId());
        assertThat(bills1).isEqualTo(bills2);
        bills2.setId(2L);
        assertThat(bills1).isNotEqualTo(bills2);
        bills1.setId(null);
        assertThat(bills1).isNotEqualTo(bills2);
    }
}
