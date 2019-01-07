package io.zcw.zipmint.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.zcw.zipmint.domain.MoneyAccount;
import io.zcw.zipmint.repository.MoneyAccountRepository;
import io.zcw.zipmint.web.rest.errors.BadRequestAlertException;
import io.zcw.zipmint.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MoneyAccount.
 */
@RestController
@RequestMapping("/api")
public class MoneyAccountResource {

    private final Logger log = LoggerFactory.getLogger(MoneyAccountResource.class);

    private static final String ENTITY_NAME = "moneyAccount";

    private final MoneyAccountRepository moneyAccountRepository;

    public MoneyAccountResource(MoneyAccountRepository moneyAccountRepository) {
        this.moneyAccountRepository = moneyAccountRepository;
    }

    /**
     * POST  /money-accounts : Create a new moneyAccount.
     *
     * @param moneyAccount the moneyAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moneyAccount, or with status 400 (Bad Request) if the moneyAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/money-accounts")
    @Timed
    public ResponseEntity<MoneyAccount> createMoneyAccount(@RequestBody MoneyAccount moneyAccount) throws URISyntaxException {
        log.debug("REST request to save MoneyAccount : {}", moneyAccount);
        if (moneyAccount.getId() != null) {
            throw new BadRequestAlertException("A new moneyAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoneyAccount result = moneyAccountRepository.save(moneyAccount);
        return ResponseEntity.created(new URI("/api/money-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /money-accounts : Updates an existing moneyAccount.
     *
     * @param moneyAccount the moneyAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moneyAccount,
     * or with status 400 (Bad Request) if the moneyAccount is not valid,
     * or with status 500 (Internal Server Error) if the moneyAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/money-accounts")
    @Timed
    public ResponseEntity<MoneyAccount> updateMoneyAccount(@RequestBody MoneyAccount moneyAccount) throws URISyntaxException {
        log.debug("REST request to update MoneyAccount : {}", moneyAccount);
        if (moneyAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MoneyAccount result = moneyAccountRepository.save(moneyAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moneyAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /money-accounts : get all the moneyAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of moneyAccounts in body
     */
    @GetMapping("/money-accounts")
    @Timed
    public List<MoneyAccount> getAllMoneyAccounts() {
        log.debug("REST request to get all MoneyAccounts");
        return moneyAccountRepository.findByUserIsCurrentUser();
    }

    /**
     * GET  /money-accounts/:id : get the "id" moneyAccount.
     *
     * @param id the id of the moneyAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moneyAccount, or with status 404 (Not Found)
     */
    @GetMapping("/money-accounts/{id}")
    @Timed
    public ResponseEntity<MoneyAccount> getMoneyAccount(@PathVariable Long id) {
        log.debug("REST request to get MoneyAccount : {}", id);
        Optional<MoneyAccount> moneyAccount = moneyAccountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(moneyAccount);
    }

    /**
     * DELETE  /money-accounts/:id : delete the "id" moneyAccount.
     *
     * @param id the id of the moneyAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/money-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMoneyAccount(@PathVariable Long id) {
        log.debug("REST request to delete MoneyAccount : {}", id);

        moneyAccountRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
