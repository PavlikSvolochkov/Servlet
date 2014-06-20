
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class NewQueue {

  static Logger logger = Logger.getLogger(ToXML.class);

  private int clientCount = 0;
  private List<Client> list = new ArrayList<Client>();

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
