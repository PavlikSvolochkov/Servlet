
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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

  private BlockingQueue queue = new ArrayBlockingQueue<>(1024);

  public ClientSaxParser(String xmlFile, BlockingQueue q) {
    logger.info("SAXParser created for file: " + xmlFile);
    this.queue = q;
    this.fileName = xmlFile;
    clientList = new ArrayList<Client>();
  }

  @Override
  public void run() {
    logger.info("Start ClientSaxParser run() method.");
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      logger.info("Parse file...");
      SAXParser parser = factory.newSAXParser();
      parser.parse(fileName, this);
      logger.info("Parsing file complited");
    } catch (ParserConfigurationException | SAXException | IOException e) {
      logger.error("ERROR PARSE DOCUMENT", e);
    }
    System.out.println("//----------------------------------------------------------------------------------------------------------------------");
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

  public List<Client> getClientList() {
    return clientList;
  }

  public void setQueue(BlockingQueue queue) {
    this.queue = queue;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    if (qName.equalsIgnoreCase("client")) {
      client = new Client();
      if (clientList == null) {
        logger.info("Creating ClientList");
        clientList = new ArrayList();
        logger.info("ClientList is created");
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
      try {
        System.out.println("put Client into the queue...");
        queue.put(client);
        System.out.println("put Client complited");
      } catch (InterruptedException ex) {
        logger.info("ERROR in queue.put() method.");
      }
    }
    if (qName.equalsIgnoreCase("name")) {
      client.setName(tmpValue);
    }
    if (qName.equalsIgnoreCase("surname")) {
      client.setSurname(tmpValue);
    }
    if (qName.equalsIgnoreCase("dateOfBirth")) {
      client.setDateOfBirth(tmpValue);
    }
    if (qName.equalsIgnoreCase("card")) {
      client.getCards().add(tmpValue);
    }
    if (qName.equalsIgnoreCase("account")) {
      client.getAccounts().add(tmpValue);
    }
  }

  @Override
  public void startDocument() throws SAXException {
    System.out.println("START DOCUMENT PARSING");
  }

  @Override
  public void endDocument() throws SAXException {
    System.out.println("END DOCUMENT PARSING");
  }
}
