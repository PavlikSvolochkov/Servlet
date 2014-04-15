
import java.sql.SQLException;
import java.text.ParseException;
import org.apache.log4j.Logger;

public class XMLData implements Runnable {

  static Logger logger = Logger.getLogger(XMLData.class);

  private Queue1 queue;
  private QueryManager queryManager;

  public XMLData(Queue1 q, QueryManager qm) throws ParseException, SQLException {
    logger.info("Call default constructor.");
    this.queue = q;
    this.queryManager = qm;
    //this.queryManager.setStatement(conn.createStatement());
  }

  @Override
  public void run() {
    logger.info("Start run() method.");
    while (true) {
      try {
        if (queue.isEmpty()) {
          Thread.sleep(1000);
        }
        queryManager.setClient(queue.get());
        queryManager.insertClient();
        queryManager.insertCards();
        queryManager.insertAccounts();
      } catch (SQLException ex) {
        logger.debug("SQLException in XMLData.run()", ex);
      } catch (InterruptedException ex) {
        logger.debug("InterruptedException in XMLData.run()", ex);
      }
    }
  }

  public void setQueue(Queue1 q) {
    logger.info("Setting a queue...");
    this.queue = q;
  }
}
