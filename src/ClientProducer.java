
import java.util.concurrent.BlockingQueue;

public class ClientProducer implements Runnable {

  String tempStr = null;

  Client client;

  protected BlockingQueue queue = null;

  public ClientProducer(BlockingQueue q) {
    this.queue = q;
    System.out.println("Enter string:");
  }

  @Override
  public void run() {
    while (true) {
      try {
        System.out.println("Producer put(): " + tempStr);
        queue.put(tempStr);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
