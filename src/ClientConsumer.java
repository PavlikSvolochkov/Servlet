
import java.util.concurrent.BlockingQueue;
import org.apache.log4j.Logger;

public class ClientConsumer implements Runnable {

  static Logger logger = Logger.getLogger(ClientConsumer.class.getName());

  protected BlockingQueue queue;

  public ClientConsumer(BlockingQueue q) {
    logger.info("ClientConsumer constructor is opened");
    this.queue = q;
    logger.info("ClientConsumer constructor is closed");
    new Thread(this).start();
  }

  @Override
  public void run() {
    logger.info("ClientConsumer run() method is opened");
    while (!queue.isEmpty()) {
      try {
        System.out.println("\nFROM CONSUMER GET() >>> " + queue.take().toString());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    logger.info("ClientConsumer run() method is closed");
  }
}
