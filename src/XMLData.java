
import java.io.File;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;

public class XMLData {

  static Logger logger = Logger.getLogger(XMLData.class);

  private String fileName;
  private List<Client> clientList = null;

  private File file = null;
  private Connection conn = null;
  private Statement statement = null;

  public XMLData() {
    logger.info("Call default constructor. Is empty");
  }

  public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {

    DBConnection conn = new DBConnection();
    conn.connect();

    ClientSaxParser saxParser = new ClientSaxParser("d:\\temp\\data\\clients.xml");
    //saxParser.printData();

    XMLData insert = new XMLData();
    insert.setConn(conn.getConnection());
    insert.setClientList(saxParser.getClientList());
    insert.insert();
    conn.close();
  }

  public void insert() throws SQLException, ParseException {

    logger.info("Creating statement");
    statement = conn.createStatement();
    if (statement == null) {
      logger.error("Statement is null");
      System.exit(0);
    }

    for (Client client : clientList) {
      System.out.println("//----------------------------------------------------------------------------------------------------------------------");

      System.out.println("First Name: " + client.getName());
      System.out.println("SurName: " + client.getSurname());
      System.out.println("Date of birth: " + client.getDateOfBirth());
      System.out.println("Cards: " + client.getCards());
      System.out.println("Accounts: " + client.getAccounts());

      SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
      //Array array = conn.createArrayOf("NVARCHAR2", client.getCards().toArray());

      System.out.println("ARRAY OF CARDS >>> " + client.getCards().toString());
      System.out.println("INSERT INTO CLIENTS(NAME, SURNAME, DATEOFBIRTH) " + "VALUES('" + client.getName()
              + "', '" + client.getSurname() + "', '" + sdf.format(client.getDateOfBirth()) + "')");

      logger.info("Inserting data to xml");

      statement.executeUpdate("INSERT INTO CLIENTS(NAME, SURNAME, DATEOFBIRTH) " + "VALUES('" + client.getName()
              + "', '" + client.getSurname() + "', '" + sdf.format(client.getDateOfBirth()) + "')");

//----------------------------------------------------------------------------------------------------------------------
      System.out.println("CALL NEW_CLIENT_CARDS_ACCOUNTS('" + client.getName() + "', '" + client.getSurname() + "', '"
              + sdf.format(client.getDateOfBirth()) + "', " + client.getCards().toArray() + ", " + client.getAccounts().toArray() + ");");
      
//      statement.executeUpdate("CALL FULL_CLIENT('" + client.getName() + "', '" + client.getSurname() + "', '"
//              + sdf.format(client.getDateOfBirth()) + "', " + client.getCards().toArray() + ", " + client.getAccounts().toArray() + ");");

//      for (int i = 0; i < client.getCards().size(); i++) {
//        System.out.println("INSERT INTO CARDS(CARD) VALUES('" + client.getCards().get(i) + "')");
//        statement.executeUpdate("INSERT INTO CARDS(CARD) VALUES('" + client.getCards().toArray() + "')");
//      }
//      for (int i = 0; i < client.getAccounts().size(); i++) {
//        statement.executeUpdate("INSERT INTO ACCOUNTS(ID_CLIENT, ACCOUNT) VALUES(CLIENTS.ID, " + client.getAccounts().get(i) + ")");
//      }
      logger.info("Data is successfully inserted!");
    }
  }

  public void setConn(Connection conn) {
    logger.info("Set connection");
    this.conn = conn;
    if (this.conn == null) {
      logger.error("Connection is null");
      System.exit(0);
    }
  }

  public void setClientList(List<Client> clientList) {
    this.clientList = clientList;
  }
}
