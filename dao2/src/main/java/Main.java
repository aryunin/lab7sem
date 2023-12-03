import dao.DAO;
import dao.MovieDAO;
import model.Director;
import model.Movie;
import util.Migration;

import java.sql.SQLException;
import java.util.List;

public class Main {
    private static DAO<Movie, Long> movieDAO = MovieDAO.getInstance();

    public static void main(String[] args) throws SQLException {
        Migration.migrate();

        Movie firstMovie = new Movie("Pulp Fiction");
        Director firstDirector = new Director("Quentin", "Tarantino");
        firstMovie.setDirector(firstDirector);

        Movie secondMovie = new Movie("Titanic");
        Director secondDirector = new Director("James", "Cameron");
        secondMovie.setDirector(secondDirector);

        List<Movie> movies = List.of(firstMovie, secondMovie);

        for (Movie movie : movies)
            movieDAO.insert(movie);

        System.out.println(movieDAO.get(1L));
        System.out.println(movieDAO.get());
    }
}
