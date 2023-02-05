package ceelo.webapp.repository.rowmapper;

import ceelo.webapp.domain.Player;
import io.r2dbc.spi.Row;
import java.util.UUID;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Player}, with proper type conversions.
 */
@Service
public class PlayerRowMapper implements BiFunction<Row, String, Player> {

    private final ColumnConverter converter;

    public PlayerRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Player} stored in the database.
     */
    @Override
    public Player apply(Row row, String prefix) {
        Player entity = new Player();
        entity.setId(converter.fromRow(row, prefix + "_id", UUID.class));
        entity.setLogin(converter.fromRow(row, prefix + "_login", String.class));
        return entity;
    }
}
