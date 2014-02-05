
import java.io.File;
import java.sql.Connection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class InsertXMLData {

  String name = null;
  String surname = null;
  String dateofbirth = null;
  String cards = null;
  String account = null;

  String fileName = "d:/temp/tests/clients111111.xml";

  File file;
  private Connection conn;

  public InsertXMLData() {
    
  }

  public InsertXMLData(String fileName) {
    this.fileName = fileName;

    try {
      file = new File(this.fileName);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

      Document doc = dBuilder.parse(file);
      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("client");

      for (int temp = 0; temp < nList.getLength(); temp++) {

        Node nNode = nList.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

          Element eElement = (Element) nNode;

          name = eElement.getElementsByTagName("name").item(0).getTextContent().trim();
          surname = eElement.getElementsByTagName("surname").item(0).getTextContent().trim();
          dateofbirth = eElement.getElementsByTagName("dateOfBirth").item(0).getTextContent().trim();
//          cards = eElement.getElementsByTagName("cards").item(0).getTextContent().trim();
//          account = eElement.getElementsByTagName("accounts").item(0).getTextContent().trim();

          System.out.println("First Name: " + name);
          System.out.println("SurName: " + surname);
          System.out.println("Date of birth: " + dateofbirth);
          System.out.println("Cards: " + cards);
          System.out.println("Accounts: " + account);

          connectionDB.getStatement().executeUpdate(
                  "insert into clients(name, surname, dateofbirth) " + "values('" + name + "', '" + surname + "', '"
                  + dateofbirth + "')");
          connectionDB.getStatement().executeUpdate(
                  "insert into cards(id, id_client, card) values(LAST_INSERT_ID(), LAST_INSERT_ID(), '" + cards + "');");
        }
      }
      connectionDB.close();
      System.out.println("Data is successfully inserted!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }
}
