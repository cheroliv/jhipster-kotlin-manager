package ceelo.webapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import ceelo.webapp.IntegrationTest;
import ceelo.webapp.domain.Player;
import ceelo.webapp.repository.EntityManager;
import ceelo.webapp.repository.PlayerRepository;
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
 * Integration tests for the {@link PlayerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class PlayerResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/players";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Player player;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Player createEntity(EntityManager em) {
        Player player = new Player().id(UUID.randomUUID()).login(DEFAULT_LOGIN);
        return player;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Player createUpdatedEntity(EntityManager em) {
        Player player = new Player().id(UUID.randomUUID()).login(UPDATED_LOGIN);
        return player;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Player.class).block();
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
        player = createEntity(em);
    }

    @Test
    void createPlayer() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().collectList().block().size();
        player.setId(null);
        // Create the Player
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(player))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate + 1);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getLogin()).isEqualTo(DEFAULT_LOGIN);
    }

    @Test
    void createPlayerWithExistingId() throws Exception {
        // Create the Player with an existing ID
        playerRepository.save(player).block();

        int databaseSizeBeforeCreate = playerRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(player))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().collectList().block().size();
        // set the field null
        player.setLogin(null);

        // Create the Player, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(player))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPlayersAsStream() {
        // Initialize the database
        player.setId(UUID.randomUUID());
        playerRepository.save(player).block();

        List<Player> playerList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Player.class)
            .getResponseBody()
            .filter(player::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(playerList).isNotNull();
        assertThat(playerList).hasSize(1);
        Player testPlayer = playerList.get(0);
        assertThat(testPlayer.getLogin()).isEqualTo(DEFAULT_LOGIN);
    }

    @Test
    void getAllPlayers() {
        // Initialize the database
        player.setId(UUID.randomUUID());
        playerRepository.save(player).block();

        // Get all the playerList
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
            .value(hasItem(player.getId().toString()))
            .jsonPath("$.[*].login")
            .value(hasItem(DEFAULT_LOGIN));
    }

    @Test
    void getPlayer() {
        // Initialize the database
        player.setId(UUID.randomUUID());
        playerRepository.save(player).block();

        // Get the player
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, player.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(player.getId().toString()))
            .jsonPath("$.login")
            .value(is(DEFAULT_LOGIN));
    }

    @Test
    void getNonExistingPlayer() {
        // Get the player
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingPlayer() throws Exception {
        // Initialize the database
        player.setId(UUID.randomUUID());
        playerRepository.save(player).block();

        int databaseSizeBeforeUpdate = playerRepository.findAll().collectList().block().size();

        // Update the player
        Player updatedPlayer = playerRepository.findById(player.getId()).block();
        updatedPlayer.login(UPDATED_LOGIN);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedPlayer.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedPlayer))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getLogin()).isEqualTo(UPDATED_LOGIN);
    }

    @Test
    void putNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().collectList().block().size();
        player.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, player.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(player))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().collectList().block().size();
        player.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(player))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().collectList().block().size();
        player.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(player))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePlayerWithPatch() throws Exception {
        // Initialize the database
        player.setId(UUID.randomUUID());
        playerRepository.save(player).block();

        int databaseSizeBeforeUpdate = playerRepository.findAll().collectList().block().size();

        // Update the player using partial update
        Player partialUpdatedPlayer = new Player();
        partialUpdatedPlayer.setId(player.getId());

        partialUpdatedPlayer.login(UPDATED_LOGIN);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPlayer.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayer))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getLogin()).isEqualTo(UPDATED_LOGIN);
    }

    @Test
    void fullUpdatePlayerWithPatch() throws Exception {
        // Initialize the database
        player.setId(UUID.randomUUID());
        playerRepository.save(player).block();

        int databaseSizeBeforeUpdate = playerRepository.findAll().collectList().block().size();

        // Update the player using partial update
        Player partialUpdatedPlayer = new Player();
        partialUpdatedPlayer.setId(player.getId());

        partialUpdatedPlayer.login(UPDATED_LOGIN);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPlayer.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayer))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getLogin()).isEqualTo(UPDATED_LOGIN);
    }

    @Test
    void patchNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().collectList().block().size();
        player.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, player.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(player))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().collectList().block().size();
        player.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(player))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().collectList().block().size();
        player.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(player))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePlayer() {
        // Initialize the database
        player.setId(UUID.randomUUID());
        playerRepository.save(player).block();

        int databaseSizeBeforeDelete = playerRepository.findAll().collectList().block().size();

        // Delete the player
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, player.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Player> playerList = playerRepository.findAll().collectList().block();
        assertThat(playerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
