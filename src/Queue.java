
import java.util.ArrayList;
import java.util.List;

public class Queue {

  public static int clientCount = 0;

  Client client;
  List<Client> clientList = new ArrayList<>();
  boolean valueSet = false;

  public synchronized void put(Client c) {
    while (valueSet) {
      try {
        wait();
      } catch (InterruptedException e) {
        System.out.println("InterruptedException in Queue.put()");
      }
    }
    this.client = c;
    this.clientList.add(client);
    valueSet = true;
    System.out.println("SENDED >>> " + c.getName());
    notify();
  }

  public synchronized Client get() {
    while (!valueSet) {
      try {
        wait();
      } catch (InterruptedException e) {
        System.out.println("InterruptedException in Queue.get()");
      }
    }
    System.out.println("RECEIVED >>> " + client.getName());
    valueSet = false;
    notify();
    return client;
  }

  public boolean isEmpty() {
    return clientList.isEmpty();
  }
}
