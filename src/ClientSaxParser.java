
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ClientSaxParser extends DefaultHandler {

  static Logger logger = Logger.getLogger(ClientSaxParser.class);

  private String tmpValue;
  private String xmlFileName;

  private Client client = null;
  private List<Client> syncClientList = null;
  private ClientQueue queue;

  SimpleDateFormat sdf = new SimpleDateFormat("dd-Mmm-yyyy");

  public ClientSaxParser(String xmlFile) {
    this.xmlFileName = xmlFile;
    syncClientList = Collections.synchronizedList(new ArrayList<Client>());
    parseDocument();
  }

  public void setQueue(ClientQueue q) {
    this.queue = q;
  }
  
  private void parseDocument() {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      logger.info("Creating parser for file: " + xmlFileName);
      SAXParser parser = factory.newSAXParser();
      parser.parse(xmlFileName, this);
      logger.info("Parsing file complited");
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
  }

  public List<Client> getSyncClientList() {
    return syncClientList;
  }

  public void printData() {
    for (Client tmpClient : syncClientList) {
      System.out.println(tmpClient.toString());
    }
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    if (qName.equalsIgnoreCase("client")) {
      client = new Client();
      if (syncClientList == null) {
        logger.info("Creating synchronizedList");
        syncClientList = Collections.synchronizedList(new ArrayList<Client>());
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

    synchronized (syncClientList) {
      if (qName.equals("client")) {
        syncClientList.add(client);
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
          e.printStackTrace();
        }
      }
      if (qName.equalsIgnoreCase("card")) {
        client.getCards().add(tmpValue);
      }
      if (qName.equalsIgnoreCase("account")) {
        client.getAccounts().add(tmpValue);
      }
      queue.putElement(client);
    }
  }
}
