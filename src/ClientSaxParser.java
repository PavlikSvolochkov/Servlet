
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ClientSaxParser extends DefaultHandler implements Runnable {

  static Logger logger = Logger.getLogger(ClientSaxParser.class);

  private String tmpValue;
  private String fileName;

  private List<Client> clientList = null;
  private Client client = null;

  private Queue queue;
  SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YY");

  public List<Client> getClientList() {
    return clientList;
  }

  public ClientSaxParser(String xmlFile) {
    this.fileName = xmlFile;
    clientList = new ArrayList<Client>();
  }

  public void parseDocument() {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      logger.info("Creating parser for file: " + fileName);
      SAXParser parser = factory.newSAXParser();
      parser.parse(fileName, this);
      logger.info("Parsing file complited");
    } catch (ParserConfigurationException | SAXException | IOException e) {
      logger.error("ERROR PARSE DOCUMENT", e);
    }
  }

  public void printData() {
    for (Client tmpClient : clientList) {
      System.out.println(tmpClient.toString());
    }
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    if (qName.equalsIgnoreCase("client")) {
      client = new Client();
      if (clientList == null) {
        logger.info("Creating synchronizedList");
        clientList = new ArrayList<Client>();
        logger.info("SynchronizedList is created");
      }
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    tmpValue = new String(ch, start, length);
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {

    if (qName.equals("client")) {
      clientList.add(client);
      //queue.put(client);
    }
    if (qName.equalsIgnoreCase("name")) {
      client.setName(tmpValue);
    }
    if (qName.equalsIgnoreCase("surname")) {
      client.setSurname(tmpValue);
    }
    if (qName.equalsIgnoreCase("dateOfBirth")) {
      try {
        client.setDateOfBirth(sdf.parse(tmpValue));
      } catch (ParseException e) {
        logger.error("ERROR PARSE DATE", e);
      }
    }
    if (qName.equalsIgnoreCase("card")) {
      client.getCards().add(tmpValue);
    }
    if (qName.equalsIgnoreCase("account")) {
      client.getAccounts().add(tmpValue);
    }
  }

  public void setQueue(Queue queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    while (true) {
      queue.put(client);
    }
  }
}
