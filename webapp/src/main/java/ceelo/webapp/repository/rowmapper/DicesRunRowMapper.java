package ceelo.webapp.repository.rowmapper;

import ceelo.webapp.domain.DicesRun;
import io.r2dbc.spi.Row;
import java.util.UUID;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link DicesRun}, with proper type conversions.
 */
@Service
public class DicesRunRowMapper implements BiFunction<Row, String, DicesRun> {

    private final ColumnConverter converter;

    public DicesRunRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link DicesRun} stored in the database.
     */
    @Override
    public DicesRun apply(Row row, String prefix) {
        DicesRun entity = new DicesRun();
        entity.setId(converter.fromRow(row, prefix + "_id", UUID.class));
        entity.setGameId(converter.fromRow(row, prefix + "_game_id", UUID.class));
        entity.setPlayerId(converter.fromRow(row, prefix + "_player_id", UUID.class));
        entity.setFirstDice(converter.fromRow(row, prefix + "_first_dice", Integer.class));
        entity.setMiddleDice(converter.fromRow(row, prefix + "_middle_dice", Integer.class));
        entity.setLastDice(converter.fromRow(row, prefix + "_last_dice", Integer.class));
        return entity;
    }
}
