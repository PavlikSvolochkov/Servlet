package temp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.log4j.Logger;

public class ToXML implements Runnable {

  private static final Logger logger = Logger.getLogger(ToXML.class);

  int count = 0;

  private List<NewClient> clientList;
//  private NewQueue queue;
  private boolean checkLast;

  private XMLOutputFactory outputFactory;
  private XMLStreamWriter writer;

  private Exchanger<List<NewClient>> ex;

  public ToXML(Exchanger<List<NewClient>> exList, String filePath) throws IOException, XMLStreamException {
//    this.queue = queue;
    this.checkLast = true;

    logger.info("Creating file: " + filePath);
    File file = new File(filePath);
    file.createNewFile();

    logger.info("Creating writer...");
    this.outputFactory = XMLOutputFactory.newInstance();
    this.writer = outputFactory.createXMLStreamWriter(new FileWriter(filePath));
    logger.info("writer was created.");
    ex = exList;
    new Thread(this).start();
  }

  @Override
  public void run() {

    try {
//      Thread.sleep(1000);
      writer.writeStartElement("clients");

      while (checkLast) {
        clientList = ex.exchange(new ArrayList<NewClient>());
        logger.info("ClientList.size(): " + clientList.size());
        System.out.println("Hello, List!");

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
          count++;
        }
      }
      logger.info("Clients count from ToXML.class: " + count);
      writer.writeEndElement();
      writer.writeEndDocument();
      writer.close();
    } catch (XMLStreamException ex) {
      logger.info("XMLStreamException", ex);
    } catch (InterruptedException ex) {
      logger.info("InterruptedException", ex);
    }
  }

  public void setClientList(List<NewClient> clientList) {
    this.clientList = clientList;
  }

  public void clientsInfo() {
    for (NewClient c : clientList) {
      System.out.println(c.toString());
    }
  }

  public void setLast(boolean checkLast) {
    this.checkLast = checkLast;
  }
}
