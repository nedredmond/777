package io.zcw.zipmint.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.netflix.ribbon.proxy.annotation.Http;
import io.zcw.zipmint.domain.MoneyAccount;
import io.zcw.zipmint.domain.Transaction;
import io.zcw.zipmint.repository.TransactionRepository;
import io.zcw.zipmint.service.TransactionService;
import io.zcw.zipmint.web.rest.errors.BadRequestAlertException;
import io.zcw.zipmint.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Transaction.
 */
@RestController
@RequestMapping("/api")
public class TransactionResource {

    private final Logger log = LoggerFactory.getLogger(TransactionResource.class);

    // private static final String ENTITY_NAME = "transaction";

    private final TransactionService transactionService;

    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * POST  /transactions : Create a new transaction.
     *
     * @param transaction the transaction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transaction, or with status 400 (Bad Request) if the transaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transactions")
    @Timed
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws URISyntaxException {
        log.debug("REST request to save Transaction : {}", transaction);
        return transactionService.createTransaction(transaction);
    }

    /**
     * PUT  /transactions : Updates an existing transaction.
     *
     * @param transaction the transaction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transaction,
     * or with status 400 (Bad Request) if the transaction is not valid,
     * or with status 500 (Internal Server Error) if the transaction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transactions")
    @Timed
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) throws URISyntaxException {
        log.debug("REST request to update Transaction : {}", transaction);
        return transactionService.updateTransaction(transaction);
    }

    /**
     * GET  /transactions : get all the transactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transactions in body
     */
    @GetMapping("/transactions")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getAllTransactions() {
        log.debug("REST request to get all Transactions");
        return new ResponseEntity<>(transactionService.getSortedByDate(), HttpStatus.OK);
    }

    /**
     * GET  /transactions/:id : get the "id" transaction.
     *
     * @param id the id of the transaction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transaction, or with status 404 (Not Found)
     */
    @GetMapping("/transactions/{id}")
    @Timed
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        log.debug("REST request to get Transaction : {}", id);
       return transactionService.getTransaction(id);
    }

    /**
     * DELETE  /transactions/:id : delete the "id" transaction.
     *
     * @param id the id of the transaction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        log.debug("REST request to delete Transaction : {}", id);
        return transactionService.deleteTransaction(id);
    }

    @GetMapping("/transactions/debit")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getDebitTransactions() {
        log.debug("REST request to get debit Transactions");
        return new ResponseEntity<>(transactionService.getDebitTransactions(), HttpStatus.OK);
    }

    @GetMapping("/transactions/credit")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getCreditTransactions() {
        log.debug("REST request to get credit Transactions");
        return new ResponseEntity<>(transactionService.getCreditTransactions(), HttpStatus.OK);
    }

    @GetMapping("/transactions/by_cat")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getSortedByCategory() {
        log.debug("REST request to sort Transactions by Category");
        return new ResponseEntity<>(transactionService.getSortedByCategory(), HttpStatus.OK);
    }

    @GetMapping("/transactions/by_desc")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getSortedByDescription() {
        log.debug("REST request to sort Transactions by Description");
        return new ResponseEntity<>(transactionService.getSortedByDescription(), HttpStatus.OK);
    }

    @GetMapping("/transactions/by_amount")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getSortedByAmount() {
        log.debug("REST request to sort Transactions by Amount");
        return new ResponseEntity<>(transactionService.getSortedByAmount(), HttpStatus.OK);
    }

    @GetMapping("/transactions/by_account")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getSortedByAccount() {
        log.debug("REST request to sort Transactions by Account");
        return new ResponseEntity<>(transactionService.getSortedByAccount(), HttpStatus.OK);
    }

    @GetMapping("/transactions/accounts")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getDistinctAccounts() {
        log.debug("REST request to get distinct Accounts");
        return new ResponseEntity<>(transactionService.getDistinctAccounts(), HttpStatus.OK);
    }

    @GetMapping("/transactions/moneyAccount/{id}")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getTransactionsByAccount(@PathVariable Long id) {
        log.debug("REST request to get Transactions by Account");
        return new ResponseEntity<>(transactionService.getTransactionsByAccount(id), HttpStatus.OK);
    }

    @GetMapping("/transactions/searchQuery")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getSearchResults(String searchQuery) {
        log.debug("REST request to filter by search query");
        return new ResponseEntity<>(transactionService.searchTransaction(searchQuery), HttpStatus.OK);
    }

    @GetMapping(value = "/transactions/recent")
    @Timed
    public ResponseEntity<Iterable<Transaction>> getRecentTransactions(){
        log.debug("REST request to get recent transactions");
        return new ResponseEntity<>(transactionService.getRecentTransaction(), HttpStatus.OK);
    }
}
