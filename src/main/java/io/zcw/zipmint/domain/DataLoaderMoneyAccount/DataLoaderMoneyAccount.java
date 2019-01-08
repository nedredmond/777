package io.zcw.zipmint.domain.DataLoaderMoneyAccount;

import io.zcw.zipmint.domain.BillItem;
import io.zcw.zipmint.domain.MoneyAccount;

import io.zcw.zipmint.domain.UserDetails;
import io.zcw.zipmint.domain.enumeration.AccountType;
import io.zcw.zipmint.repository.MoneyAccountRepository;
import io.zcw.zipmint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashSet;

@Component
public class DataLoaderMoneyAccount {

    private MoneyAccountRepository moneyAccountRepository;
    private LinkedHashSet<MoneyAccount> moneyAccountSet = new LinkedHashSet<>();

    @Autowired
    public DataLoaderMoneyAccount(MoneyAccountRepository moneyAccountRepository) {
        this.moneyAccountRepository = moneyAccountRepository;
    }

    @PostConstruct
    private void loadMoneyAccount(){
        MoneyAccount moneyAccount= new MoneyAccount();
        moneyAccount.setId(1L);
        moneyAccount.setType(AccountType.CREDIT);
        moneyAccount.setAccountTotal(1000L);
        moneyAccount.setSignIn("Jack");
        moneyAccount.setPw("123");
        moneyAccountRepository.save(moneyAccount);
        moneyAccountSet.add(moneyAccount);


        MoneyAccount moneyAccount2= new MoneyAccount();
        moneyAccount2.setId(2L);
        moneyAccount2.setType(AccountType.CHECKING);
        moneyAccount2.setAccountTotal(500L);
        moneyAccount2.setSignIn("Jack");
        moneyAccount2.setPw("456");
        moneyAccountRepository.save(moneyAccount2);
        moneyAccountSet.add(moneyAccount2);



        MoneyAccount moneyAccount3= new MoneyAccount();
        moneyAccount3.setId(3L);
        moneyAccount3.setType(AccountType.SAVINGS);
        moneyAccount3.setAccountTotal(1500L);
        moneyAccount3.setSignIn("Jack");
        moneyAccount3.setPw("789");
        moneyAccountRepository.save(moneyAccount3);
        moneyAccountSet.add(moneyAccount3);



        MoneyAccount moneyAccount4= new MoneyAccount();
        moneyAccount4.setId(4L);
        moneyAccount4.setType(AccountType.LOAN);
        moneyAccount4.setAccountTotal(600L);
        moneyAccount4.setSignIn("Jack");
        moneyAccount4.setPw("789");
        moneyAccountRepository.save(moneyAccount4);
        moneyAccountSet.add(moneyAccount4);
    }

    public LinkedHashSet<MoneyAccount> getMoneyAccountSet() {
        return moneyAccountSet;
    }

}
