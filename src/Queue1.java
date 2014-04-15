
import java.util.LinkedList;
import java.util.Queue;

public class Queue1 {

  public static int clientCount = 0;

  Client client;
  Queue<Client> queueA;

//----------------------------------------------------------------------------------------------------------------------
  public Queue1() {
    this.queueA = new LinkedList<>();
  }
//----------------------------------------------------------------------------------------------------------------------

  public synchronized void put(Client c) {
    this.client = c;
    this.queueA.add(client);
    clientCount++;
    System.out.println("SENDED >>> " + queueA.peek().getName());
  }
//----------------------------------------------------------------------------------------------------------------------

  public synchronized Client get() {
    System.out.println("RECEIVED >>> " + queueA.peek().getName());
    return queueA.poll();
  }
//----------------------------------------------------------------------------------------------------------------------

  public boolean isEmpty() {
    return queueA.isEmpty();
  }
}
//----------------------------------------------------------------------------------------------------------------------
