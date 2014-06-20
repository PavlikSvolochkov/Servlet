
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class NewQueue {

  private static final Logger logger = Logger.getLogger(ToXML.class);

  private int clientCount;
  private List<Client> list;

  public NewQueue() {
    this.clientCount = 0;
    this.list = new ArrayList<>();
  }

  public synchronized void put(Client c) throws InterruptedException {
    list.add(c);
    clientCount++;
  }

  public synchronized List get() {
    System.out.println("Get()");
    return list;
  }

  public void getClientCount() {
    System.out.println("CLIENT COUNT >>> " + clientCount);
  }
}
