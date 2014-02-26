
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DBConnection {

  static Logger logger = Logger.getLogger(DBConnection.class.getName());

  private String url;
  private String user;
  private String pass;
  private String driver;

  Connection conn = null;

  public DBConnection() {
    logger.info("Init default connection");
    this.url = "jdbc:oracle:thin:@193.8.203.37:1522/PS";
    this.user = "monservice";
    this.pass = "monservice";
    this.driver = "oracle.jdbc.driver.OracleDriver";
    logger.info("Default connection init");
  }

  public DBConnection(String dbUrl, String user, String password) {
    setUrl(dbUrl);
    setUser(user);
    setPassword(password);
  }

  public void connect() throws SQLException, ClassNotFoundException {
    logger.info("Calling connect() method to connecting database");
    Class.forName(driver);
    this.conn = DriverManager.getConnection(url, user, pass);
    logger.info("Connection to: " + url + " is opened");
    System.out.println("//----------------------------------------------------------------------------------------------------------------------");
  }

  public void close() throws SQLException {
    logger.info("Closing connection");
    this.conn.close();
    logger.info("Connection is closed");
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

  public void setDriver(String driver) {
    this.driver = driver;
  }
}
