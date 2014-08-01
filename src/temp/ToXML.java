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

public class ToXML extends Thread {

  private static final Logger logger = Logger.getLogger(ToXML.class);

  private int count;
  private String filePath;
  private boolean checkLast;

  private List<NewClient> clientList;

  private File file;

  private XMLOutputFactory outputFactory;
  private XMLStreamWriter writer;

  private Exchanger<List<NewClient>> ex;

  public ToXML(Exchanger<List<NewClient>> exList) throws IOException, XMLStreamException {
    this.checkLast = true;
    this.filePath = "d:\\temp\\data\\new_clients.xml";
//    this.filePath = "/output.xml";
    this.count = 0;

    logger.debug("Creating file: " + filePath);
    file = new File(filePath);
    file.createNewFile();
    System.out.println(file.getAbsoluteFile());

    logger.debug("Creating writer...");
    this.outputFactory = XMLOutputFactory.newInstance();
    this.writer = outputFactory.createXMLStreamWriter(new FileWriter(filePath));
    logger.debug("writer was created.");
    ex = exList;
    new Thread(this).start();
  }

  @Override
  public void run() {

    try {
      writer.writeStartElement("clients");

      while (true) {

        clientList = ex.exchange(new ArrayList<NewClient>());
        logger.debug("ClientList.size(): " + clientList.size());
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
        if (checkLast == false) {
          break;
        }
      }
      logger.debug("Clients count from ToXML.class: " + count);
      writer.writeEndElement();
      writer.writeEndDocument();
      writer.close();
      logger.debug("ToXML.class finished.");
    } catch (XMLStreamException ex) {
      logger.debug("XMLStreamException", ex);
    } catch (InterruptedException ex) {
      logger.debug("InterruptedException", ex);
    }
  }

  public File getFile() {
    logger.debug("ToXML.getFile() method return xml file: " + file.getName() + " length: " + file.length());
    return file;
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
