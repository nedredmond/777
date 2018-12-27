package io.zcw.zipmint;

import io.zcw.zipmint.domain.BillItem;
import io.zcw.zipmint.repository.BillItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.LinkedHashSet;

@Component
public class DataLoaderBillItem {

    private BillItemRepository billItemRepository;
    private LinkedHashSet<BillItem> billItemSet = new LinkedHashSet<>();

    @Autowired
    public DataLoaderBillItem(BillItemRepository billItemRepository){
        this.billItemRepository = billItemRepository;
    }

    @PostConstruct
    private void LoadBillItems(){
        BillItem billItem = new BillItem();
        billItem.setCompanyName("Utility Company Nov");
        billItem.setDueDate(LocalDate.of(2018, 11, 15));
        billItem.setPaymentAmount(100L);
        billItem.setAutoPay(Boolean.TRUE);
        billItemRepository.save(billItem);
        billItemSet.add(billItem);

        BillItem billItem2 = new BillItem();
        billItem2.setCompanyName("Utility Company Dec");
        billItem2.setDueDate(LocalDate.of(2018, 12, 15));
        billItem2.setPaymentAmount(50L);
        billItem2.setAutoPay(Boolean.FALSE);
        billItemRepository.save(billItem2);
        billItemSet.add(billItem2);
    }

    public LinkedHashSet<BillItem> getBillItemSet() {
        return billItemSet;
    }
}
