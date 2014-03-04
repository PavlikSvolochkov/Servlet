
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import org.apache.log4j.Logger;

public class XMLData implements Runnable {

  static Logger logger = Logger.getLogger(XMLData.class);

  private Client client;
  private Queue queue;
  private Connection conn = null;
  private Statement statement = null;

  public XMLData(Queue q, Connection c) throws ParseException, SQLException {
    logger.info("Call default constructor.");
    this.queue = q;
    this.conn = c;
    this.statement = conn.createStatement();
  }

  @Override
  public void run() {
    logger.info("Start run() method.");
    System.out.println(Queue.clientCount);
    while (true) {
      try {
        client = queue.get();
        statement.executeUpdate("CALL MY_TEST_PACKAGE.INSERT_CLIENT('" + client.getName() + "', '"
                + client.getSurname() + "', '" + client.getDateOfBirth() + "')");
        for (int i = 0; i < client.getCards().size(); i++) {
          statement.executeUpdate("CALL MY_TEST_PACKAGE.INSERT_CARDS('" + client.getCards().get(i) + "')");
        }
        for (int i = 0; i < client.getAccounts().size(); i++) {
          statement.executeUpdate("CALL MY_TEST_PACKAGE.INSERT_ACCOUNTS('" + client.getAccounts().get(i) + "')");
        }
        //System.out.println("CUSTOMER ADD IN DB");
      } catch (SQLException ex) {
        logger.debug("SQLException in XMLData.run()", ex);
      }
    }
  }

  public void setConn(Connection c) {
    logger.info("Set connection");
    this.conn = c;
    if (this.conn == null) {
      logger.error("Connection is null");
      System.exit(0);
    }
  }

  public void setQueue(Queue q) {
    logger.info("Setting a queue...");
    this.queue = q;
  }
}
