
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;

public class InsertXMLData {

  private String fileName;
  private List<Client> clientList = null;

  private File file = null;
  private Connection conn = null;
  private Statement statement = null;

  public InsertXMLData() {
    System.out.println("InsertXMLData. Default constructor is empty.");
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public void setClientList(List<Client> clientList) {
    this.clientList = clientList;
  }

  public void insert() throws SQLException, ParseException {

    statement = conn.createStatement();
    for (Client client : clientList) {

      System.out.println("");
      System.out.println("First Name: " + client.getName());
      System.out.println("SurName: " + client.getSurname());
      System.out.println("Date of birth: " + client.getDateOfBirth());
      System.out.println("Cards: " + client.getCards());
      System.out.println("Accounts: " + client.getAccounts());

      System.out.println("INSERT INTO CLIENTS(NAME, SURNAME, DATEOFBIRTH) " + "VALUES('" + client.getName() + "', '" + client.getSurname()
              + "', '" + client.getDateOfBirth() + "')");

      statement.executeUpdate("INSERT INTO CLIENTS(NAME, SURNAME, DATEOFBIRTH) " + "VALUES('" + client.getName() + "', '" + client.getSurname()
              + "', '" + client.getDateOfBirth() + "')");

//      for (int i = 0; i < client.getCards().size(); i++) {
//        statement.executeUpdate("INSERT INTO CARDS(ID_CLIENT, CARD) VALUES(CLIENTS.ID_CLIENT, " + client.getCards().get(i) + ")");
//      }
//
//      for (int i = 0; i < client.getAccounts().size(); i++) {
//        statement.executeUpdate("INSERT INTO ACCOUNTS(ID_CLIENT, ACCOUNT) VALUES(CLIENTS.ID_CLIENT, " + client.getAccounts().get(i) + ")");
//      }
      System.out.println("Data is successfully inserted!");
    }
  }

  public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {

    DBConnection conn = new DBConnection();
    conn.connect();

    ClientSaxParser saxParser = new ClientSaxParser("d:\\temp\\data\\clients.xml");
    saxParser.printData();

    InsertXMLData insert = new InsertXMLData();
    insert.setConn(conn.getConnection());
    insert.setClientList(saxParser.getSyncClientList());
    insert.insert();
    conn.close();
  }
}
