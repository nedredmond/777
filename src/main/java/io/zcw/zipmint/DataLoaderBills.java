package io.zcw.zipmint;

import io.zcw.zipmint.domain.BillItem;
import io.zcw.zipmint.domain.Bills;
import io.zcw.zipmint.domain.MoneyAccount;
import io.zcw.zipmint.repository.BillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class DataLoaderBills {

    private BillsRepository billsRepository;
    private DataLoaderBillItem dataLoaderBillItem;
    MoneyAccount moneyAccount;

    @Autowired
    public DataLoaderBills(BillsRepository billsRepository, DataLoaderBillItem dataLoaderBillItem){
        this.billsRepository = billsRepository;
        this.dataLoaderBillItem = dataLoaderBillItem;
        moneyAccount = new MoneyAccount();
        moneyAccount.setAccountTotal(1000L);
        moneyAccount.setId(1234L);
    }

    @PostConstruct
    private void LoadBills(){
        Bills bills = new Bills();
        bills.setCompanyName("Utility Company");
        bills.setDueDate(LocalDate.of(2019, 1, 15));
        bills.setPaymentAmount(100.00);
        bills.setAutoPay(Boolean.TRUE);
        // bills.setMoneyAccount(moneyAccount);
        bills.setBillItems(dataLoaderBillItem.getBillItemSet());
        bills.setPaymentTotal(getBillItemSetTotal(dataLoaderBillItem));
        billsRepository.save(bills);
    }

    private Long getBillItemSetTotal(DataLoaderBillItem dataLoaderBillItem){
        Long total = 0L;
        for (BillItem b: dataLoaderBillItem.getBillItemSet() ) {
            total += b.getPaymentAmount();
        }
        return total;
    }
}

