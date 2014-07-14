package temp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DBConnect {

  private static final Logger logger = Logger.getLogger(DBConnect.class);

  private String url;
  private String user;
  private String pass;
  private String driver;

  private Connection conn;

  public DBConnect() {
    logger.info("Init default connection");
    this.url = "jdbc:oracle:thin:@193.8.203.37:1522/PS";
    this.user = "monservice";
    this.pass = "monservice";
    this.driver = "oracle.jdbc.driver.OracleDriver";
    logger.info("Default connection init");
  }

  public DBConnect(String dbUrl, String user, String password) {
    this.url = dbUrl;
    this.user = user;
    this.pass = password;
  }

  public void connect() throws SQLException, ClassNotFoundException {
    logger.info("Calling connect() method for connection to a database");
    Class.forName(driver);
    this.conn = DriverManager.getConnection(url, user, pass);
    logger.info("Connection to: " + url + " is opened");
    System.out.println("//----------------------------------------------------------------------------------------------------------------------");
  }

  public Connection getConnection() {
    return this.conn;
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

  public void setDriver(String driver) {
    this.driver = driver;
  }
}
