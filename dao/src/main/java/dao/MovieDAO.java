package dao;

import datasource.ConnectionFactory;
import mapper.MovieRowMapper;
import mapper.RowMapper;
import model.Movie;
import util.IdSequence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAO implements DAO<Movie, Long> {
    private static IdSequence idSequence = new IdSequence();
    private static RowMapper<Movie> rowMapper = MovieRowMapper.getInstance();
    private static MovieDAO instance;

    private MovieDAO() {}

    public static MovieDAO getInstance() {
        synchronized (MovieDAO.class) {
            if (instance == null)
                instance = new MovieDAO();
            return instance;
        }
    }

    @Override
    public Movie insert(Movie movie) throws SQLException {
        Long id = idSequence.getNext();

        String query = "INSERT INTO movie VALUES (?, ?)";
        try (var conn = ConnectionFactory.getConnection()) {
            var stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.setString(2, movie.getName());
            stmt.executeUpdate();
        }

        movie.setId(id);
        return movie;
    }


    @Override
    public Optional<Movie> get(Long id) throws SQLException {
        String query = "SELECT * FROM movie WHERE id=?";
        Optional<Movie> result;

        try (var conn = ConnectionFactory.getConnection()) {
            ResultSet rs;
            var stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            result = rs.next() ? Optional.ofNullable(rowMapper.map(rs)) : Optional.empty();
        }

        return result;
    }

    @Override
    public List<Movie> get() throws SQLException {
        String query = "SELECT * FROM movie";
        List<Movie> result = new ArrayList<>();

        try (var conn = ConnectionFactory.getConnection()) {
            ResultSet rs;
            var stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                result.add(rowMapper.map(rs));
            }
        }

        return result;
    }
}
