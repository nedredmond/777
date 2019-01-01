package io.zcw.zipmint.service;

import com.codahale.metrics.annotation.Timed;
import io.zcw.zipmint.domain.BudgetItem;
import io.zcw.zipmint.domain.Transaction;
import io.zcw.zipmint.domain.enumeration.Category;
import io.zcw.zipmint.repository.BudgetItemRepository;
import io.zcw.zipmint.repository.TransactionRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BudgetItemService {

    private final BudgetItemRepository budgetItemRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public BudgetItemService(BudgetItemRepository budgetItemRepository, TransactionRepository transactionRepository, TransactionService transactionService) {
        this.budgetItemRepository = budgetItemRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
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

    @GetMapping("/budget-items/current")
    @Timed
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
