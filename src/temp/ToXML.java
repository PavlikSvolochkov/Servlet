package temp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.log4j.Logger;

public class ToXML implements Runnable {

  private static final Logger logger = Logger.getLogger(ToXML.class);

  private Queue<NewClient> clientList;
  private NewQueue queue;

  private XMLOutputFactory outputFactory;
  private XMLStreamWriter writer;

  public ToXML(NewQueue queue, String filePath) throws IOException, XMLStreamException {
    this.queue = queue;

    logger.info("Creating file: " + filePath);
    File file = new File(filePath);
    file.createNewFile();

    logger.info("Creating writer...");
    this.outputFactory = XMLOutputFactory.newInstance();
    this.writer = outputFactory.createXMLStreamWriter(new FileWriter(filePath));
    logger.info("writer was created.");
  }

  @Override
  public void run() {

    try {
      Thread.sleep(1000);
      clientList = queue.get();

      writer.writeStartElement("clients");
      while (clientList.isEmpty()) {
        try {

          logger.info("Get the list.");
          toXML();
          notify();

        } catch (XMLStreamException ex) {
          logger.info("XMLStreamException", ex);
        } catch (IOException ex) {
          logger.info("IOException", ex);
        }
      }
      writer.writeEndElement();
      writer.writeEndDocument();
      writer.close();
      //logger.info("Clients count >>> " + clientList.size());
    } catch (InterruptedException e) {
      logger.info("InterruptedException", e);
    } catch (XMLStreamException ex) {
      logger.info("XMLStreamException", ex);
    }
  }

  public void toXML() throws XMLStreamException, IOException {

//    writer.writeStartElement("clients");
    for (NewClient c : clientList) {
      writer.writeStartElement("client");
      writer.writeAttribute("id", String.valueOf(c.getId()));

      writer.writeStartElement("name");
      writer.writeCharacters(c.getName());
      writer.writeEndElement();

      writer.writeStartElement("surname");
      writer.writeCharacters(c.getSurname());
      writer.writeEndElement();

      writer.writeStartElement("dateOfBirth");
      writer.writeCharacters(c.getDateOfBirth());
      writer.writeEndElement();

      // Cards
      writer.writeStartElement("cards");
      for (String card : c.getCards()) {
        writer.writeStartElement("card");
        writer.writeCharacters(card);
        writer.writeEndElement();
      }
      writer.writeEndElement();

      // Accounts
      writer.writeStartElement("accounts");
      for (String acc : c.getAccounts()) {
        writer.writeStartElement("account");
        writer.writeCharacters(acc);
        writer.writeEndElement();
      }
      writer.writeEndElement();

      // EndElement <client>
      writer.writeEndElement();
      writer.flush();
    }
//    writer.writeEndElement();
//    writer.writeEndDocument();
//    writer.close();
    logger.info("Clients count >>> " + clientList.size());
  }

  public void setClientList(Queue<NewClient> clientList) {
    this.clientList = clientList;
  }

  public void clientsInfo() {
    for (NewClient c : clientList) {
      System.out.println(c.toString());
    }
  }
}