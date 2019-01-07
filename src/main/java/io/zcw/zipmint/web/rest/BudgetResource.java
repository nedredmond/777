package io.zcw.zipmint.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.zcw.zipmint.domain.Budget;
import io.zcw.zipmint.domain.Transaction;
import io.zcw.zipmint.repository.BudgetRepository;
import io.zcw.zipmint.repository.TransactionRepository;
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
 * REST controller for managing Budget.
 */
@RestController
@RequestMapping("/api")
public class BudgetResource {

    private final Logger log = LoggerFactory.getLogger(BudgetResource.class);

    private static final String ENTITY_NAME = "budget";

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;

    public BudgetResource(BudgetRepository budgetRepository, TransactionRepository transactionRepository) {
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * POST  /budgets : Create a new budget.
     *
     * @param budget the budget to create
     * @return the ResponseEntity with status 201 (Created) and with body the new budget, or with status 400 (Bad Request) if the budget has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/budgets")
    @Timed
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) throws URISyntaxException {
        log.debug("REST request to save Budget : {}", budget);
        if (budget.getId() != null) {
            throw new BadRequestAlertException("A new budget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Budget result = budgetRepository.save(budget);
        return ResponseEntity.created(new URI("/api/budgets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /budgets : Updates an existing budget.
     *
     * @param budget the budget to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated budget,
     * or with status 400 (Bad Request) if the budget is not valid,
     * or with status 500 (Internal Server Error) if the budget couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/budgets")
    @Timed
    public ResponseEntity<Budget> updateBudget(@RequestBody Budget budget) throws URISyntaxException {
        log.debug("REST request to update Budget : {}", budget);
        if (budget.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Budget result = budgetRepository.save(budget);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, budget.getId().toString()))
            .body(result);
    }

    /**
     * GET  /budgets : get all the budgets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of budgets in body
     */
    @GetMapping("/budgets")
    @Timed
    public List<Budget> getAllBudgets() {
        log.debug("REST request to get all Budgets");
        return budgetRepository.findAll();
    }

    /**
     * GET  /budgets/:id : get the "id" budget.
     *
     * @param id the id of the budget to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the budget, or with status 404 (Not Found)
     */
    @GetMapping("/budgets/{id}")
    @Timed
    public ResponseEntity<Budget> getBudget(@PathVariable Long id) {
        log.debug("REST request to get Budget : {}", id);
        Optional<Budget> budget = budgetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(budget);
    }

    /**
     * DELETE  /budgets/:id : delete the "id" budget.
     *
     * @param id the id of the budget to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/budgets/{id}")
    @Timed
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        log.debug("REST request to delete Budget : {}", id);

        budgetRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    public Budget calculateTotalSpent(){
        Budget budget = new Budget();
        Double total = 0.0;
        for(Transaction transaction: transactionRepository.findAll()){
            total+=transaction.getAmount();
        }
        budget.setActualTotal(total);
        return budget;
    }

    @GetMapping("/budgets/totalspent")
    @Timed
    public ResponseEntity<Budget> getTotalSpent(){
        log.debug("REST request to get total spent");
        return new ResponseEntity<>(calculateTotalSpent(),HttpStatus.OK);
    }

}
