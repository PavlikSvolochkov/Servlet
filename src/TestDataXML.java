
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class TestDataXML {

  private List<Client> clientList = null;
  private Client client = null;

  String query = "SELECT * FROM clients";
  private String file_location = null;

  ConnectionDB connectionDB;

  XMLOutputFactory outputFactory;

  XMLStreamWriter writer;

  public TestDataXML() throws ClassNotFoundException, SQLException, ParserConfigurationException, XMLStreamException, IOException {
    connectionDB = new ConnectionDB();
    connectionDB.setClienQuery(query);
    connectionDB.connect();
    outputFactory = XMLOutputFactory.newInstance();
    writer = outputFactory.createXMLStreamWriter(new FileWriter("d:\\temp\\data\\output2.xml"));
  }
    
  public void setFileLocation(String file_location) {
    this.file_location = file_location;
  }

  public void toXML() throws XMLStreamException, IOException, SQLException, ClassNotFoundException {

    writer.writeStartDocument("UTF-8", "1.0");
    writer.writeStartElement("clients");

    while (connectionDB.getClientResultSet().next()) {

      int id_client = connectionDB.getClientResultSet().getInt("id_client");
      String name = connectionDB.getClientResultSet().getString("name");
      String surName = connectionDB.getClientResultSet().getString("surname");
      Date birthDate = connectionDB.getClientResultSet().getDate("dateOfBirth");

      writer.writeStartElement("client");

      writer.writeStartElement("name");
      writer.writeCharacters(connectionDB.getClientResultSet().getString("name"));
      writer.writeEndElement();

      writer.writeStartElement("surname");
      writer.writeCharacters(connectionDB.getClientResultSet().getString("surname"));
      writer.writeEndElement();

      writer.writeStartElement("dateOfBirth");
      writer.writeCharacters(connectionDB.getClientResultSet().getString("dateOfBirth"));
      writer.writeEndElement();

      writer.writeStartElement("cards");

      connectionDB.setCardsQuery("SELECT * FROM CARDS WHERE ID_CLIENT=" + id_client);
      connectionDB.cardsResultSet = connectionDB.statementCards.executeQuery(connectionDB.getCardsQuery());

      while (connectionDB.getCardsResultSet().next()) {
        writer.writeStartElement("card");
        writer.writeCharacters(connectionDB.getCardsResultSet().getString("CARD"));
        writer.writeEndElement();
      }
      writer.writeEndElement();
//      

      connectionDB.setAccountQuery("SELECT * FROM ACCOUNTS WHERE ID_CLIENT=" + id_client);
      connectionDB.accountsResultSet = connectionDB.statementAccounts.executeQuery(connectionDB.getAccountsQuery());

      writer.writeStartElement("accounts");
      while (connectionDB.getAccountsResultSet().next()) {
        writer.writeStartElement("account");
        writer.writeCharacters(connectionDB.getAccountsResultSet().getString("account"));
        writer.writeEndElement();
      }
      writer.writeEndElement();

      writer.writeEndElement(); // EndElement <client>

      writer.flush();

      // print the results
      System.out.format("%s, %s, %s, %s\n", id_client, name, surName, birthDate);
    }
    writer.writeEndElement();
    writer.writeEndDocument();
    writer.close();

    connectionDB.close();

    System.out.println("Insert data in file complite");
  }

  public static void main(String[] args) throws ClassNotFoundException, SQLException, ParserConfigurationException, XMLStreamException, IOException {
    TestDataXML dataXML = new TestDataXML();
    dataXML.toXML();
  }
}
