
public class Main {

  public static void main(String[] args) throws Exception {

    DBConnection conn = new DBConnection();
    conn.connect();

    Queue queue = new Queue(conn.getConnection());
    ClientSaxParser saxParser = new ClientSaxParser("d:\\temp\\data\\clients.xml");
    saxParser.setQueue(queue);
    saxParser.parseDocument();

    saxParser.getClients();
  }
}
