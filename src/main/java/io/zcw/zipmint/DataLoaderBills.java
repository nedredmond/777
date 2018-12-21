package io.zcw.zipmint;

import io.zcw.zipmint.domain.BillItem;
import io.zcw.zipmint.domain.Bills;
import io.zcw.zipmint.repository.BillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

@Component
public class DataLoaderBills {

    private BillsRepository billsRepository;
    private DataLoaderBillItem dataLoaderBillItem;

    @Autowired
    public DataLoaderBills(BillsRepository billsRepository, DataLoaderBillItem dataLoaderBillItem){
        this.billsRepository = billsRepository;
        this.dataLoaderBillItem = dataLoaderBillItem;
        LoadBills();
    }

    private void LoadBills(){
        Bills bills = new Bills();
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
