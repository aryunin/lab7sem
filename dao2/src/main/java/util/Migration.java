package util;

import datasource.ConnectionFactory;

import java.sql.SQLException;

public class Migration {
    public static void migrate() throws SQLException {
        createTables();
    }

    public static void createTables() throws SQLException {
        String query = """
                CREATE TABLE IF NOT EXISTS director (
                    id BIGINT UNIQUE NOT NULL,
                    first_name VARCHAR(255) NOT NULL,
                    last_name VARCHAR(255) NOT NULL
                );
                
                CREATE TABLE IF NOT EXISTS movie (
                    id BIGINT UNIQUE NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    director_id BIGINT,
                    foreign key (director_id) references director(id)
                );
                """;


        try (var conn = ConnectionFactory.getConnection()){
            var stmt = conn.createStatement();
            stmt.execute(query);
        }
    }
}
