package mapper;

import model.Director;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectorRowMapper implements RowMapper<Director> {
    private static DirectorRowMapper instance;

    private DirectorRowMapper() {}

    public static DirectorRowMapper getInstance() {
        synchronized (DirectorRowMapper.class) {
            if (instance == null)
                instance = new DirectorRowMapper();
            return instance;
        }
    }

    @Override
    public Director map(ResultSet rs) throws SQLException {
        return new Director(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }
}
