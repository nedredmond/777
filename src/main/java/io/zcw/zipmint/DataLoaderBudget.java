package io.zcw.zipmint;

import io.zcw.zipmint.domain.Budget;
import io.zcw.zipmint.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class DataLoaderBudget {

    private BudgetRepository budgetRepository;
    private DataLoaderBudgetItem dataLoaderBudgetItem;

    @Autowired
    public DataLoaderBudget(BudgetRepository budgetRepository, DataLoaderBudgetItem dataLoaderBudgetItem) {
        this.budgetRepository = budgetRepository;
        this.dataLoaderBudgetItem = dataLoaderBudgetItem;
    }

    @PostConstruct
    public void loadBudget(){
        Budget budget = new Budget();
        budget.setBudgetItems(dataLoaderBudgetItem.getBudgetItemSet());
        budget.setExpectedTotal(1500.0);
        budget.setActualTotal(1225.0);
        budget.setStartDate(LocalDate.of(2018,12,01));
        budget.setEndDate(LocalDate.of(2018,12,31));
        budgetRepository.save(budget);
    }

}
