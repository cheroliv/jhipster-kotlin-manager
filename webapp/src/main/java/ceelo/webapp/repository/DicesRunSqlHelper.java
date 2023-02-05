package ceelo.webapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class DicesRunSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("game_id", table, columnPrefix + "_game_id"));
        columns.add(Column.aliased("player_id", table, columnPrefix + "_player_id"));
        columns.add(Column.aliased("first_dice", table, columnPrefix + "_first_dice"));
        columns.add(Column.aliased("middle_dice", table, columnPrefix + "_middle_dice"));
        columns.add(Column.aliased("last_dice", table, columnPrefix + "_last_dice"));

        return columns;
    }
}
