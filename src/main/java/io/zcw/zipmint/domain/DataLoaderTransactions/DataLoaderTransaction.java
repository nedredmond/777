package io.zcw.zipmint.domain.DataLoaderTransactions;

import io.zcw.zipmint.domain.Transaction;
import io.zcw.zipmint.domain.enumeration.Category;
import io.zcw.zipmint.domain.enumeration.TransactionType;
import io.zcw.zipmint.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.LinkedHashSet;

@Component
public class DataLoaderTransaction {

    private TransactionRepository transactionRepository;
    private LinkedHashSet<Transaction> transactionSet = new LinkedHashSet<>();

    @Autowired
    public DataLoaderTransaction(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @PostConstruct
    private void LoadBillItems() {
        Transaction transaction = new Transaction();
        transaction.setAmount(-10.75);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setDateTime(LocalDate.of(2018, 12, 20));
        transaction.setDescription("ACME");
        transaction.setMemo("Groceries");
        transaction.setCategory(Category.FOOD);
        // transaction.setMoneyAccount(new MoneyAccount());
        transactionRepository.save(transaction);
        transactionSet.add(transaction);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(-100.00);
        transaction2.setTransactionType(TransactionType.DEBIT);
        transaction2.setDateTime(LocalDate.of(2018, 12, 24));
        transaction2.setDescription("Walmart");
        transaction2.setMemo("Gifts");
        transaction2.setCategory(Category.SHOPPING);
        transactionRepository.save(transaction2);
        transactionSet.add(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setAmount(-12.75);
        transaction3.setTransactionType(TransactionType.DEBIT);
        transaction3.setDateTime(LocalDate.of(2018, 12, 21));
        transaction3.setDescription("El Diablo Burritos");
        transaction3.setMemo("Lunch");
        transaction3.setCategory(Category.FOOD);
        transactionRepository.save(transaction3);
        transactionSet.add(transaction);

        Transaction transaction4 = new Transaction();
        transaction4.setAmount(-4.50);
        transaction4.setTransactionType(TransactionType.DEBIT);
        transaction4.setDateTime(LocalDate.now());
        transaction4.setDescription("TBaar");
        transaction4.setMemo("Bubble tea");
        transaction4.setCategory(Category.FOOD);
        transactionRepository.save(transaction4);
        transactionSet.add(transaction);

        Transaction transaction5 = new Transaction();
        transaction5.setAmount(10.00);
        transaction5.setTransactionType(TransactionType.CREDIT);
        transaction5.setDateTime(LocalDate.of(2018, 12, 22));
        transaction5.setDescription("Urban Outfitters");
        transaction5.setMemo("Refund");
        transaction5.setCategory(Category.SHOPPING);
        transactionRepository.save(transaction5);
        transactionSet.add(transaction);
    }

    public LinkedHashSet<Transaction> getTransactionSet() {
        return transactionSet;
    }
}
