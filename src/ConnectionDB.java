
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {

  private String URL;
  private String USER;
  private String PASSWORD;
  private String QUERY = "SELECT * FROM CLIENTS";

  Connection connection;
  Statement statement;
  ResultSet resultSet;

  public ConnectionDB() {
    this.URL = "jdbc:oracle:thin:@193.8.203.37:1522/PS";
    this.USER = "monservice";
    this.PASSWORD = "monservice";
  }

  public ConnectionDB(String dbUrl, String user, String password) {
    this.URL = dbUrl;
    this.USER = user;
    this.PASSWORD = password;
  }

  public void connect() throws SQLException, ClassNotFoundException {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    this.statement = connection.createStatement();
    this.resultSet = statement.executeQuery(getQuery());
    System.out.println("Well done!");
  }

  public void printQuery() throws SQLException {
    while (resultSet.next()) {
      System.out.println("ID_CLIENT: " + resultSet.getString("ID_CLIENT"));
      System.out.println("NAME: " + resultSet.getString("NAME"));
      System.out.println("SURNAME: " + resultSet.getString("SURNAME"));
      System.out.println("DATEOFBIRTH: " + resultSet.getString("DATEOFBIRTH"));
    }
  }

  public ResultSet getResultSet() throws SQLException {
    return this.resultSet;
  }

  public Statement getStatement() throws SQLException {
    return this.statement;
  }

  public void setQuery(String query) {
    this.QUERY = query;
  }

  public String getQuery() {
    return QUERY;
  }

  public void close() throws SQLException {
    this.connection.close();
  }

  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    ConnectionDB connectionDB = new ConnectionDB();
    connectionDB.connect();
    connectionDB.printQuery();
  }
}
