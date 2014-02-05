
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class InsertXMLData {

  private String fileName;
  private List<Client> clientList = null;

  private File file = null;
  private Connection conn = null;
  private Statement statement = null;

  public InsertXMLData() {
    System.out.println("InsertXMLData empty default constructor.");
  }

  public void insert(String fileName) throws SQLException {

    this.fileName = fileName;
    statement = conn.createStatement();

    try {
      file = new File(this.fileName);

      for (Client client : clientList) {
        System.out.println("");
        System.out.println("First Name: " + client.getName());
        System.out.println("SurName: " + client.getSurname());
        System.out.println("Date of birth: " + client.getDateOfBirth());
        System.out.println("Cards: " + client.getCards());
        System.out.println("Accounts: " + client.getAccounts());
        
        statement.executeUpdate("INSERT INTO CLIENTS(ID_CLIENT, NAME, SURNAME, DATEOFBIRTH) " 
                + "VALUES(ID_CLIENT.NEXTVAL, '" + client.getName() + "', '" + client.getSurname() 
                + "', '" + client.getDateOfBirth() + "')");
        statement.executeUpdate("INSERT INTO CARDS(ID_CLIENT, CARD) VALUES(LAST_INSERT_ID(), '" + client.getCards() + "');");        
        statement.executeUpdate("INSERT INTO ACCOUNTS(ID_CLIENT, ACCOUNT) VALUES(LAST_INSERT_ID(), '" + client.getAccounts() + "');");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("Data is successfully inserted!");
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public void setClientList(List<Client> clientList) {
    this.clientList = clientList;
  }
}
