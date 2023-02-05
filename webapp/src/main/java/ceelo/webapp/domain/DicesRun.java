package ceelo.webapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A DicesRun.
 */
@Table("dices_run")
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DicesRun implements Serializable, Persistable<UUID> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private UUID id;

    @NotNull(message = "must not be null")
    @Column("game_id")
    private UUID gameId;

    @NotNull(message = "must not be null")
    @Column("player_id")
    private UUID playerId;

    @Min(value = 1)
    @Max(value = 6)
    @Column("first_dice")
    private Integer firstDice;

    @Min(value = 1)
    @Max(value = 6)
    @Column("middle_dice")
    private Integer middleDice;

    @Min(value = 1)
    @Max(value = 6)
    @Column("last_dice")
    private Integer lastDice;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public DicesRun id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGameId() {
        return this.gameId;
    }

    public DicesRun gameId(UUID gameId) {
        this.setGameId(gameId);
        return this;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayerId() {
        return this.playerId;
    }

    public DicesRun playerId(UUID playerId) {
        this.setPlayerId(playerId);
        return this;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public Integer getFirstDice() {
        return this.firstDice;
    }

    public DicesRun firstDice(Integer firstDice) {
        this.setFirstDice(firstDice);
        return this;
    }

    public void setFirstDice(Integer firstDice) {
        this.firstDice = firstDice;
    }

    public Integer getMiddleDice() {
        return this.middleDice;
    }

    public DicesRun middleDice(Integer middleDice) {
        this.setMiddleDice(middleDice);
        return this;
    }

    public void setMiddleDice(Integer middleDice) {
        this.middleDice = middleDice;
    }

    public Integer getLastDice() {
        return this.lastDice;
    }

    public DicesRun lastDice(Integer lastDice) {
        this.setLastDice(lastDice);
        return this;
    }

    public void setLastDice(Integer lastDice) {
        this.lastDice = lastDice;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public DicesRun setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DicesRun)) {
            return false;
        }
        return id != null && id.equals(((DicesRun) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DicesRun{" +
            "id=" + getId() +
            ", gameId='" + getGameId() + "'" +
            ", playerId='" + getPlayerId() + "'" +
            ", firstDice=" + getFirstDice() +
            ", middleDice=" + getMiddleDice() +
            ", lastDice=" + getLastDice() +
            "}";
    }
}
