package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T, ID> {
    T insert(T obj) throws SQLException;
    Optional<T> get(ID id) throws SQLException;
    List<T> get() throws SQLException;
}
