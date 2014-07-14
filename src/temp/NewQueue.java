package temp;

import java.util.LinkedList;
import java.util.Queue;
import org.apache.log4j.Logger;

public class NewQueue {

  private static final Logger logger = Logger.getLogger(ToXML.class);

  private int clientCount;
  public static Queue<NewClient> list;

  public NewQueue() {
    this.clientCount = 0;
    this.list = new LinkedList<>();
  }

  public synchronized void put(NewClient c) throws InterruptedException {
    list.add(c);
    logger.info("PUT: " + c.getId());
    clientCount++;
  }

  public synchronized Queue get() {
    System.out.println("Get()");
    return list;
  }

  public void getClientCount() {
    System.out.println("CLIENT COUNT >>> " + clientCount);
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }
}
