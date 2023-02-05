package ceelo.webapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import ceelo.webapp.IntegrationTest;
import ceelo.webapp.domain.DicesRun;
import ceelo.webapp.repository.DicesRunRepository;
import ceelo.webapp.repository.EntityManager;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link DicesRunResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DicesRunResourceIT {

    private static final UUID DEFAULT_GAME_ID = UUID.randomUUID();
    private static final UUID UPDATED_GAME_ID = UUID.randomUUID();

    private static final UUID DEFAULT_PLAYER_ID = UUID.randomUUID();
    private static final UUID UPDATED_PLAYER_ID = UUID.randomUUID();

    private static final Integer DEFAULT_FIRST_DICE = 1;
    private static final Integer UPDATED_FIRST_DICE = 2;

    private static final Integer DEFAULT_MIDDLE_DICE = 1;
    private static final Integer UPDATED_MIDDLE_DICE = 2;

    private static final Integer DEFAULT_LAST_DICE = 1;
    private static final Integer UPDATED_LAST_DICE = 2;

    private static final String ENTITY_API_URL = "/api/dices-runs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DicesRunRepository dicesRunRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private DicesRun dicesRun;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DicesRun createEntity(EntityManager em) {
        DicesRun dicesRun = new DicesRun()
            .id(UUID.randomUUID())
            .gameId(DEFAULT_GAME_ID)
            .playerId(DEFAULT_PLAYER_ID)
            .firstDice(DEFAULT_FIRST_DICE)
            .middleDice(DEFAULT_MIDDLE_DICE)
            .lastDice(DEFAULT_LAST_DICE);
        return dicesRun;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DicesRun createUpdatedEntity(EntityManager em) {
        DicesRun dicesRun = new DicesRun()
            .id(UUID.randomUUID())
            .gameId(UPDATED_GAME_ID)
            .playerId(UPDATED_PLAYER_ID)
            .firstDice(UPDATED_FIRST_DICE)
            .middleDice(UPDATED_MIDDLE_DICE)
            .lastDice(UPDATED_LAST_DICE);
        return dicesRun;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(DicesRun.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        dicesRun = createEntity(em);
    }

    @Test
    void createDicesRun() throws Exception {
        int databaseSizeBeforeCreate = dicesRunRepository.findAll().collectList().block().size();
        dicesRun.setId(null);
        // Create the DicesRun
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dicesRun))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeCreate + 1);
        DicesRun testDicesRun = dicesRunList.get(dicesRunList.size() - 1);
        assertThat(testDicesRun.getGameId()).isEqualTo(DEFAULT_GAME_ID);
        assertThat(testDicesRun.getPlayerId()).isEqualTo(DEFAULT_PLAYER_ID);
        assertThat(testDicesRun.getFirstDice()).isEqualTo(DEFAULT_FIRST_DICE);
        assertThat(testDicesRun.getMiddleDice()).isEqualTo(DEFAULT_MIDDLE_DICE);
        assertThat(testDicesRun.getLastDice()).isEqualTo(DEFAULT_LAST_DICE);
    }

    @Test
    void createDicesRunWithExistingId() throws Exception {
        // Create the DicesRun with an existing ID
        dicesRunRepository.save(dicesRun).block();

        int databaseSizeBeforeCreate = dicesRunRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dicesRun))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkGameIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = dicesRunRepository.findAll().collectList().block().size();
        // set the field null
        dicesRun.setGameId(null);

        // Create the DicesRun, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dicesRun))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPlayerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = dicesRunRepository.findAll().collectList().block().size();
        // set the field null
        dicesRun.setPlayerId(null);

        // Create the DicesRun, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dicesRun))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllDicesRunsAsStream() {
        // Initialize the database
        dicesRun.setId(UUID.randomUUID());
        dicesRunRepository.save(dicesRun).block();

        List<DicesRun> dicesRunList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(DicesRun.class)
            .getResponseBody()
            .filter(dicesRun::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(dicesRunList).isNotNull();
        assertThat(dicesRunList).hasSize(1);
        DicesRun testDicesRun = dicesRunList.get(0);
        assertThat(testDicesRun.getGameId()).isEqualTo(DEFAULT_GAME_ID);
        assertThat(testDicesRun.getPlayerId()).isEqualTo(DEFAULT_PLAYER_ID);
        assertThat(testDicesRun.getFirstDice()).isEqualTo(DEFAULT_FIRST_DICE);
        assertThat(testDicesRun.getMiddleDice()).isEqualTo(DEFAULT_MIDDLE_DICE);
        assertThat(testDicesRun.getLastDice()).isEqualTo(DEFAULT_LAST_DICE);
    }

    @Test
    void getAllDicesRuns() {
        // Initialize the database
        dicesRun.setId(UUID.randomUUID());
        dicesRunRepository.save(dicesRun).block();

        // Get all the dicesRunList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(dicesRun.getId().toString()))
            .jsonPath("$.[*].gameId")
            .value(hasItem(DEFAULT_GAME_ID.toString()))
            .jsonPath("$.[*].playerId")
            .value(hasItem(DEFAULT_PLAYER_ID.toString()))
            .jsonPath("$.[*].firstDice")
            .value(hasItem(DEFAULT_FIRST_DICE))
            .jsonPath("$.[*].middleDice")
            .value(hasItem(DEFAULT_MIDDLE_DICE))
            .jsonPath("$.[*].lastDice")
            .value(hasItem(DEFAULT_LAST_DICE));
    }

    @Test
    void getDicesRun() {
        // Initialize the database
        dicesRun.setId(UUID.randomUUID());
        dicesRunRepository.save(dicesRun).block();

        // Get the dicesRun
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, dicesRun.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(dicesRun.getId().toString()))
            .jsonPath("$.gameId")
            .value(is(DEFAULT_GAME_ID.toString()))
            .jsonPath("$.playerId")
            .value(is(DEFAULT_PLAYER_ID.toString()))
            .jsonPath("$.firstDice")
            .value(is(DEFAULT_FIRST_DICE))
            .jsonPath("$.middleDice")
            .value(is(DEFAULT_MIDDLE_DICE))
            .jsonPath("$.lastDice")
            .value(is(DEFAULT_LAST_DICE));
    }

    @Test
    void getNonExistingDicesRun() {
        // Get the dicesRun
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDicesRun() throws Exception {
        // Initialize the database
        dicesRun.setId(UUID.randomUUID());
        dicesRunRepository.save(dicesRun).block();

        int databaseSizeBeforeUpdate = dicesRunRepository.findAll().collectList().block().size();

        // Update the dicesRun
        DicesRun updatedDicesRun = dicesRunRepository.findById(dicesRun.getId()).block();
        updatedDicesRun
            .gameId(UPDATED_GAME_ID)
            .playerId(UPDATED_PLAYER_ID)
            .firstDice(UPDATED_FIRST_DICE)
            .middleDice(UPDATED_MIDDLE_DICE)
            .lastDice(UPDATED_LAST_DICE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedDicesRun.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedDicesRun))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeUpdate);
        DicesRun testDicesRun = dicesRunList.get(dicesRunList.size() - 1);
        assertThat(testDicesRun.getGameId()).isEqualTo(UPDATED_GAME_ID);
        assertThat(testDicesRun.getPlayerId()).isEqualTo(UPDATED_PLAYER_ID);
        assertThat(testDicesRun.getFirstDice()).isEqualTo(UPDATED_FIRST_DICE);
        assertThat(testDicesRun.getMiddleDice()).isEqualTo(UPDATED_MIDDLE_DICE);
        assertThat(testDicesRun.getLastDice()).isEqualTo(UPDATED_LAST_DICE);
    }

    @Test
    void putNonExistingDicesRun() throws Exception {
        int databaseSizeBeforeUpdate = dicesRunRepository.findAll().collectList().block().size();
        dicesRun.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, dicesRun.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dicesRun))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDicesRun() throws Exception {
        int databaseSizeBeforeUpdate = dicesRunRepository.findAll().collectList().block().size();
        dicesRun.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dicesRun))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDicesRun() throws Exception {
        int databaseSizeBeforeUpdate = dicesRunRepository.findAll().collectList().block().size();
        dicesRun.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dicesRun))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDicesRunWithPatch() throws Exception {
        // Initialize the database
        dicesRun.setId(UUID.randomUUID());
        dicesRunRepository.save(dicesRun).block();

        int databaseSizeBeforeUpdate = dicesRunRepository.findAll().collectList().block().size();

        // Update the dicesRun using partial update
        DicesRun partialUpdatedDicesRun = new DicesRun();
        partialUpdatedDicesRun.setId(dicesRun.getId());

        partialUpdatedDicesRun
            .playerId(UPDATED_PLAYER_ID)
            .firstDice(UPDATED_FIRST_DICE)
            .middleDice(UPDATED_MIDDLE_DICE)
            .lastDice(UPDATED_LAST_DICE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDicesRun.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDicesRun))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeUpdate);
        DicesRun testDicesRun = dicesRunList.get(dicesRunList.size() - 1);
        assertThat(testDicesRun.getGameId()).isEqualTo(DEFAULT_GAME_ID);
        assertThat(testDicesRun.getPlayerId()).isEqualTo(UPDATED_PLAYER_ID);
        assertThat(testDicesRun.getFirstDice()).isEqualTo(UPDATED_FIRST_DICE);
        assertThat(testDicesRun.getMiddleDice()).isEqualTo(UPDATED_MIDDLE_DICE);
        assertThat(testDicesRun.getLastDice()).isEqualTo(UPDATED_LAST_DICE);
    }

    @Test
    void fullUpdateDicesRunWithPatch() throws Exception {
        // Initialize the database
        dicesRun.setId(UUID.randomUUID());
        dicesRunRepository.save(dicesRun).block();

        int databaseSizeBeforeUpdate = dicesRunRepository.findAll().collectList().block().size();

        // Update the dicesRun using partial update
        DicesRun partialUpdatedDicesRun = new DicesRun();
        partialUpdatedDicesRun.setId(dicesRun.getId());

        partialUpdatedDicesRun
            .gameId(UPDATED_GAME_ID)
            .playerId(UPDATED_PLAYER_ID)
            .firstDice(UPDATED_FIRST_DICE)
            .middleDice(UPDATED_MIDDLE_DICE)
            .lastDice(UPDATED_LAST_DICE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDicesRun.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDicesRun))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeUpdate);
        DicesRun testDicesRun = dicesRunList.get(dicesRunList.size() - 1);
        assertThat(testDicesRun.getGameId()).isEqualTo(UPDATED_GAME_ID);
        assertThat(testDicesRun.getPlayerId()).isEqualTo(UPDATED_PLAYER_ID);
        assertThat(testDicesRun.getFirstDice()).isEqualTo(UPDATED_FIRST_DICE);
        assertThat(testDicesRun.getMiddleDice()).isEqualTo(UPDATED_MIDDLE_DICE);
        assertThat(testDicesRun.getLastDice()).isEqualTo(UPDATED_LAST_DICE);
    }

    @Test
    void patchNonExistingDicesRun() throws Exception {
        int databaseSizeBeforeUpdate = dicesRunRepository.findAll().collectList().block().size();
        dicesRun.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, dicesRun.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dicesRun))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDicesRun() throws Exception {
        int databaseSizeBeforeUpdate = dicesRunRepository.findAll().collectList().block().size();
        dicesRun.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dicesRun))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDicesRun() throws Exception {
        int databaseSizeBeforeUpdate = dicesRunRepository.findAll().collectList().block().size();
        dicesRun.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dicesRun))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DicesRun in the database
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDicesRun() {
        // Initialize the database
        dicesRun.setId(UUID.randomUUID());
        dicesRunRepository.save(dicesRun).block();

        int databaseSizeBeforeDelete = dicesRunRepository.findAll().collectList().block().size();

        // Delete the dicesRun
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, dicesRun.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<DicesRun> dicesRunList = dicesRunRepository.findAll().collectList().block();
        assertThat(dicesRunList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
