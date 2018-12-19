package io.zcw.zipmint;

import io.zcw.zipmint.domain.BillItem;
import io.zcw.zipmint.repository.BillItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoaderBillItem {

    private BillItemRepository billItemRepository;
    // private LocalDate localDate

    @Autowired
    public DataLoaderBillItem(BillItemRepository billItemRepository){
        this.billItemRepository = billItemRepository;
        LoadBills();
    }

    private void LoadBills(){
        BillItem billItem = new BillItem();
        billItem.setCompanyName("Utility Company");
        billItem.setDueDate(LocalDate.of(2019, 1, 30));
        billItem.setPaymentAmount(100L);
        billItem.setAutoPay(Boolean.TRUE);
        billItemRepository.save(billItem);

        BillItem billItem2 = new BillItem();
        billItem2.setCompanyName("Water Company");
        billItem2.setDueDate(LocalDate.of(2019, 1, 15));
        billItem2.setPaymentAmount(50L);
        billItem2.setAutoPay(Boolean.FALSE);
        billItemRepository.save(billItem2);
    }
}
