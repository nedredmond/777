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
        transaction2.setAmount(-108.82);
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
        transaction5.setAmount(19.95);
        transaction5.setTransactionType(TransactionType.CREDIT);
        transaction5.setDateTime(LocalDate.of(2018, 12, 22));
        transaction5.setDescription("Banana Republic");
        transaction5.setMemo("Refund");
        transaction5.setCategory(Category.SHOPPING);
        transactionRepository.save(transaction5);
        transactionSet.add(transaction);

        Transaction transaction6 = new Transaction();
        transaction6.setAmount(-35.50);
        transaction6.setTransactionType(TransactionType.DEBIT);
        transaction6.setDateTime(LocalDate.of(2019, 1, 3));
        transaction6.setDescription("Honda");
        transaction6.setMemo("Oil change");
        transaction6.setCategory(Category.AUTOMOTIVE);
        transactionRepository.save(transaction6);
        transactionSet.add(transaction);

        Transaction transaction7 = new Transaction();
        transaction7.setAmount(-1.00);
        transaction7.setTransactionType(TransactionType.DEBIT);
        transaction7.setDateTime(LocalDate.of(2019, 1, 7));
        transaction7.setDescription("ZipMynt");
        transaction7.setMemo("Peace offering");
        transaction7.setCategory(Category.TRANSFER);
        transactionRepository.save(transaction7);
        transactionSet.add(transaction);

        Transaction transaction8 = new Transaction();
        transaction8.setAmount(-205.77);
        transaction8.setTransactionType(TransactionType.DEBIT);
        transaction8.setDateTime(LocalDate.of(2019, 1, 1));
        transaction8.setDescription("Delmarva Power");
        transaction8.setMemo("Heating");
        transaction8.setCategory(Category.UTILITIES);
        transactionRepository.save(transaction8);
        transactionSet.add(transaction);

        Transaction transaction9 = new Transaction();
        transaction9.setAmount(25.00);
        transaction9.setTransactionType(TransactionType.CREDIT);
        transaction9.setDateTime(LocalDate.of(2018, 12, 22));
        transaction9.setDescription("Anonymous");
        transaction9.setMemo("Commission");
        transaction9.setCategory(Category.INCOME);
        transactionRepository.save(transaction9);
        transactionSet.add(transaction);

        Transaction transaction10 = new Transaction();
        transaction10.setAmount(-6.75);
        transaction10.setTransactionType(TransactionType.DEBIT);
        transaction10.setDateTime(LocalDate.of(2018, 12, 22));
        transaction10.setDescription("Mimi Teriyaki");
        transaction10.setMemo("Yummy yummy");
        transaction10.setCategory(Category.FOOD);
        transactionRepository.save(transaction10);
        transactionSet.add(transaction);
    }

    public LinkedHashSet<Transaction> getTransactionSet() {
        return transactionSet;
    }
}
