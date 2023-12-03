package dao;

import datasource.ConnectionFactory;
import mapper.MovieRowMapper;
import mapper.RowMapper;
import model.Director;
import model.Movie;
import util.IdSequence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAO implements DAO<Movie, Long> {
    private static IdSequence idSequence = new IdSequence();
    private static RowMapper<Movie> rowMapper = MovieRowMapper.getInstance();
    private static DAO<Director, Long> directorDAO = DirectorDAO.getInstance();
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

        Director director = movie.getDirector();
        if (director != null) saveDirector(movie, director);

        String query = "INSERT INTO movie VALUES (?, ?, ?)";
        try (var conn = ConnectionFactory.getConnection()) {
            var stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.setString(2, movie.getName());
            if (movie.getDirector() != null)
                stmt.setLong(3, movie.getDirector().getId());
            else
                stmt.setNull(3, Types.BIGINT);
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

            if (rs.next()) {
                Movie movie = rowMapper.map(rs);
                long directorId = rs.getLong("director_id"); // в идеале это должно быть не тут
                result = Optional.of(loadDirector(movie, directorId));
            }
            else {
                result = Optional.empty();
            }
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
                Movie movie = rowMapper.map(rs);
                long directorId = rs.getLong("director_id"); // в идеале это должно быть не тут
                result.add(loadDirector(movie, directorId));
            }
        }

        return result;
    }

    private Movie loadDirector(Movie movie, long directorId) throws SQLException {
        Director director = directorDAO.get(directorId)
                .orElseThrow(() -> new RuntimeException("Incorrect director id"));
        movie.setDirector(director);
        return movie;
    }

    private Movie saveDirector(Movie movie, Director director) throws SQLException {
        if (director.getId() == null || director.getId() == 0L)
            directorDAO.insert(director);
        movie.setDirector(director);
        return movie;
    }
}
