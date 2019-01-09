package io.zcw.zipmint.repository;

import io.zcw.zipmint.domain.MoneyAccount;
import io.zcw.zipmint.domain.Transaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select transaction from Transaction transaction join MoneyAccount money_account on money_account.id = transaction.moneyAccount.id where money_account.user.login = ?#{principal.username}")
    List<Transaction> findByUserIsCurrentUser();

    List<Transaction> findByDateTimeGreaterThanAndDateTimeLessThan(LocalDate start, LocalDate end);

}
