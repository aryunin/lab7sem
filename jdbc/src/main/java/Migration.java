import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Migration {
    public static void migrate() throws SQLException {
        createTables();
        insertData();
    }

    private static void createTables() throws SQLException {
        List<String> tables = new ArrayList<>();

        tables.add("CREATE TABLE IF NOT EXISTS movie (id BIGINT UNIQUE NOT NULL, name VARCHAR(255) NOT NULL)");

        try (var conn = ConnectionFactory.getConnection()){
            var stmt = conn.createStatement();
            for (var query : tables)
                stmt.execute(query);
        }
    }

    private static void insertData() throws SQLException {
        List<String> movies = List.of("Titanic", "Pulp Fiction");

        try (var conn = ConnectionFactory.getConnection()){
            var stmt = conn.prepareStatement("INSERT INTO movie VALUES (?, ?)");

            for (var movie : movies) {
                Long id = IdSequence.getNext();

                stmt.setLong(1, id);
                stmt.setString(2, movie);

                stmt.executeUpdate();
            }
        }
    }
}
