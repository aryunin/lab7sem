package dao;

import datasource.ConnectionFactory;
import mapper.DirectorRowMapper;
import mapper.RowMapper;
import model.Director;
import model.Movie;
import util.IdSequence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DirectorDAO implements DAO<Director, Long> {
    private static final IdSequence idSequence = new IdSequence();
    private static final RowMapper<Director> rowMapper = DirectorRowMapper.getInstance();
    private static DirectorDAO instance;

    private DirectorDAO() {}

    public static DirectorDAO getInstance() {
        synchronized (DirectorDAO.class) {
            if (instance == null)
                instance = new DirectorDAO();
            return instance;
        }
    }

    @Override
    public Director insert(Director director) throws SQLException {
        Long id = idSequence.getNext();

        String query = "INSERT INTO director VALUES (?, ?, ?)";
        try (var conn = ConnectionFactory.getConnection()) {
            var stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.setString(2, director.getFirstName());
            stmt.setString(3, director.getSecondName());
            stmt.executeUpdate();
        }

        director.setId(id);
        return director;
    }

    @Override
    public Optional<Director> get(Long id) throws SQLException {
        String query = "SELECT * FROM director WHERE id=?";
        Optional<Director> result;

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
    public List<Director> get() throws SQLException {
        String query = "SELECT * FROM director";
        List<Director> result = new ArrayList<>();

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
