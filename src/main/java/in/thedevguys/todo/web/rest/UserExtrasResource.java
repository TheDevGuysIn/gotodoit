package in.thedevguys.todo.web.rest;

import in.thedevguys.todo.domain.UserExtras;
import in.thedevguys.todo.repository.UserExtrasRepository;
import in.thedevguys.todo.service.UserExtrasQueryService;
import in.thedevguys.todo.service.UserExtrasService;
import in.thedevguys.todo.service.criteria.UserExtrasCriteria;
import in.thedevguys.todo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link in.thedevguys.todo.domain.UserExtras}.
 */
@RestController
@RequestMapping("/api")
public class UserExtrasResource {

    private final Logger log = LoggerFactory.getLogger(UserExtrasResource.class);

    private static final String ENTITY_NAME = "userExtras";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserExtrasService userExtrasService;

    private final UserExtrasRepository userExtrasRepository;

    private final UserExtrasQueryService userExtrasQueryService;

    public UserExtrasResource(
        UserExtrasService userExtrasService,
        UserExtrasRepository userExtrasRepository,
        UserExtrasQueryService userExtrasQueryService
    ) {
        this.userExtrasService = userExtrasService;
        this.userExtrasRepository = userExtrasRepository;
        this.userExtrasQueryService = userExtrasQueryService;
    }

    /**
     * {@code POST  /user-extras} : Create a new userExtras.
     *
     * @param userExtras the userExtras to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userExtras, or with status {@code 400 (Bad Request)} if the userExtras has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-extras")
    public ResponseEntity<UserExtras> createUserExtras(@RequestBody UserExtras userExtras) throws URISyntaxException {
        log.debug("REST request to save UserExtras : {}", userExtras);
        if (userExtras.getId() != null) {
            throw new BadRequestAlertException("A new userExtras cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserExtras result = userExtrasService.save(userExtras);
        return ResponseEntity
            .created(new URI("/api/user-extras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-extras/:id} : Updates an existing userExtras.
     *
     * @param id the id of the userExtras to save.
     * @param userExtras the userExtras to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExtras,
     * or with status {@code 400 (Bad Request)} if the userExtras is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userExtras couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-extras/{id}")
    public ResponseEntity<UserExtras> updateUserExtras(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserExtras userExtras
    ) throws URISyntaxException {
        log.debug("REST request to update UserExtras : {}, {}", id, userExtras);
        if (userExtras.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userExtras.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userExtrasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserExtras result = userExtrasService.update(userExtras);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userExtras.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-extras/:id} : Partial updates given fields of an existing userExtras, field will ignore if it is null
     *
     * @param id the id of the userExtras to save.
     * @param userExtras the userExtras to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExtras,
     * or with status {@code 400 (Bad Request)} if the userExtras is not valid,
     * or with status {@code 404 (Not Found)} if the userExtras is not found,
     * or with status {@code 500 (Internal Server Error)} if the userExtras couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-extras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserExtras> partialUpdateUserExtras(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserExtras userExtras
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserExtras partially : {}, {}", id, userExtras);
        if (userExtras.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userExtras.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userExtrasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserExtras> result = userExtrasService.partialUpdate(userExtras);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userExtras.getId().toString())
        );
    }

    /**
     * {@code GET  /user-extras} : get all the userExtras.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userExtras in body.
     */
    @GetMapping("/user-extras")
    public ResponseEntity<List<UserExtras>> getAllUserExtras(UserExtrasCriteria criteria) {
        log.debug("REST request to get UserExtras by criteria: {}", criteria);
        List<UserExtras> entityList = userExtrasQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /user-extras/count} : count all the userExtras.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-extras/count")
    public ResponseEntity<Long> countUserExtras(UserExtrasCriteria criteria) {
        log.debug("REST request to count UserExtras by criteria: {}", criteria);
        return ResponseEntity.ok().body(userExtrasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-extras/:id} : get the "id" userExtras.
     *
     * @param id the id of the userExtras to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userExtras, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-extras/{id}")
    public ResponseEntity<UserExtras> getUserExtras(@PathVariable Long id) {
        log.debug("REST request to get UserExtras : {}", id);
        Optional<UserExtras> userExtras = userExtrasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userExtras);
    }

    /**
     * {@code DELETE  /user-extras/:id} : delete the "id" userExtras.
     *
     * @param id the id of the userExtras to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-extras/{id}")
    public ResponseEntity<Void> deleteUserExtras(@PathVariable Long id) {
        log.debug("REST request to delete UserExtras : {}", id);
        userExtrasService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
