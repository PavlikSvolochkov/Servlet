
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class TestDataXML {

  private List<Client> clientList = null;
  private Client client = null;

  private String clientQuery = "SELECT * FROM CLIENTS";
  private String cardsQuery = "SELECT * FROM CARDS";
  private String accountQuery = "SELECT * FROM ACCOUNTS";
  private String fileLocation = null;

  Connection conn = null;

  ResultSet clientRS = null;
  ResultSet cardsRS = null;
  ResultSet accountsRS = null;

  Statement stmtClient = null;
  Statement stmtCards = null;
  Statement stmtAccounts = null;

  XMLOutputFactory outputFactory = null;
  XMLStreamWriter writer = null;

  public TestDataXML() {

    outputFactory = XMLOutputFactory.newInstance();

    try {
      
      writer = outputFactory.createXMLStreamWriter(new FileWriter("d:\\temp\\data\\output2.xml"));

      this.stmtClient = conn.createStatement();
      this.stmtCards = conn.createStatement();
      this.stmtAccounts = conn.createStatement();

      this.clientRS = stmtClient.executeQuery(getClientQuery());
      this.cardsRS = stmtCards.executeQuery(getCardsQuery());
      this.accountsRS = stmtAccounts.executeQuery(getAccountQuery());

    } catch (IOException | XMLStreamException | SQLException e) {
      e.printStackTrace();
    }
  }

  public void setFileLocation(String file_location) {
    this.fileLocation = file_location;
  }

  public void toXML() throws XMLStreamException, IOException, SQLException, ClassNotFoundException {

    writer.writeStartDocument("UTF-8", "1.0");
    writer.writeStartElement("clients");

    while (clientRS.next()) {

      int id_client = clientRS.getInt("id_client");
      String name = clientRS.getString("name");
      String surname = clientRS.getString("surname");
      Date birthDate = clientRS.getDate("dateOfBirth");

      writer.writeStartElement("client");

      writer.writeStartElement("name");
      writer.writeCharacters(clientRS.getString("name"));
      writer.writeEndElement();

      writer.writeStartElement("surname");
      writer.writeCharacters(clientRS.getString("surname"));
      writer.writeEndElement();

      writer.writeStartElement("dateOfBirth");
      writer.writeCharacters(clientRS.getString("dateOfBirth"));
      writer.writeEndElement();

      writer.writeStartElement("cards");

      cardsQuery = "SELECT * FROM CARDS WHERE ID_CLIENT=" + id_client;
      cardsRS = stmtCards.executeQuery(cardsQuery);

      while (cardsRS.next()) {
        writer.writeStartElement("card");
        writer.writeCharacters(cardsRS.getString("CARD"));
        writer.writeEndElement();
      }
      writer.writeEndElement();

      accountQuery = "SELECT * FROM ACCOUNTS WHERE ID_CLIENT=" + id_client;
      accountsRS = stmtAccounts.executeQuery(accountQuery);

      writer.writeStartElement("accounts");
      while (accountsRS.next()) {
        writer.writeStartElement("account");
        writer.writeCharacters(accountsRS.getString("account"));
        writer.writeEndElement();
      }
      writer.writeEndElement();

      writer.writeEndElement(); // EndElement <client>

      writer.flush();

      // print the results
      System.out.format("%s, %s, %s, %s\n", id_client, name, surname, birthDate);
    }
    writer.writeEndElement();
    writer.writeEndDocument();
    writer.close();

    //conn.close();
    System.out.println("Insert data in file complite");
  }

  public String getClientQuery() {
    return clientQuery;
  }

  public String getCardsQuery() {
    return cardsQuery;
  }

  public String getAccountQuery() {
    return accountQuery;
  }

  public ResultSet getClientRS() {
    return clientRS;
  }

  public ResultSet getCardsRS() {
    return cardsRS;
  }

  public ResultSet getAccountsRS() {
    return accountsRS;
  }

  public void setClienQuery(String clientQuery) {
    this.clientQuery = clientQuery;
  }

  public void setCardsQuery(String cardsQuery) {
    this.cardsQuery = cardsQuery;
  }

  public void setAccountQuery(String accountQuery) {
    this.accountQuery = accountQuery;
  }

  public void setConnection(Connection conn) {
    this.conn = conn;
  }
}
