package io.zcw.zipmint.service;

import io.zcw.zipmint.domain.Transaction;
import io.zcw.zipmint.domain.enumeration.Category;
import io.zcw.zipmint.domain.enumeration.TransactionType;
import io.zcw.zipmint.repository.TransactionRepository;
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
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @InjectMocks
    private TransactionService transactionServiceMock;

    private Transaction transaction = new Transaction();
    private Transaction transaction2 = new Transaction();
    private Transaction transaction3 = new Transaction();
    private List<Transaction> transactionList = new ArrayList<>();

    @Before
    public void setUp() {
        transaction.setAmount(-80.75);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setDateTime(LocalDate.of(2018, 12, 20));
        transaction.setDescription("Walmart");
        transaction.setMemo("Gifts");
        transaction.setCategory(Category.SHOPPING);
        transactionList.add(transaction);

        transaction2.setAmount(100.00);
        transaction2.setTransactionType(TransactionType.CREDIT);
        transaction2.setDateTime(LocalDate.of(2018, 12, 24));
        transaction2.setDescription("Paycheck");
        transaction2.setMemo("Get money, get paid");
        transaction2.setCategory(Category.INCOME);
        transactionList.add(transaction2);

        transaction3.setAmount(-12.75);
        transaction3.setTransactionType(TransactionType.DEBIT);
        transaction3.setDateTime(LocalDate.of(2018, 12, 21));
        transaction3.setDescription("El Diablo Burritos");
        transaction3.setMemo("Lunch");
        transaction3.setCategory(Category.FOOD);
        transactionList.add(transaction3);

        Mockito.when(transactionRepositoryMock.findAll()).thenReturn(transactionList);
    }

    @Test
    public void getDebitTest() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction); expected.add(transaction3);

        Assert.assertEquals(expected, transactionServiceMock.getDebitTransactions());
    }

    @Test
    public void getDebitTest_False(){
        Assert.assertNotEquals(transactionList, transactionServiceMock.getDebitTransactions());
    }

    @Test
    public void getCreditTest() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction2);

        Assert.assertEquals(expected, transactionServiceMock.getCreditTransactions());
    }

    @Test
    public void getCreditTest_False(){
        Assert.assertNotEquals(transactionList, transactionServiceMock.getDebitTransactions());
    }

    @Test
    public void getByDateTest(){
        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction); expected.add(transaction3); expected.add(transaction2);

        Assert.assertEquals(expected, transactionServiceMock.getSortedByDate());
    }

    @Test
    public void getByDateTest_False(){
        List<Transaction> unsorted = new ArrayList<>();
        unsorted.add(transaction); unsorted.add(transaction2); unsorted.add(transaction3);

        Assert.assertNotEquals(unsorted, transactionServiceMock.getSortedByDate());
    }

    @Test
    public void getByDescriptionTest(){
        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction3); expected.add(transaction2); expected.add(transaction);

        Assert.assertEquals(expected, transactionServiceMock.getSortedByDescription());
    }

    @Test
    public void getByDescriptionTest_False(){
        List<Transaction> unsorted = new ArrayList<>();
        unsorted.add(transaction); unsorted.add(transaction2); unsorted.add(transaction3);

        Assert.assertNotEquals(unsorted, transactionServiceMock.getSortedByDescription());
    }

    @Test
    public void getByCategoryTest(){
        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction3); expected.add(transaction2); expected.add(transaction);

        Assert.assertEquals(expected, transactionServiceMock.getSortedByCategory());
    }

    @Test
    public void getByCategoryTest_False(){
        List<Transaction> unsorted = new ArrayList<>();
        unsorted.add(transaction); unsorted.add(transaction2); unsorted.add(transaction3);

        Assert.assertNotEquals(unsorted, transactionServiceMock.getSortedByCategory());
    }

    @Test
    public void getByAmountTest(){
        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction); expected.add(transaction3); expected.add(transaction2);

        Assert.assertEquals(expected, transactionServiceMock.getSortedByAmount());
    }

    @Test
    public void getByAmountTest_False(){
        List<Transaction> unsorted = new ArrayList<>();
        unsorted.add(transaction); unsorted.add(transaction2); unsorted.add(transaction3);

        Assert.assertNotEquals(unsorted, transactionServiceMock.getSortedByAmount());
    }

    @Test
    public void searchTest(){
        List<Transaction> expected = new ArrayList<>();

        Assert.assertEquals(expected, transactionServiceMock.searchTransaction("RENT"));
    }

    @Test
    public void searchTest_1(){
        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction);

        Assert.assertEquals(expected, transactionServiceMock.searchTransaction("walmart"));
    }

    @Test
    public void searchTest_2(){
        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction2);

        Assert.assertEquals(expected, transactionServiceMock.searchTransaction("100"));
    }

    @Test
    public void searchTest_3(){
        Assert.assertEquals(transactionList, transactionServiceMock.searchTransaction("12"));
    }

    @Test
    public void searchTest_False(){
        Assert.assertNotEquals(transactionList, transactionServiceMock.searchTransaction("lunch"));
    }

}
