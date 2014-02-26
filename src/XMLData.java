
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.concurrent.BlockingQueue;
import org.apache.log4j.Logger;

public class XMLData implements Runnable {

  static Logger logger = Logger.getLogger(XMLData.class);

  private Client client;
  private BlockingQueue queue;
  private Connection conn = null;
  private Statement statement = null;

  public XMLData(BlockingQueue q, Connection c) throws ParseException, SQLException {
    logger.info("Call default constructor.");
    this.queue = q;
    this.conn = c;
    this.statement = conn.createStatement();
  }

  @Override
  public void run() {
    logger.info("Start run() method.");
    while (!queue.isEmpty()) {
      try {
        client = (Client) queue.take();
        statement.executeUpdate("CALL MY_TEST_PACKAGE.INSERT_CLIENT('" + client.getName() + "', '"
                + client.getSurname() + "', '" + client.getDateOfBirth() + "')");

        for (int i = 0; i < client.getCards().size(); i++) {
          statement.executeUpdate("CALL MY_TEST_PACKAGE.INSERT_CARDS('" + client.getCards().get(i) + "')");
        }

        for (int i = 0; i < client.getAccounts().size(); i++) {
          statement.executeUpdate("CALL MY_TEST_PACKAGE.INSERT_ACCOUNTS('" + client.getAccounts().get(i) + "')");
        }
      } catch (InterruptedException | SQLException ex) {
        logger.error("ERROR INSERING DATA");
        ex.printStackTrace();
      }
    }
    try {
      logger.info("Closing statement and connection...");
      statement.close();
      conn.close();
      logger.info("Statement and connection is closed");
    } catch (SQLException ex) {
      logger.error("ERROR CLOSING STATEMENT");
      ex.printStackTrace();
    }
    logger.info("Stop run() method.");
  }

  public void setConn(Connection c) {
    logger.info("Set connection");
    this.conn = c;
    if (this.conn == null) {
      logger.error("Connection is null");
      System.exit(0);
    }
  }

  public void setQueue(BlockingQueue q) {
    this.queue = q;
  }
}
