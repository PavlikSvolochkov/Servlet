
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

  private String url;
  private String user;
  private String pass;

  static Connection conn;

  public DBConnection() {
    this.url = "jdbc:oracle:thin:@localhost:1521/orcl";
    this.user = "sys as sysdba";
    this.pass = "111";
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
    return conn;
  }
}
