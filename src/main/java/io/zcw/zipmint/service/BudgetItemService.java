package io.zcw.zipmint.service;

import io.github.jhipster.web.util.ResponseUtil;
import io.zcw.zipmint.domain.BudgetItem;
import io.zcw.zipmint.domain.Transaction;
import io.zcw.zipmint.domain.enumeration.Category;
import io.zcw.zipmint.repository.BudgetItemRepository;
import io.zcw.zipmint.repository.TransactionRepository;
import io.zcw.zipmint.web.rest.errors.BadRequestAlertException;
import io.zcw.zipmint.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BudgetItemService {

    private static final String ENTITY_NAME = "budgetItem";

    private final BudgetItemRepository budgetItemRepository;
    private final TransactionRepository transactionRepository;

    public BudgetItemService(BudgetItemRepository budgetItemRepository, TransactionRepository transactionRepository) {
        this.budgetItemRepository = budgetItemRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<BudgetItem> createBudgetItem(@RequestBody BudgetItem budgetItem) throws URISyntaxException {
        if (budgetItem.getId() != null) {
            throw new BadRequestAlertException("A new budgetItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BudgetItem result = budgetItemRepository.save(budgetItem);
        return ResponseEntity.created(new URI("/api/budget-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    public ResponseEntity<BudgetItem> updateBudgetItem(@RequestBody BudgetItem budgetItem) throws URISyntaxException {
        if (budgetItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BudgetItem result = budgetItemRepository.save(budgetItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, budgetItem.getId().toString()))
            .body(result);
    }

    public ResponseEntity<BudgetItem> getBudgetItem(@PathVariable Long id) {
        Optional<BudgetItem> budgetItem = budgetItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(budgetItem);
    }

    public ResponseEntity<Void> deleteBudgetItem(@PathVariable Long id) {
        budgetItemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    public List<Transaction> filterByMonth(int month) {
        LocalDate date = LocalDate.now();
        LocalDate start = date.withDayOfMonth(1);
        LocalDate end = date.withDayOfMonth(date.lengthOfMonth());

        return transactionRepository.findByDateTimeGreaterThanAndDateTimeLessThan(start, end);

//        ArrayList<Transaction> transactionsList = new ArrayList<>();
//        for(Transaction t: transactionRepository.findAll()){
//            if(t.getDateTime().getMonthValue()==month){
//                transactionsList.add(t);
//            }
//        }
//        return transactionsList;
    }

    public List<BudgetItem> getBudgetItemForCurrentMonth(){
        int currentMonth = LocalDate.now().getMonthValue();

        //get budget items
        List<BudgetItem> budgetItems = budgetItemRepository.findAll();

        //get transactions
        List<Transaction> transactions = filterByMonth(currentMonth);

        //update budget item with current transaction total
//        Function<? super Transaction, ?> func = (x) -> x.getAmount();
        Map<Category, Double> result =
            transactions.stream().collect(Collectors.toMap(Transaction::getCategory, Transaction::getAmount));

        for(BudgetItem budgetItem : budgetItems) {
            budgetItem.setActualSpending(result.get(budgetItem.getCategory()));
        }

        return budgetItems;
    }

    public List<BudgetItem> sortByAmountSpent(List<BudgetItem> budgetItemList){
        budgetItemList.sort(Comparator.comparing(o -> o.getActualSpending()));
        return budgetItemList;
    }

    public Iterable<BudgetItem> getSortedByAmountSpent(){
        return sortByAmountSpent(budgetItemRepository.findAll());
    }

    public List<BudgetItem> sortByBudgetAmount(List<BudgetItem> budgetItemList){
        budgetItemList.sort(Comparator.comparing(o -> o.getExpectedSpending()));
        return budgetItemList;
    }

    public Iterable<BudgetItem> getSortedByBudgetAmount(){
        return sortByBudgetAmount(budgetItemRepository.findAll());
    }

    public List<BudgetItem> sortByCategory(List<BudgetItem> budgetItemList){
        budgetItemList.sort(Comparator.comparing(o -> o.getCategory()));
        return budgetItemList;
    }

    public Iterable<BudgetItem> getSortedByCategory(){
        return sortByCategory(budgetItemRepository.findAll());
    }

    public List<Transaction> filterByCategory(Category category, int month){
        ArrayList<Transaction> filteredList = new ArrayList<>();
        for(Transaction t: filterByMonth(month)){
            if(t.getCategory().equals(category)){
                filteredList.add(t);
            }
        }
        return filteredList;
    }
}
