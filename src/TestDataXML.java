
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

  ConnectionDB connectDB;

  XMLOutputFactory outputFactory;

  XMLStreamWriter writer;

  public TestDataXML() throws ClassNotFoundException, SQLException, ParserConfigurationException, XMLStreamException, IOException {
    connectDB = new ConnectionDB();
    connectDB.setClienQuery(query);
    connectDB.connect();
    outputFactory = XMLOutputFactory.newInstance();
    writer = outputFactory.createXMLStreamWriter(new FileWriter("d:\\temp\\data\\output2.xml"));
  }
    
  public void setFileLocation(String file_location) {
    this.file_location = file_location;
  }

  public void toXML() throws XMLStreamException, IOException, SQLException, ClassNotFoundException {

    writer.writeStartDocument("UTF-8", "1.0");
    writer.writeStartElement("clients");

    while (connectDB.getClientRS().next()) {

      int id_client = connectDB.getClientRS().getInt("id_client");
      String name = connectDB.getClientRS().getString("name");
      String surName = connectDB.getClientRS().getString("surname");
      Date birthDate = connectDB.getClientRS().getDate("dateOfBirth");

      writer.writeStartElement("client");

      writer.writeStartElement("name");
      writer.writeCharacters(connectDB.getClientRS().getString("name"));
      writer.writeEndElement();

      writer.writeStartElement("surname");
      writer.writeCharacters(connectDB.getClientRS().getString("surname"));
      writer.writeEndElement();

      writer.writeStartElement("dateOfBirth");
      writer.writeCharacters(connectDB.getClientRS().getString("dateOfBirth"));
      writer.writeEndElement();

      writer.writeStartElement("cards");

      connectDB.setCardsQuery("SELECT * FROM CARDS WHERE ID_CLIENT=" + id_client);
      connectDB.cardsRS = connectDB.stmtCards.executeQuery(connectDB.getCardsQuery());

      while (connectDB.getCardsRS().next()) {
        writer.writeStartElement("card");
        writer.writeCharacters(connectDB.getCardsRS().getString("CARD"));
        writer.writeEndElement();
      }
      writer.writeEndElement();
//      

      connectDB.setAccountQuery("SELECT * FROM ACCOUNTS WHERE ID_CLIENT=" + id_client);
      connectDB.accountsRS = connectDB.stmtAccounts.executeQuery(connectDB.getAccountsQuery());

      writer.writeStartElement("accounts");
      while (connectDB.getAccountsRS().next()) {
        writer.writeStartElement("account");
        writer.writeCharacters(connectDB.getAccountsRS().getString("account"));
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

    connectDB.close();

    System.out.println("Insert data in file complite");
  }

  public static void main(String[] args) throws ClassNotFoundException, SQLException, ParserConfigurationException, XMLStreamException, IOException {
    TestDataXML dataXML = new TestDataXML();
    dataXML.toXML();
  }
}
