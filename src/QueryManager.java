
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class QueryManager {

  static Logger logger = Logger.getLogger(QueryManager.class.getName());

  private Client client;
  private Statement statement;

  public QueryManager() {
    System.out.println("Default constructor.");
  }

  public QueryManager(Client c) {
    logger.info("Initialize client in constructor");
    this.client = c;
  }

  public void insertClient() throws SQLException {
    logger.info("Inserting client in DB...");
    statement.executeUpdate("CALL MY_TEST_PACKAGE.INSERT_CLIENT('" + client.getName() + "', '"
            + client.getSurname() + "', '" + client.getDateOfBirth() + "')");
    logger.info("Insertion client complited");
  }

  public void insertCards() throws SQLException {
    logger.info("Inserting client's cards in DB...");
    for (int i = 0; i < client.getCards().size(); i++) {
      statement.executeUpdate("CALL MY_TEST_PACKAGE.INSERT_CARDS('" + client.getCards().get(i) + "')");
    }
    logger.info("Insertion cards complited");
  }

  public void insertAccounts() throws SQLException {
    logger.info("Inserting client's accounts in DB...");
    for (int i = 0; i < client.getAccounts().size(); i++) {
      statement.executeUpdate("CALL MY_TEST_PACKAGE.INSERT_ACCOUNTS('" + client.getAccounts().get(i) + "')");
    }
    logger.info("Insertion accounts complited");
  }

  public void updateClient(long id, String name, String surname, String dateofbirth) throws SQLException {
    logger.info("Updating client's information id DB...");
    statement.executeUpdate("CALL MY_TEST_PACKAGE.UPDATE_CLIENT('" + id + "', '" + client.getName() + "', '"
            + client.getSurname() + "', '" + client.getDateOfBirth() + "')");
    logger.info("Updating complited.");
  }

  public void deletClient(long id) throws SQLException {
    logger.info("Deleting client with id=" + id + "...");
    statement.executeUpdate("CALL MY_TEST_PACKAGE.DELETE_CLIENT('" + id + "')");
    logger.info("Deleting complited.");
  }

  public void setClient(Client c) {
    this.client = c;
  }

  public void setStatement(Statement s) {
    this.statement = s;
  }
}
