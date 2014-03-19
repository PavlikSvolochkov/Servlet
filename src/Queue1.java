
import java.util.LinkedList;
import java.util.Queue;

public class Queue1 {

  public static int clientCount = 0;

  Client client;
  Queue<Client> queueA;
  
//----------------------------------------------------------------------------------------------------------------------

  public Queue1() {
    this.queueA = new LinkedList<Client>();
  }
//----------------------------------------------------------------------------------------------------------------------

  public synchronized void put(Client c) {
    this.queueA.add(c);
    clientCount++;
    System.out.println("SENDED >>> " + queueA.peek().getName());
  }
//----------------------------------------------------------------------------------------------------------------------

  public synchronized Client get() {
    System.out.println("RECEIVED >>> ");
    return queueA.poll();
  }
//----------------------------------------------------------------------------------------------------------------------

  public boolean isEmpty() {
    return queueA.isEmpty();
  }
}
//----------------------------------------------------------------------------------------------------------------------
