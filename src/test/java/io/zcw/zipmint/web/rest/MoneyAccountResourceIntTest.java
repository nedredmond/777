package io.zcw.zipmint.web.rest;

import io.zcw.zipmint.Application;

import io.zcw.zipmint.domain.MoneyAccount;
import io.zcw.zipmint.repository.MoneyAccountRepository;
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

import io.zcw.zipmint.domain.enumeration.AccountType;
/**
 * Test class for the MoneyAccountResource REST controller.
 *
 * @see MoneyAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MoneyAccountResourceIntTest {

    private static final AccountType DEFAULT_TYPE = AccountType.CREDIT;
    private static final AccountType UPDATED_TYPE = AccountType.CHECKING;

    private static final Long DEFAULT_ACCOUNT_TOTAL = 1L;
    private static final Long UPDATED_ACCOUNT_TOTAL = 2L;

    private static final String DEFAULT_SIGN_IN = "AAAAAAAAAA";
    private static final String UPDATED_SIGN_IN = "BBBBBBBBBB";

    private static final String DEFAULT_PW = "AAAAAAAAAA";
    private static final String UPDATED_PW = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    @Autowired
    private MoneyAccountRepository moneyAccountRepository;

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

    private MockMvc restMoneyAccountMockMvc;

    private MoneyAccount moneyAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MoneyAccountResource moneyAccountResource = new MoneyAccountResource(moneyAccountRepository);
        this.restMoneyAccountMockMvc = MockMvcBuilders.standaloneSetup(moneyAccountResource)
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
    public static MoneyAccount createEntity(EntityManager em) {
        MoneyAccount moneyAccount = new MoneyAccount()
            .type(DEFAULT_TYPE)
            .accountTotal(DEFAULT_ACCOUNT_TOTAL)
            .signIn(DEFAULT_SIGN_IN)
            .pw(DEFAULT_PW)
            .bankName(DEFAULT_BANK_NAME);
        return moneyAccount;
    }

    @Before
    public void initTest() {
        moneyAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoneyAccount() throws Exception {
        int databaseSizeBeforeCreate = moneyAccountRepository.findAll().size();

        // Create the MoneyAccount
        restMoneyAccountMockMvc.perform(post("/api/money-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moneyAccount)))
            .andExpect(status().isCreated());

        // Validate the MoneyAccount in the database
        List<MoneyAccount> moneyAccountList = moneyAccountRepository.findAll();
        assertThat(moneyAccountList).hasSize(databaseSizeBeforeCreate + 1);
        MoneyAccount testMoneyAccount = moneyAccountList.get(moneyAccountList.size() - 1);
        assertThat(testMoneyAccount.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMoneyAccount.getAccountTotal()).isEqualTo(DEFAULT_ACCOUNT_TOTAL);
        assertThat(testMoneyAccount.getSignIn()).isEqualTo(DEFAULT_SIGN_IN);
        assertThat(testMoneyAccount.getPw()).isEqualTo(DEFAULT_PW);
        assertThat(testMoneyAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
    }

    @Test
    @Transactional
    public void createMoneyAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moneyAccountRepository.findAll().size();

        // Create the MoneyAccount with an existing ID
        moneyAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoneyAccountMockMvc.perform(post("/api/money-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moneyAccount)))
            .andExpect(status().isBadRequest());

        // Validate the MoneyAccount in the database
        List<MoneyAccount> moneyAccountList = moneyAccountRepository.findAll();
        assertThat(moneyAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMoneyAccounts() throws Exception {
        // Initialize the database
        moneyAccountRepository.saveAndFlush(moneyAccount);

        // Get all the moneyAccountList
        restMoneyAccountMockMvc.perform(get("/api/money-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moneyAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].accountTotal").value(hasItem(DEFAULT_ACCOUNT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].signIn").value(hasItem(DEFAULT_SIGN_IN.toString())))
            .andExpect(jsonPath("$.[*].pw").value(hasItem(DEFAULT_PW.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getMoneyAccount() throws Exception {
        // Initialize the database
        moneyAccountRepository.saveAndFlush(moneyAccount);

        // Get the moneyAccount
        restMoneyAccountMockMvc.perform(get("/api/money-accounts/{id}", moneyAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moneyAccount.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.accountTotal").value(DEFAULT_ACCOUNT_TOTAL.intValue()))
            .andExpect(jsonPath("$.signIn").value(DEFAULT_SIGN_IN.toString()))
            .andExpect(jsonPath("$.pw").value(DEFAULT_PW.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMoneyAccount() throws Exception {
        // Get the moneyAccount
        restMoneyAccountMockMvc.perform(get("/api/money-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoneyAccount() throws Exception {
        // Initialize the database
        moneyAccountRepository.saveAndFlush(moneyAccount);

        int databaseSizeBeforeUpdate = moneyAccountRepository.findAll().size();

        // Update the moneyAccount
        MoneyAccount updatedMoneyAccount = moneyAccountRepository.findById(moneyAccount.getId()).get();
        // Disconnect from session so that the updates on updatedMoneyAccount are not directly saved in db
        em.detach(updatedMoneyAccount);
        updatedMoneyAccount
            .type(UPDATED_TYPE)
            .accountTotal(UPDATED_ACCOUNT_TOTAL)
            .signIn(UPDATED_SIGN_IN)
            .pw(UPDATED_PW)
            .bankName(UPDATED_BANK_NAME);

        restMoneyAccountMockMvc.perform(put("/api/money-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMoneyAccount)))
            .andExpect(status().isOk());

        // Validate the MoneyAccount in the database
        List<MoneyAccount> moneyAccountList = moneyAccountRepository.findAll();
        assertThat(moneyAccountList).hasSize(databaseSizeBeforeUpdate);
        MoneyAccount testMoneyAccount = moneyAccountList.get(moneyAccountList.size() - 1);
        assertThat(testMoneyAccount.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMoneyAccount.getAccountTotal()).isEqualTo(UPDATED_ACCOUNT_TOTAL);
        assertThat(testMoneyAccount.getSignIn()).isEqualTo(UPDATED_SIGN_IN);
        assertThat(testMoneyAccount.getPw()).isEqualTo(UPDATED_PW);
        assertThat(testMoneyAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMoneyAccount() throws Exception {
        int databaseSizeBeforeUpdate = moneyAccountRepository.findAll().size();

        // Create the MoneyAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoneyAccountMockMvc.perform(put("/api/money-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moneyAccount)))
            .andExpect(status().isBadRequest());

        // Validate the MoneyAccount in the database
        List<MoneyAccount> moneyAccountList = moneyAccountRepository.findAll();
        assertThat(moneyAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMoneyAccount() throws Exception {
        // Initialize the database
        moneyAccountRepository.saveAndFlush(moneyAccount);

        int databaseSizeBeforeDelete = moneyAccountRepository.findAll().size();

        // Get the moneyAccount
        restMoneyAccountMockMvc.perform(delete("/api/money-accounts/{id}", moneyAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MoneyAccount> moneyAccountList = moneyAccountRepository.findAll();
        assertThat(moneyAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoneyAccount.class);
        MoneyAccount moneyAccount1 = new MoneyAccount();
        moneyAccount1.setId(1L);
        MoneyAccount moneyAccount2 = new MoneyAccount();
        moneyAccount2.setId(moneyAccount1.getId());
        assertThat(moneyAccount1).isEqualTo(moneyAccount2);
        moneyAccount2.setId(2L);
        assertThat(moneyAccount1).isNotEqualTo(moneyAccount2);
        moneyAccount1.setId(null);
        assertThat(moneyAccount1).isNotEqualTo(moneyAccount2);
    }
}
