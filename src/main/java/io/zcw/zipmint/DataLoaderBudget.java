package io.zcw.zipmint;

import io.zcw.zipmint.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoaderBudget {

    private BudgetRepository budgetRepository;
    private DataLoaderBudgetItem dataLoaderBudgetItem;


}
