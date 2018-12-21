package io.zcw.zipmint;

import io.zcw.zipmint.domain.BillItem;
import io.zcw.zipmint.repository.BillItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.LinkedHashSet;

@Component
public class DataLoaderBillItem {

    private BillItemRepository billItemRepository;
    private LinkedHashSet<BillItem> billItemSet = new LinkedHashSet<>();

    @Autowired
    public DataLoaderBillItem(BillItemRepository billItemRepository){
        this.billItemRepository = billItemRepository;
        LoadBillItems();
    }

    private void LoadBillItems(){
        BillItem billItem = new BillItem();
        billItem.setCompanyName("Utility Company");
        billItem.setDueDate(LocalDate.of(2019, 1, 30));
        billItem.setPaymentAmount(100L);
        billItem.setAutoPay(Boolean.TRUE);
        billItemRepository.save(billItem);
        billItemSet.add(billItem);

        BillItem billItem2 = new BillItem();
        billItem2.setCompanyName("Water Company");
        billItem2.setDueDate(LocalDate.of(2019, 1, 15));
        billItem2.setPaymentAmount(50L);
        billItem2.setAutoPay(Boolean.FALSE);
        billItemRepository.save(billItem2);
        billItemSet.add(billItem2);
    }

    public LinkedHashSet<BillItem> getBillItemSet() {
        return billItemSet;
    }
}
