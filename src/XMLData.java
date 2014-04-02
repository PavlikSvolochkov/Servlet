
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import org.apache.log4j.Logger;

public class XMLData implements Runnable {

  static Logger logger = Logger.getLogger(XMLData.class);

  private Queue1 queue;
  private Connection conn;
  private QueryManager queryManager;

  public XMLData(Queue1 q, Connection c) throws ParseException, SQLException {
    logger.info("Call default constructor.");
    this.queryManager = new QueryManager();
    this.queue = q;
    this.conn = c;
    this.queryManager.setStatement(conn.createStatement());
  }

  @Override
  public void run() {
    logger.info("Start run() method.");
    System.out.println(Queue1.clientCount);
    while (true) {
      try {
        queryManager.setClient(queue.get());
        queryManager.insertClient();
        queryManager.insertCards();
        queryManager.insertAccounts();
      } catch (SQLException ex) {
        logger.debug("SQLException in XMLData.run()", ex);
      } finally {
        try {
          queryManager.closeStatement();
        } catch (SQLException ex) {
          logger.error("ERROR IN CLOSING STATEMENT", ex);
        }
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

  public void setQueue(Queue1 q) {
    logger.info("Setting a queue...");
    this.queue = q;
  }
}
