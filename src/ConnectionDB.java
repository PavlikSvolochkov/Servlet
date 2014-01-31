
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {

  private String URL;
  private String USER;
  private String PASSWORD;
  private String CLIENT_QUERY = "SELECT * FROM CLIENTS";
  private String CARDS_QUERY = "SELECT * FROM CARDS";
  private String ACCOUNTS_QUERY = "SELECT * FROM ACCOUNTS";

  Connection connection;
  Statement statement;
  ResultSet clientResultSet;
  ResultSet cardsResultSet;
  ResultSet accountsResultSet;
  Statement statementClient;
  Statement statementCards;
  Statement statementAccounts;

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
    
    this.statementClient = connection.createStatement();
    this.statementCards = connection.createStatement();
    this.statementAccounts = connection.createStatement();
    
    this.clientResultSet = statementClient.executeQuery(getClientQuery());
    this.cardsResultSet = statementCards.executeQuery(getCardsQuery());
    this.accountsResultSet = statementAccounts.executeQuery(getAccountsQuery());
    System.out.println("Connection Well Done!");
  }

  public Statement getStatement() throws SQLException {
    return this.statement;
  }

  public void close() throws SQLException {
    this.connection.close();
  }

  public void setUrl(String url) {
    this.URL = url;
  }

  public void setUser(String user) {
    this.USER = user;
  }

  public void setPassword(String password) {
    this.PASSWORD = password;
  }

  public String getClientQuery() {
    return CLIENT_QUERY;
  }

  public String getCardsQuery() {
    return CARDS_QUERY;
  }

  public String getAccountsQuery() {
    return ACCOUNTS_QUERY;
  }

  public ResultSet getClientResultSet() {
    return clientResultSet;
  }

  public ResultSet getCardsResultSet() {
    return cardsResultSet;
  }

  public ResultSet getAccountsResultSet() {
    return accountsResultSet;
  }

  public void setClienQuery(String CLIENT_QUERY) {
    this.CLIENT_QUERY = CLIENT_QUERY;
  }

  public void setCardsQuery(String CARDS_QUERY) {
    this.CARDS_QUERY = CARDS_QUERY;
  }

  public void setAccountQuery(String ACCOUNTS_QUERY) {
    this.ACCOUNTS_QUERY = ACCOUNTS_QUERY;
  }
}
