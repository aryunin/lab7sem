import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    private static Long idSeq = 0L;

    public static void main(String[] args) throws SQLException {
        try (var con = ConnectionFactory.getConnection()){

            Migration.migrate();

            String selectMovieSingle = "SELECT name FROM movie WHERE id=?";
            String selectMovieMulti = "SELECT name FROM movie";

            var preparedStatement = con.prepareStatement(selectMovieSingle);
            preparedStatement.setLong(1, 1);
            var singleRes = preparedStatement.executeQuery();
            if (!singleRes.next()) return;

            System.out.println();
            System.out.println("Single movie (id 1):\n" + mapRow(singleRes));
            System.out.println();

            var stmt = con.createStatement();
            var multiRes = stmt.executeQuery(selectMovieMulti);
            var listRes = new ArrayList<String>();
            while (multiRes.next()) {
                listRes.add(multiRes.getString("name"));
            }

            System.out.println("All movies:");
            listRes.forEach(System.out::println);
        }
    }

    private static String mapRow(ResultSet rs) throws SQLException {
        return rs.getString("name");
    }
}
