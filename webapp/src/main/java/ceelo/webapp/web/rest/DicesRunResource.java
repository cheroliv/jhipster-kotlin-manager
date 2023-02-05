package ceelo.webapp.web.rest;

import ceelo.webapp.domain.DicesRun;
import ceelo.webapp.repository.DicesRunRepository;
import ceelo.webapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link ceelo.webapp.domain.DicesRun}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DicesRunResource {

    private final Logger log = LoggerFactory.getLogger(DicesRunResource.class);

    private static final String ENTITY_NAME = "dicesRun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DicesRunRepository dicesRunRepository;

    public DicesRunResource(DicesRunRepository dicesRunRepository) {
        this.dicesRunRepository = dicesRunRepository;
    }

    /**
     * {@code POST  /dices-runs} : Create a new dicesRun.
     *
     * @param dicesRun the dicesRun to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dicesRun, or with status {@code 400 (Bad Request)} if the dicesRun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dices-runs")
    public Mono<ResponseEntity<DicesRun>> createDicesRun(@Valid @RequestBody DicesRun dicesRun) throws URISyntaxException {
        log.debug("REST request to save DicesRun : {}", dicesRun);
        if (dicesRun.getId() != null) {
            throw new BadRequestAlertException("A new dicesRun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dicesRun.setId(UUID.randomUUID());
        return dicesRunRepository
            .save(dicesRun)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/dices-runs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /dices-runs/:id} : Updates an existing dicesRun.
     *
     * @param id the id of the dicesRun to save.
     * @param dicesRun the dicesRun to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dicesRun,
     * or with status {@code 400 (Bad Request)} if the dicesRun is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dicesRun couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dices-runs/{id}")
    public Mono<ResponseEntity<DicesRun>> updateDicesRun(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody DicesRun dicesRun
    ) throws URISyntaxException {
        log.debug("REST request to update DicesRun : {}, {}", id, dicesRun);
        if (dicesRun.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dicesRun.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return dicesRunRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return dicesRunRepository
                    .save(dicesRun.setIsPersisted())
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /dices-runs/:id} : Partial updates given fields of an existing dicesRun, field will ignore if it is null
     *
     * @param id the id of the dicesRun to save.
     * @param dicesRun the dicesRun to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dicesRun,
     * or with status {@code 400 (Bad Request)} if the dicesRun is not valid,
     * or with status {@code 404 (Not Found)} if the dicesRun is not found,
     * or with status {@code 500 (Internal Server Error)} if the dicesRun couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dices-runs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<DicesRun>> partialUpdateDicesRun(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody DicesRun dicesRun
    ) throws URISyntaxException {
        log.debug("REST request to partial update DicesRun partially : {}, {}", id, dicesRun);
        if (dicesRun.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dicesRun.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return dicesRunRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<DicesRun> result = dicesRunRepository
                    .findById(dicesRun.getId())
                    .map(existingDicesRun -> {
                        if (dicesRun.getGameId() != null) {
                            existingDicesRun.setGameId(dicesRun.getGameId());
                        }
                        if (dicesRun.getPlayerId() != null) {
                            existingDicesRun.setPlayerId(dicesRun.getPlayerId());
                        }
                        if (dicesRun.getFirstDice() != null) {
                            existingDicesRun.setFirstDice(dicesRun.getFirstDice());
                        }
                        if (dicesRun.getMiddleDice() != null) {
                            existingDicesRun.setMiddleDice(dicesRun.getMiddleDice());
                        }
                        if (dicesRun.getLastDice() != null) {
                            existingDicesRun.setLastDice(dicesRun.getLastDice());
                        }

                        return existingDicesRun;
                    })
                    .flatMap(dicesRunRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /dices-runs} : get all the dicesRuns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dicesRuns in body.
     */
    @GetMapping("/dices-runs")
    public Mono<List<DicesRun>> getAllDicesRuns() {
        log.debug("REST request to get all DicesRuns");
        return dicesRunRepository.findAll().collectList();
    }

    /**
     * {@code GET  /dices-runs} : get all the dicesRuns as a stream.
     * @return the {@link Flux} of dicesRuns.
     */
    @GetMapping(value = "/dices-runs", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DicesRun> getAllDicesRunsAsStream() {
        log.debug("REST request to get all DicesRuns as a stream");
        return dicesRunRepository.findAll();
    }

    /**
     * {@code GET  /dices-runs/:id} : get the "id" dicesRun.
     *
     * @param id the id of the dicesRun to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dicesRun, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dices-runs/{id}")
    public Mono<ResponseEntity<DicesRun>> getDicesRun(@PathVariable UUID id) {
        log.debug("REST request to get DicesRun : {}", id);
        Mono<DicesRun> dicesRun = dicesRunRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dicesRun);
    }

    /**
     * {@code DELETE  /dices-runs/:id} : delete the "id" dicesRun.
     *
     * @param id the id of the dicesRun to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dices-runs/{id}")
    public Mono<ResponseEntity<Void>> deleteDicesRun(@PathVariable UUID id) {
        log.debug("REST request to delete DicesRun : {}", id);
        return dicesRunRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
