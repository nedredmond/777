package io.zcw.zipmint.repository;

import io.zcw.zipmint.domain.MoneyAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MoneyAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoneyAccountRepository extends JpaRepository<MoneyAccount, Long> {

}
