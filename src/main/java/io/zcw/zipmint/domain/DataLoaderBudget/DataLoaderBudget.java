package io.zcw.zipmint.domain.DataLoaderBudget;

import io.zcw.zipmint.domain.Budget;
import io.zcw.zipmint.domain.BudgetItem;
import io.zcw.zipmint.repository.BudgetRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.LinkedHashSet;

@Component
public class DataLoaderBudget {

    private BudgetRepository budgetRepository;
    private LinkedHashSet<Budget> budgetSet = new LinkedHashSet<>();

    public DataLoaderBudget(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @PostConstruct
    private void loadBudget(){
        Budget monthBudget = new Budget();
        monthBudget.setStartDate(LocalDate.of(2018,01,01));
        monthBudget.setEndDate(LocalDate.of(2018,01,31));
        monthBudget.setExpectedTotal(1500.0);
        budgetRepository.save(monthBudget);
        budgetSet.add(monthBudget);

    }

    public LinkedHashSet<Budget> getBudget(){
        return budgetSet;
    }
}
