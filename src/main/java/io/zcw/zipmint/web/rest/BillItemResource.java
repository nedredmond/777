package io.zcw.zipmint.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.zcw.zipmint.domain.BillItem;
import io.zcw.zipmint.repository.BillItemRepository;
import io.zcw.zipmint.web.rest.errors.BadRequestAlertException;
import io.zcw.zipmint.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BillItem.
 */
@RestController
@RequestMapping("/api")
public class BillItemResource {

    private final Logger log = LoggerFactory.getLogger(BillItemResource.class);

    private static final String ENTITY_NAME = "billItem";

    private final BillItemRepository billItemRepository;

    public BillItemResource(BillItemRepository billItemRepository) {
        this.billItemRepository = billItemRepository;
    }

    /**
     * POST  /bill-items : Create a new billItem.
     *
     * @param billItem the billItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billItem, or with status 400 (Bad Request) if the billItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bill-items")
    @Timed
    public ResponseEntity<BillItem> createBillItem(@RequestBody BillItem billItem) throws URISyntaxException {
        log.debug("REST request to save BillItem : {}", billItem);
        if (billItem.getId() != null) {
            throw new BadRequestAlertException("A new billItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillItem result = billItemRepository.save(billItem);
        return ResponseEntity.created(new URI("/api/bill-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bill-items : Updates an existing billItem.
     *
     * @param billItem the billItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billItem,
     * or with status 400 (Bad Request) if the billItem is not valid,
     * or with status 500 (Internal Server Error) if the billItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bill-items")
    @Timed
    public ResponseEntity<BillItem> updateBillItem(@RequestBody BillItem billItem) throws URISyntaxException {
        log.debug("REST request to update BillItem : {}", billItem);
        if (billItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillItem result = billItemRepository.save(billItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bill-items : get all the billItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of billItems in body
     */
    @GetMapping("/bill-items")
    @Timed
    public List<BillItem> getAllBillItems() {
        log.debug("REST request to get all BillItems");
        return billItemRepository.findAll();
    }

    /**
     * GET  /bill-items/:id : get the "id" billItem.
     *
     * @param id the id of the billItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billItem, or with status 404 (Not Found)
     */
    @GetMapping("/bill-items/{id}")
    @Timed
    public ResponseEntity<BillItem> getBillItem(@PathVariable Long id) {
        log.debug("REST request to get BillItem : {}", id);
        Optional<BillItem> billItem = billItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(billItem);
    }

    /**
     * DELETE  /bill-items/:id : delete the "id" billItem.
     *
     * @param id the id of the billItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bill-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteBillItem(@PathVariable Long id) {
        log.debug("REST request to delete BillItem : {}", id);

        billItemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    public List<BillItem> filterUnpaidBills(){
        List<BillItem> unpaidBills = new ArrayList<>();


        for(BillItem bill:billItemRepository.findAll()){
            if(bill.getDueDate().compareTo(LocalDate.now())>=0){
                if(bill.getPaymentAmount()>0){
                    unpaidBills.add(bill);
                }
            }
        }
        return unpaidBills;
    }

    @GetMapping(value = "/bill-items/unpaid")
    @Timed
    public ResponseEntity<Iterable<BillItem>> getUnpaidBills(){
        log.debug("REST request to get all unpaid bills");
        return new ResponseEntity<>(filterUnpaidBills(), HttpStatus.OK);
    }

    public List<BillItem> filterLateBills(){
        List<BillItem> lateBills = new ArrayList<>();

        for(BillItem bill: billItemRepository.findAll()){
            if(bill.getDueDate().compareTo(LocalDate.now())<0){
                if(bill.getPaymentAmount()>0){
                    lateBills.add(bill);
                }
            }
        }
        return lateBills;
    }

    @GetMapping(value = "/bill-items/late")
    @Timed
    public ResponseEntity<Iterable<BillItem>> getLateBills(){
        log.debug("REST request to get all late bills");
        return new ResponseEntity<>(filterLateBills(),HttpStatus.OK);
    }
}
