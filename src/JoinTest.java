
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class JoinTest {

  private String fileLocation = "d:/temp/";
  private String fileName = "clients.xml";
  private final String testQuery = "SELECT NAME, SURNAME, DATEOFBIRTH, CARDS.CARD, ACCOUNTS.ACCOUNT FROM CLIENTS "
          + "INNER JOIN CARDS ON CARDS.ID_CLIENT=CLIENTS.ID "
          + "INNER JOIN ACCOUNTS ON ACCOUNTS.ID_CLIENT=CLIENTS.ID;";

  Connection conn = null;
  ResultSet resultSet = null;
  Statement statement = null;

  XMLOutputFactory outputFactory = null;
  XMLStreamWriter writer = null;

  public void build() throws XMLStreamException, SQLException, IOException, ClassNotFoundException {
    
    DBConnection connection = new DBConnection();
    connection.connect();

    outputFactory = XMLOutputFactory.newInstance();
    writer = outputFactory.createXMLStreamWriter(new FileWriter(fileLocation + fileName));

    statement = connection.getConnection().createStatement();
    resultSet = statement.executeQuery(testQuery);

    writer.writeStartElement("clients");

    while (resultSet.next()) {

      int idClient = resultSet.getInt("ID");
      String name = resultSet.getString("NAME");
      String surname = resultSet.getString("SURNAME");
      Date birthDate = resultSet.getDate("DATEOFBIRTH");
      int card = resultSet.getInt("CARD");
      int account = resultSet.getInt("ACCOUNT");

      writer.writeStartElement("client");

      writer.writeStartElement("name");
      writer.writeCharacters(resultSet.getString("NAME"));
      writer.writeEndElement();

      writer.writeStartElement("surname");
      writer.writeCharacters(resultSet.getString("SURNAME"));
      writer.writeEndElement();

      writer.writeStartElement("dateOfBirth");
      writer.writeCharacters(resultSet.getString("DATEOFBIRTH"));
      writer.writeEndElement();

      writer.writeStartElement("cards");

      String cardsQuery = "SELECT * FROM CARDS WHERE ID_CLIENT=" + idClient;
      resultSet = statement.executeQuery(cardsQuery);

      while (resultSet.next()) {
        writer.writeStartElement("card");
        writer.writeCharacters(resultSet.getString("CARD"));
        writer.writeEndElement();
      }
      writer.writeEndElement();

      String accountQuery = "SELECT * FROM ACCOUNTS WHERE ID_CLIENT=" + idClient;
      resultSet = statement.executeQuery(accountQuery);

      writer.writeStartElement("accounts");
      while (resultSet.next()) {
        writer.writeStartElement("account");
        writer.writeCharacters(resultSet.getString("ACCOUNT"));
        writer.writeEndElement();
      }
      writer.writeEndElement();
      writer.writeEndElement(); // EndElement <client>

      writer.flush();

      // print the results
      System.out.format("%s, %s, %s, %s\n", idClient, name, surname, birthDate);
    }
    writer.writeEndElement();
    writer.writeEndDocument();
    writer.close();
    System.out.println("Insert data in file complite");
  }

  public static void main(String[] args) throws XMLStreamException, SQLException, IOException, ClassNotFoundException {
    JoinTest joinTest = new JoinTest();
    joinTest.build();
  }
}
