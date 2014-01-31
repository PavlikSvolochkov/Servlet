
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ClientSaxParser extends DefaultHandler {

  private String tmpValue;
  private String xmlFileName;

  private List<Client> clientList = null;
  private Client client = null;

  SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YY");

  public List<Client> getClientList() {
    return clientList;
  }

  public ClientSaxParser(String bookXmlFileName) {
    this.xmlFileName = bookXmlFileName;
    clientList = new ArrayList<Client>();
    parseDocument();
    printDatas();
  }

  private void parseDocument() {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      SAXParser parser = factory.newSAXParser();
      parser.parse(xmlFileName, this);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
  }

  private void printDatas() {
    for (Client tmpClient : clientList) {
      System.out.println(tmpClient.toString());
    }
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    if (qName.equalsIgnoreCase("client")) {
      client = new Client();
      if (clientList == null) {
        clientList = new ArrayList<>();
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
  }
  
  public static void main(String[] args) {
    ClientSaxParser parser = new ClientSaxParser("d:\\dev\\Project\\ClientsServlet\\src\\clients.xml");
    parser.parseDocument();
    parser.printDatas();
  }
}
