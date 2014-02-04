
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataXML extends Thread {

  String query = "SELECT * FROM clients";
  private String LOCATION = null;

  DBConnection connectionDB;

  DocumentBuilderFactory factory;
  DocumentBuilder builder;
  Document doc;

  Element rootElement;
  Element clientTitle;

  public DataXML() throws ClassNotFoundException, SQLException, ParserConfigurationException {
    connectionDB = new DBConnection();
    connectionDB.setQuery(query);
    connectionDB.connect();
    factory = DocumentBuilderFactory.newInstance();
    builder = factory.newDocumentBuilder();
    doc = builder.newDocument();

    rootElement = doc.createElement("clients");
    clientTitle = doc.createElement("client");
  }

  public void getFileLocation(String location) {
    LOCATION = location;
  }

  @Override
  public void run() {
    try {

      while (connectionDB.getResultSet().next()) {
        rootElement.appendChild(clientTitle);

        int id = connectionDB.getResultSet().getInt("id");
        String name = connectionDB.getResultSet().getString("name");
        String surName = connectionDB.getResultSet().getString("surname");
        String birthDate = connectionDB.getResultSet().getString("dateofbirth");

        Element nameTitle = doc.createElement("name");
        nameTitle.appendChild(doc.createTextNode(name));
        clientTitle.appendChild(nameTitle);

        Element surnameTitle = doc.createElement("surname");
        surnameTitle.appendChild(doc.createTextNode(surName));
        clientTitle.appendChild(surnameTitle);

        Element birthdateTitle = doc.createElement("dateOfBirth");
        birthdateTitle.appendChild(doc.createTextNode(birthDate));
        clientTitle.appendChild(birthdateTitle);

        rootElement.appendChild(clientTitle);
        clientTitle = doc.createElement("client");

        // print the results
        System.out.format("%s, %s, %s, %s\n", id, name, surName, birthDate);
      }
      doc.appendChild(rootElement);
      File inserFile = new File(LOCATION);
      inserFile.createNewFile();
      Transformer t = TransformerFactory.newInstance().newTransformer();
      t.setOutputProperty(OutputKeys.INDENT, "yes");
      t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(LOCATION)));
      connectionDB.close();
      System.out.println("Insert data in file complite");
    } catch (SQLException | TransformerFactoryConfigurationError | TransformerException | IOException e) {
      e.toString();
    }
  }
}
