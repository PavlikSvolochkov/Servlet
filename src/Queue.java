
import java.util.ArrayList;
import java.util.List;

public class Queue {

  List<Client> clientList = new ArrayList<Client>();
  boolean valueSet = false;

  synchronized void put(Client c) {
    while (valueSet) {
      try {
        wait();
      } catch (InterruptedException e) {
        System.out.println("InterruptedException перехвачено в методе put()");
      }
    }
    this.clientList.add(c);
    valueSet = true;
    System.out.println("Отправлено: " + c.toString());
    //notify();
  }

  synchronized List<Client> get() {
    while (!valueSet) {
      try {
        wait();
      } catch (InterruptedException e) {
        System.out.println("InterruptedException перехвачено в методе get()");
      }
    }
    System.out.println("Получено: " + clientList.toString());
    valueSet = false;
    //notify();
    return clientList;
  }
}
