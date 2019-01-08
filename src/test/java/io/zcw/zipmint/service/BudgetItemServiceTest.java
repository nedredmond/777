package io.zcw.zipmint.service;

import io.zcw.zipmint.domain.BudgetItem;
import io.zcw.zipmint.domain.Transaction;
import io.zcw.zipmint.domain.enumeration.Category;
import io.zcw.zipmint.domain.enumeration.TransactionType;
import io.zcw.zipmint.repository.BudgetItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BudgetItemServiceTest {

    private BudgetItemService budgetItemService;
    @Mock
    private BudgetItemRepository budgetRepositoryMock;

    @InjectMocks
    private BudgetItemService budgetServiceMock;

    private BudgetItem foodBudget = new BudgetItem();
    private BudgetItem shoppingBudget = new BudgetItem();
    private Transaction lunchTransaction = new Transaction();
    private Transaction shopTransaction = new Transaction();
    private List<BudgetItem> budgetItemList = new ArrayList<>();
    private List<Transaction> transactionList = new ArrayList<>();

    @Before
    public void setUp(){
        foodBudget.setCategory(Category.FOOD);
        foodBudget.setExpectedSpending(500.00);
        budgetItemList.add(foodBudget);

        shoppingBudget.setCategory(Category.SHOPPING);
        shoppingBudget.setExpectedSpending(250.00);

        lunchTransaction.setAmount(20.00);
        lunchTransaction.setDateTime(LocalDate.of(2018,12,05));
        lunchTransaction.setCategory(Category.FOOD);
        lunchTransaction.setTransactionType(TransactionType.CREDIT);
        transactionList.add(lunchTransaction);

        shopTransaction.setAmount(58.00);
        shopTransaction.setDateTime(LocalDate.of(2018,12,20));
        shopTransaction.setCategory(Category.SHOPPING);
        shopTransaction.setTransactionType(TransactionType.CREDIT);
        transactionList.add(shopTransaction);

        Mockito.when(budgetRepositoryMock.findAll()).thenReturn(budgetItemList);
    }

    @Test
    public void testSortedByCategory(){
        budgetItemList.add(shoppingBudget);

        budgetItemService.sortByCategory(budgetItemList);
        Assert.assertEquals(foodBudget,budgetItemList.get(0));
    }

    @Test
    public void testSortedByBudget(){
        budgetItemList.add(foodBudget);

        budgetItemService.sortByBudgetAmount(budgetItemList);
        Assert.assertEquals(foodBudget, budgetItemList.get(0));
        Assert.assertEquals(shoppingBudget,budgetItemList.get(1));
    }

    @Test
    public void testSortByAmountSpent(){
        budgetItemList.add(foodBudget);

        budgetItemService.sortByAmountSpent(budgetItemList);
        Assert.assertEquals(foodBudget,budgetItemList.get(0));
    }

    @Test
    public void testGetCurrentMonthBudget(){

    }

    @Test
    public void testFilterBudgetByCategory(){
        budgetItemService.getBudgetItemForCurrentMonth();
        Category category = Category.FOOD;

        List<Transaction> expected =  budgetItemService.filterByCategory(category,12);

        Assert.assertTrue(expected.contains(lunchTransaction));
    }



}
