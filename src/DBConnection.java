
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

  private String url;
  private String user;
  private String pass;

  Connection conn = null;

  public DBConnection() {
    this.url = "jdbc:oracle:thin:@193.8.203.37:1522/PS";
    this.user = "monservice";
    this.pass = "monservice";
  }

  public DBConnection(String dbUrl, String user, String password) {
    this.url = dbUrl;
    this.user = user;
    this.pass = password;
  }

  public void connect() throws SQLException, ClassNotFoundException {
    System.out.println("Connecting to database...");
    Class.forName("oracle.jdbc.driver.OracleDriver");
    this.conn = DriverManager.getConnection(url, user, pass);
    System.out.println("Connection Well Done!");
  }

  public void close() throws SQLException {
    this.conn.close();
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setPassword(String password) {
    this.pass = password;
  }

  public Connection getConnection() {
    return this.conn;
  }
}
