
import java.util.LinkedList;
import java.util.Queue;

public class Queue1 {

  public static int clientCount = 0;

  private Client client;
  private Queue<Client> queueA;

  public Queue1() {
    this.queueA = new LinkedList<>();
  }

  public synchronized void put(Client c) {
    this.client = c;
    this.queueA.add(client);
    System.out.println("SENDED >>> " + c.getName());
    clientCount++;
  }

  public synchronized Client get() {
    System.out.println("RECEIVED >>> " + queueA.peek().getId());
    return queueA.poll();
  }

  public boolean isEmpty() {
    return queueA.isEmpty();
  }
}
