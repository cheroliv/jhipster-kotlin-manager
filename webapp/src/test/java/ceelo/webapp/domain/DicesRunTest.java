package ceelo.webapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ceelo.webapp.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DicesRunTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DicesRun.class);
        DicesRun dicesRun1 = new DicesRun();
        dicesRun1.setId(UUID.randomUUID());
        DicesRun dicesRun2 = new DicesRun();
        dicesRun2.setId(dicesRun1.getId());
        assertThat(dicesRun1).isEqualTo(dicesRun2);
        dicesRun2.setId(UUID.randomUUID());
        assertThat(dicesRun1).isNotEqualTo(dicesRun2);
        dicesRun1.setId(null);
        assertThat(dicesRun1).isNotEqualTo(dicesRun2);
    }
}
