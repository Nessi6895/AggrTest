import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.*;

public class DBManager {
    private final Connection conn;
    public DBManager(){
        try {
            String url = System.getProperty("cloudsql");
            System.out.println(url);
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Cannot connect to DB");
            throw new RuntimeException("Cannot connect to DB");
        }
    }

    public String getAllPosts(){
        try {
            StringBuilder sb = new StringBuilder();
            ResultSet rs = conn.prepareStatement("SELECT * FROM posts").executeQuery();
            while(rs.next()){
                sb.append("<div>").append(rs.getString("id")).append(" ").append(rs.getString("text")).append("</div>");
            }
            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot get result from database");
        }
    }
}
