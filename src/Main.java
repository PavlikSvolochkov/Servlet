
import java.util.List;

public class Main {

  public static void main(String[] args) throws Exception {

    DBConnection conn = new DBConnection();
    conn.connect();

    ClientSaxParser saxParser = new ClientSaxParser("d:\\dev\\Project\\ClientsServlet\\src\\clients.xml");
    List<Client> clientList = saxParser.getSyncClientList();

  }
}
