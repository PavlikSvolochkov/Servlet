
import java.util.List;

public class Main {

  public static void main(String[] args) throws Exception {

    DBConnection conn = new DBConnection();
    conn.connect();
    
    DataXML dataXML = new DataXML();
    dataXML.setConnection(conn.getConnection());
    dataXML.build();
    dataXML.toXML();
    
    ClientSaxParser saxParser = new ClientSaxParser("d:\\dev\\Project\\ClientsServlet\\src\\clients.xml");
    List<Client> clientList = saxParser.getClientList();
            
    for (Client client : clientList)
      System.out.println(client.toString());
    
//    InsertXMLData insertXMLData = new InsertXMLData();
//    insertXMLData.setConn(conn.getConnection());
//    insertXMLData.setClientList(clientList);
//    insertXMLData.insert("d:\\dev\\Project\\ClientsServlet\\src\\clients.xml");
  }
}
