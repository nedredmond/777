package io.zcw.zipmint.service;

import io.zcw.zipmint.repository.TransactionRepository;
import org.junit.Test;

public class TransactionServiceTest {

    private TransactionService transactionService;

    @Test
    public void getDebitTest(){
        transactionService.getDebitTransactions();
    }

}
