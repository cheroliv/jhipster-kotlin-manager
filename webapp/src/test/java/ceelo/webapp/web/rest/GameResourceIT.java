package ceelo.webapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import ceelo.webapp.IntegrationTest;
import ceelo.webapp.domain.Game;
import ceelo.webapp.repository.EntityManager;
import ceelo.webapp.repository.GameRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link GameResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class GameResourceIT {

    private static final UUID DEFAULT_WINNER_PLAYER_ID = UUID.randomUUID();
    private static final UUID UPDATED_WINNER_PLAYER_ID = UUID.randomUUID();

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/games";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Game game;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createEntity(EntityManager em) {
        Game game = new Game().id(UUID.randomUUID()).winnerPlayerId(DEFAULT_WINNER_PLAYER_ID).date(DEFAULT_DATE);
        return game;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createUpdatedEntity(EntityManager em) {
        Game game = new Game().id(UUID.randomUUID()).winnerPlayerId(UPDATED_WINNER_PLAYER_ID).date(UPDATED_DATE);
        return game;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Game.class).block();
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
        game = createEntity(em);
    }

    @Test
    void createGame() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().collectList().block().size();
        game.setId(null);
        // Create the Game
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(game))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate + 1);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getWinnerPlayerId()).isEqualTo(DEFAULT_WINNER_PLAYER_ID);
        assertThat(testGame.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    void createGameWithExistingId() throws Exception {
        // Create the Game with an existing ID
        gameRepository.save(game).block();

        int databaseSizeBeforeCreate = gameRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(game))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkWinnerPlayerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().collectList().block().size();
        // set the field null
        game.setWinnerPlayerId(null);

        // Create the Game, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(game))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().collectList().block().size();
        // set the field null
        game.setDate(null);

        // Create the Game, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(game))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllGamesAsStream() {
        // Initialize the database
        game.setId(UUID.randomUUID());
        gameRepository.save(game).block();

        List<Game> gameList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Game.class)
            .getResponseBody()
            .filter(game::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(gameList).isNotNull();
        assertThat(gameList).hasSize(1);
        Game testGame = gameList.get(0);
        assertThat(testGame.getWinnerPlayerId()).isEqualTo(DEFAULT_WINNER_PLAYER_ID);
        assertThat(testGame.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    void getAllGames() {
        // Initialize the database
        game.setId(UUID.randomUUID());
        gameRepository.save(game).block();

        // Get all the gameList
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
            .value(hasItem(game.getId().toString()))
            .jsonPath("$.[*].winnerPlayerId")
            .value(hasItem(DEFAULT_WINNER_PLAYER_ID.toString()))
            .jsonPath("$.[*].date")
            .value(hasItem(DEFAULT_DATE.toString()));
    }

    @Test
    void getGame() {
        // Initialize the database
        game.setId(UUID.randomUUID());
        gameRepository.save(game).block();

        // Get the game
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, game.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(game.getId().toString()))
            .jsonPath("$.winnerPlayerId")
            .value(is(DEFAULT_WINNER_PLAYER_ID.toString()))
            .jsonPath("$.date")
            .value(is(DEFAULT_DATE.toString()));
    }

    @Test
    void getNonExistingGame() {
        // Get the game
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingGame() throws Exception {
        // Initialize the database
        game.setId(UUID.randomUUID());
        gameRepository.save(game).block();

        int databaseSizeBeforeUpdate = gameRepository.findAll().collectList().block().size();

        // Update the game
        Game updatedGame = gameRepository.findById(game.getId()).block();
        updatedGame.winnerPlayerId(UPDATED_WINNER_PLAYER_ID).date(UPDATED_DATE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedGame.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedGame))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getWinnerPlayerId()).isEqualTo(UPDATED_WINNER_PLAYER_ID);
        assertThat(testGame.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    void putNonExistingGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().collectList().block().size();
        game.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, game.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(game))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().collectList().block().size();
        game.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(game))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().collectList().block().size();
        game.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(game))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGameWithPatch() throws Exception {
        // Initialize the database
        game.setId(UUID.randomUUID());
        gameRepository.save(game).block();

        int databaseSizeBeforeUpdate = gameRepository.findAll().collectList().block().size();

        // Update the game using partial update
        Game partialUpdatedGame = new Game();
        partialUpdatedGame.setId(game.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedGame.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedGame))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getWinnerPlayerId()).isEqualTo(DEFAULT_WINNER_PLAYER_ID);
        assertThat(testGame.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    void fullUpdateGameWithPatch() throws Exception {
        // Initialize the database
        game.setId(UUID.randomUUID());
        gameRepository.save(game).block();

        int databaseSizeBeforeUpdate = gameRepository.findAll().collectList().block().size();

        // Update the game using partial update
        Game partialUpdatedGame = new Game();
        partialUpdatedGame.setId(game.getId());

        partialUpdatedGame.winnerPlayerId(UPDATED_WINNER_PLAYER_ID).date(UPDATED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedGame.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedGame))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getWinnerPlayerId()).isEqualTo(UPDATED_WINNER_PLAYER_ID);
        assertThat(testGame.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    void patchNonExistingGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().collectList().block().size();
        game.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, game.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(game))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().collectList().block().size();
        game.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(game))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().collectList().block().size();
        game.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(game))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGame() {
        // Initialize the database
        game.setId(UUID.randomUUID());
        gameRepository.save(game).block();

        int databaseSizeBeforeDelete = gameRepository.findAll().collectList().block().size();

        // Delete the game
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, game.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Game> gameList = gameRepository.findAll().collectList().block();
        assertThat(gameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
