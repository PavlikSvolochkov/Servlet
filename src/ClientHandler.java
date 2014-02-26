
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ClientHandler extends DefaultHandler {

  static Logger logger = Logger.getLogger(ClientHandler.class.getName());

  private String tmpValue;

  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  BlockingQueue queue;
  private Client client = null;
  private List<Client> clientList = null;

  public ClientHandler(BlockingQueue q) {
    this.queue = q;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    if (qName.equalsIgnoreCase("client")) {
      client = new Client();
      if (clientList == null) {
        logger.info("Creating ClientList");
        clientList = new ArrayList<>();
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
        client.toString();
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
    for (Client client : clientList) {
      client.toString();
    }
  }
}
