package io.zcw.zipmint.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.zcw.zipmint.domain.UserDetails;
import io.zcw.zipmint.repository.UserDetailsRepository;
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
 * REST controller for managing UserDetails.
 */
@RestController
@RequestMapping("/api")
public class UserDetailsResource {

    private final Logger log = LoggerFactory.getLogger(UserDetailsResource.class);

    private static final String ENTITY_NAME = "userDetails";

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsResource(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    /**
     * POST  /user-details : Create a new userDetails.
     *
     * @param userDetails the userDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDetails, or with status 400 (Bad Request) if the userDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-details")
    @Timed
    public ResponseEntity<UserDetails> createUserDetails(@RequestBody UserDetails userDetails) throws URISyntaxException {
        log.debug("REST request to save UserDetails : {}", userDetails);
        if (userDetails.getId() != null) {
            throw new BadRequestAlertException("A new userDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDetails result = userDetailsRepository.save(userDetails);
        return ResponseEntity.created(new URI("/api/user-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-details : Updates an existing userDetails.
     *
     * @param userDetails the userDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userDetails,
     * or with status 400 (Bad Request) if the userDetails is not valid,
     * or with status 500 (Internal Server Error) if the userDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-details")
    @Timed
    public ResponseEntity<UserDetails> updateUserDetails(@RequestBody UserDetails userDetails) throws URISyntaxException {
        log.debug("REST request to update UserDetails : {}", userDetails);
        if (userDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDetails result = userDetailsRepository.save(userDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-details : get all the userDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userDetails in body
     */
    @GetMapping("/user-details")
    @Timed
    public List<UserDetails> getAllUserDetails() {
        log.debug("REST request to get all UserDetails");
        return userDetailsRepository.findAll();
    }

    /**
     * GET  /user-details/:id : get the "id" userDetails.
     *
     * @param id the id of the userDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDetails, or with status 404 (Not Found)
     */
    @GetMapping("/user-details/{id}")
    @Timed
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable Long id) {
        log.debug("REST request to get UserDetails : {}", id);
        Optional<UserDetails> userDetails = userDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userDetails);
    }

    /**
     * DELETE  /user-details/:id : delete the "id" userDetails.
     *
     * @param id the id of the userDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserDetails(@PathVariable Long id) {
        log.debug("REST request to delete UserDetails : {}", id);

        userDetailsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
