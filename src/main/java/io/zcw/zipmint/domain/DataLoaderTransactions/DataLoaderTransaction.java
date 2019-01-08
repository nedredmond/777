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
        transactionRepository.save(transaction);
        transactionSet.add(transaction);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(-100.00);
        transaction2.setTransactionType(TransactionType.DEBIT);
        transaction2.setDateTime(LocalDate.of(2018, 12, 24));
        transaction2.setDescription("Target");
        transaction2.setMemo("Gifts");
        transaction2.setCategory(Category.MISCELLANEOUS);
        transactionRepository.save(transaction2);
        transactionSet.add(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setAmount(-475.75);
        transaction3.setTransactionType(TransactionType.DEBIT);
        transaction3.setDateTime(LocalDate.of(2018, 12, 21));
        transaction3.setDescription("The Riverfront");
        transaction3.setMemo("Rent");
        transaction3.setCategory(Category.RENT);
        transactionRepository.save(transaction3);
        transactionSet.add(transaction);

        Transaction transaction4 = new Transaction();
        transaction4.setAmount(500.00);
        transaction4.setTransactionType(TransactionType.CREDIT);
        transaction4.setDateTime(LocalDate.now());
        transaction4.setDescription("Zipcode Inc.");
        transaction4.setMemo("Guest lecture compensation");
        transaction4.setCategory(Category.INCOME);
        transactionRepository.save(transaction4);
        transactionSet.add(transaction);

        Transaction transaction5 = new Transaction();
        transaction5.setAmount(10.00);
        transaction5.setTransactionType(TransactionType.CREDIT);
        transaction5.setDateTime(LocalDate.of(2018, 12, 22));
        transaction5.setDescription("Banana Republic");
        transaction5.setMemo("Refund");
        transaction5.setCategory(Category.SHOPPING);
        transactionRepository.save(transaction5);
        transactionSet.add(transaction);
    }

    public LinkedHashSet<Transaction> getTransactionSet() {
        return transactionSet;
    }
}
