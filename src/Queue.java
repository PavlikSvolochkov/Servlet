
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Queue {

  List<Client> clientList = new ArrayList<Client>();
  boolean valueSet = false;

  Client tempClient;
  Connection connection;
  XMLData data;

  public Queue(Connection conn) throws SQLException, ClassNotFoundException {
    this.connection = conn;
    this.data = new XMLData();
    this.data.setConn(conn);
  }

  synchronized void put(Client c) {
    while (valueSet) {
      try {
        wait();
      } catch (InterruptedException e) {
        System.out.println("InterruptedException перехвачено в методе put()");
      }
    }
    this.tempClient = c;
    this.clientList.add(c);
    valueSet = true;
    System.out.println("\nОТПРАВЛЕНО >>>>" + c.toString());
    notify();
  }

  synchronized List<Client> get() throws SQLException, ClassNotFoundException, ParseException {
    while (!valueSet) {
      try {
        wait();
      } catch (InterruptedException e) {
        System.out.println("InterruptedException перехвачено в методе get()");
      }
    }
    System.out.println("\nПОЛУЧЕНО >>>" + tempClient.toString());
    valueSet = false;
    data.insert(tempClient);
    notify();
    return clientList;
  }
}
