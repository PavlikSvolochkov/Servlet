
import org.apache.log4j.Logger;

public class ClientQueue {

  static Logger logger = Logger.getLogger(ClientQueue.class);

  Client client = null;

  public synchronized Client getElement() {
    return client;
  }

  public synchronized void putElement(Client c) {
    this.client = c;
  }

  public boolean isEmpty() {
    return client.getName() == null;
  }
}
