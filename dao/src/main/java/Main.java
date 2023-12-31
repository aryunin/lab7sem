import dao.DAO;
import dao.MovieDAO;
import model.Movie;
import util.Migration;

import java.sql.SQLException;
import java.util.List;

public class Main {
    private static DAO<Movie, Long> movieDAO = MovieDAO.getInstance();

    public static void main(String[] args) throws SQLException {
        Migration.migrate();

        List<Movie> movies = List.of(
                new Movie("Pulp Fiction"),
                new Movie("Titanic"),
                new Movie("La-La Land")
        );

        for (Movie movie : movies)
            movieDAO.insert(movie);

        System.out.println(movieDAO.get(1L));
        System.out.println(movieDAO.get());
    }
}
