package io.zcw.zipmint.repository;

import io.zcw.zipmint.domain.MoneyAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MoneyAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoneyAccountRepository extends JpaRepository<MoneyAccount, Long> {

    @Query("select money_account from MoneyAccount money_account where money_account.user.login = ?#{principal.username}")
    List<MoneyAccount> findByUserIsCurrentUser();

}
