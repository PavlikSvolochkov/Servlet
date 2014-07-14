package temp;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ClientParserHandler extends DefaultHandler {

  private static final Logger logger = Logger.getLogger(ClientParserHandler.class);

  private String tmpValue;
  private String fileName;

  private List<NewClient> clientList;
  private NewClient client;

  @Override
  public void startDocument() throws SAXException {
    System.out.println("START DOCUMENT PARSING");
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    tmpValue = new String(ch, start, length);
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equalsIgnoreCase("client")) {
      client = new NewClient();
      if (clientList == null) {
        logger.info("Creating ClientList");
        clientList = new ArrayList();
        logger.info("ClientList is created");
      }
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (qName.equals("client")) {
      clientList.add(client);
//      queue.put(client);
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
  public void endDocument() throws SAXException {
    System.out.println("END DOCUMENT PARSING");
  }

  public List<NewClient> getClientList() {
    return clientList;
  }
}
