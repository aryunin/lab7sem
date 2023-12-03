package mapper;

import model.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {
    private static MovieRowMapper instance;

    private MovieRowMapper() {}

    public static MovieRowMapper getInstance() {
        synchronized (MovieRowMapper.class) {
            if (instance == null)
                instance = new MovieRowMapper();
            return instance;
        }
    }

    @Override
    public Movie map(ResultSet rs) throws SQLException {
        return new Movie(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
}
