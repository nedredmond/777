package io.zcw.zipmint.repository;

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

    List<Transaction> findByDateTimeGreaterThanAndDateTimeLessThan(LocalDate start, LocalDate end);

}
