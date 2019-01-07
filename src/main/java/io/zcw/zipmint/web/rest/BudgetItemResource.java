package io.zcw.zipmint.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.zcw.zipmint.domain.BudgetItem;
import io.zcw.zipmint.domain.Transaction;
import io.zcw.zipmint.domain.enumeration.Category;
import io.zcw.zipmint.repository.BudgetItemRepository;
import io.zcw.zipmint.repository.TransactionRepository;
import io.zcw.zipmint.service.BudgetItemService;
import io.zcw.zipmint.service.TransactionService;
import io.zcw.zipmint.web.rest.errors.BadRequestAlertException;
import io.zcw.zipmint.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * REST controller for managing BudgetItem.
 */
@RestController
@RequestMapping("/api")
public class BudgetItemResource {

    private final Logger log = LoggerFactory.getLogger(BudgetItemResource.class);

    private BudgetItemService budgetItemService;

    public BudgetItemResource(BudgetItemService budgetItemService) {
        this.budgetItemService = budgetItemService;
    }


    /**
     * POST  /budget-items : Create a new budgetItem.π
     *
     * @param budgetItem the budgetItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new budgetItem, or with status 400 (Bad Request) if the budgetItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/budget-items")
    @Timed
    public ResponseEntity<BudgetItem> createBudgetItem(@RequestBody BudgetItem budgetItem) throws URISyntaxException {
        log.debug("REST request to save BudgetItem : {}", budgetItem);
        return budgetItemService.createBudgetItem(budgetItem);
    }

    /**
     * PUT  /budget-items : Updates an existing budgetItem.
     *
     * @param budgetItem the budgetItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated budgetItem,
     * or with status 400 (Bad Request) if the budgetItem is not valid,
     * or with status 500 (Internal Server Error) if the budgetItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/budget-items")
    @Timed
    public ResponseEntity<BudgetItem> updateBudgetItem(@RequestBody BudgetItem budgetItem) throws URISyntaxException {
        log.debug("REST request to update BudgetItem : {}", budgetItem);
        return budgetItemService.updateBudgetItem(budgetItem);
    }

    /**
     * GET  /budget-items : get all the budgetItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of budgetItems in body
     */
    @GetMapping("/budget-items")
    @Timed
    public ResponseEntity<Iterable<BudgetItem>> getAllBudgetItems() {
        log.debug("REST request to get all BudgetItems");
        return new ResponseEntity<>(budgetItemService.getSortedByCategory(), HttpStatus.OK);
    }

    /**
     * GET  /budget-items/:id : get the "id" budgetItem.
     *
     * @param id the id of the budgetItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the budgetItem, or with status 404 (Not Found)
     */
    @GetMapping("/budget-items/{id}")
    @Timed
    public ResponseEntity<BudgetItem> getBudgetItem(@PathVariable Long id) {
        log.debug("REST request to get BudgetItem : {}", id);
        return budgetItemService.getBudgetItem(id);
    }

    /**
     * DELETE  /budget-items/:id : delete the "id" budgetItem.
     *
     * @param id the id of the budgetItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/budget-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteBudgetItem(@PathVariable Long id) {
        log.debug("REST request to delete BudgetItem : {}", id);
        return budgetItemService.deleteBudgetItem(id);
    }

    @GetMapping("/budget-items/{month}/{category}")
    @Timed
    public ResponseEntity<Iterable<Transaction>> budgetByCategory(@PathVariable int month, @PathVariable Category category){
        log.debug("REST request to filter BudgetItem category: {}", category);
        return new ResponseEntity<>(budgetItemService.filterByCategory(category, month), HttpStatus.OK);
    }


    @GetMapping("/budget-items/by_cat")
    @Timed
    public ResponseEntity<Iterable<BudgetItem>> getSortedByCategory(){
        log.debug("REST request to sort Budget Item by Category");
        return new ResponseEntity<>(budgetItemService.getSortedByCategory(), HttpStatus.OK);
    }


    @GetMapping("/budget-items/by_budget")
    @Timed
    public ResponseEntity<Iterable<BudgetItem>> getSortedByBudgetAmount(){
        log.debug("REST request to sort Budget Item by Budget Amount");
        return new ResponseEntity<>(budgetItemService.getSortedByBudgetAmount(), HttpStatus.OK);
    }

    @GetMapping("/budget-items/by_spent")
    @Timed
    public ResponseEntity<Iterable<BudgetItem>> getSortedByAmountSpent(){
        log.debug("REST request to sort Transactions by Amount Spent");
        return new ResponseEntity<>(budgetItemService.getSortedByAmountSpent(), HttpStatus.OK);
    }

    @GetMapping("/budget-items/current")
    @Timed
    public ResponseEntity<Iterable<BudgetItem>> getBudgetItemForCurrentMonth(){
        log.debug("REST request to get all budget item for current month");
        return new ResponseEntity<>(budgetItemService.getBudgetItemForCurrentMonth(), HttpStatus.OK);
    }
}
