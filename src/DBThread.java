
import org.apache.log4j.Logger;

public class DBThread extends Thread {

  static Logger logger = Logger.getLogger(DBThread.class);

  Client client;
  ClientQueue queue;

  @Override
  public void run() {
    while (true) {
      if (queue.isEmpty()) {
        try {
          sleep(1000);
        } catch (InterruptedException ex) {
          logger.debug("Interrupted", ex);
        }
      } else {
        client = queue.getElement();
      }
    }
  }
}
