package util;

import datasource.ConnectionFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Migration {
    public static void migrate() throws SQLException {
        createTables();
    }

    public static void createTables() throws SQLException {
        List<String> tables = new ArrayList<>();

        tables.add("CREATE TABLE IF NOT EXISTS movie (id BIGINT UNIQUE NOT NULL, name VARCHAR(255) NOT NULL)");

        try (var conn = ConnectionFactory.getConnection()){
            var stmt = conn.createStatement();
            for (var query : tables)
                stmt.execute(query);
        }
    }
}
