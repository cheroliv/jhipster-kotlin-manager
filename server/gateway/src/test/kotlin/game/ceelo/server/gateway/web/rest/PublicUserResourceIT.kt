package game.ceelo.server.gateway.web.rest

import game.ceelo.server.gateway.IntegrationTest
import game.ceelo.server.gateway.domain.User
import game.ceelo.server.gateway.repository.EntityManager
import game.ceelo.server.gateway.repository.UserRepository
import game.ceelo.server.gateway.security.ADMIN
import game.ceelo.server.gateway.security.USER
import game.ceelo.server.gateway.service.dto.UserDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * Integration tests for the {@link UserResource} REST controller.
 */
@AutoConfigureWebTestClient
@WithMockUser(authorities = [ADMIN])
@IntegrationTest
class PublicUserResourceIT {

    private val DEFAULT_LOGIN = "johndoe"

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var em: EntityManager

    @Autowired
    private lateinit var webTestClient: WebTestClient

    private lateinit var user: User

    @BeforeEach
    fun initTest() {
        user = UserResourceIT.initTestUser(userRepository, em)
    }

    @Test
    fun getAllPublicUsers() {
        // Initialize the database
        userRepository.save(user).block()

        // Get all the users
        val foundUser = webTestClient.get().uri("/api/users?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .returnResult(UserDTO::class.java).responseBody.blockFirst()

        assertThat(foundUser.login).isEqualTo(DEFAULT_LOGIN)
    }

    @Test
    fun getAllAuthorities() {
        webTestClient.get().uri("/api/authorities")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody()
            .jsonPath("$").isArray()
            .jsonPath("$[?(@=='$ADMIN')]").hasJsonPath()
            .jsonPath("$[?(@=='$USER')]").hasJsonPath()
    }

    @Test
    @Throws(Exception::class)
    fun getAllUsersSortedByParameters() {
        // Initialize the database
        userRepository.save(user).block()

        webTestClient.get().uri("/api/users?sort=resetKey,desc").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isBadRequest
        webTestClient.get().uri("/api/users?sort=password,desc").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isBadRequest
        webTestClient.get().uri("/api/users?sort=resetKey,id,desc").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isBadRequest
        webTestClient.get().uri("/api/users?sort=id,desc").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk
    }
}
