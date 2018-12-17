package io.zcw.zipmint.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.zcw.zipmint.domain.Bills;
import io.zcw.zipmint.repository.BillsRepository;
import io.zcw.zipmint.web.rest.errors.BadRequestAlertException;
import io.zcw.zipmint.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Bills.
 */
@RestController
@RequestMapping("/api")
public class BillsResource {

    private final Logger log = LoggerFactory.getLogger(BillsResource.class);

    private static final String ENTITY_NAME = "bills";

    private final BillsRepository billsRepository;

    public BillsResource(BillsRepository billsRepository) {
        this.billsRepository = billsRepository;
    }

    /**
     * POST  /bills : Create a new bills.
     *
     * @param bills the bills to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bills, or with status 400 (Bad Request) if the bills has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bills")
    @Timed
    public ResponseEntity<Bills> createBills(@RequestBody Bills bills) throws URISyntaxException {
        log.debug("REST request to save Bills : {}", bills);
        if (bills.getId() != null) {
            throw new BadRequestAlertException("A new bills cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bills result = billsRepository.save(bills);
        return ResponseEntity.created(new URI("/api/bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bills : Updates an existing bills.
     *
     * @param bills the bills to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bills,
     * or with status 400 (Bad Request) if the bills is not valid,
     * or with status 500 (Internal Server Error) if the bills couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bills")
    @Timed
    public ResponseEntity<Bills> updateBills(@RequestBody Bills bills) throws URISyntaxException {
        log.debug("REST request to update Bills : {}", bills);
        if (bills.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bills result = billsRepository.save(bills);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bills.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bills : get all the bills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bills in body
     */
    @GetMapping("/bills")
    @Timed
    public List<Bills> getAllBills() {
        log.debug("REST request to get all Bills");
        return billsRepository.findAll();
    }

    /**
     * GET  /bills/:id : get the "id" bills.
     *
     * @param id the id of the bills to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bills, or with status 404 (Not Found)
     */
    @GetMapping("/bills/{id}")
    @Timed
    public ResponseEntity<Bills> getBills(@PathVariable Long id) {
        log.debug("REST request to get Bills : {}", id);
        Optional<Bills> bills = billsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bills);
    }

    /**
     * DELETE  /bills/:id : delete the "id" bills.
     *
     * @param id the id of the bills to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bills/{id}")
    @Timed
    public ResponseEntity<Void> deleteBills(@PathVariable Long id) {
        log.debug("REST request to delete Bills : {}", id);

        billsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
