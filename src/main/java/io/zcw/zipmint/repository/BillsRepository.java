package io.zcw.zipmint.repository;

import io.zcw.zipmint.domain.Bills;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bills entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillsRepository extends JpaRepository<Bills, Long> {

}
