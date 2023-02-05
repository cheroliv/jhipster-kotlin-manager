package ceelo.webapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ceelo.webapp.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Player.class);
        Player player1 = new Player();
        player1.setId(UUID.randomUUID());
        Player player2 = new Player();
        player2.setId(player1.getId());
        assertThat(player1).isEqualTo(player2);
        player2.setId(UUID.randomUUID());
        assertThat(player1).isNotEqualTo(player2);
        player1.setId(null);
        assertThat(player1).isNotEqualTo(player2);
    }
}
