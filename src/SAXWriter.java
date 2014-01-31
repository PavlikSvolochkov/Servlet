
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.xml.sax.SAXException;

public class SAXWriter {

  public static void main(String[] args) throws SAXException {

    try {

      XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

      XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter("d:\\temp\\data\\\\output2.xml"));

      writer.writeStartDocument();
      writer.writeStartElement("clients");
      writer.writeStartElement("client");

      writer.writeStartElement("name");
      writer.writeCharacters("Vasia");
      writer.writeEndElement();

      writer.writeStartElement("surname");
      writer.writeCharacters("Vasilev");
      writer.writeEndElement();

      writer.writeStartElement("dateOfBirth");
      writer.writeCharacters("29.01.1982");
      writer.writeEndElement();

      writer.writeStartElement("cards");
      writer.writeStartElement("card");
      writer.writeCharacters("1236547890");
      writer.writeEndElement();
      writer.writeStartElement("card");
      writer.writeCharacters("9874563210");
      writer.writeEndElement();

      writer.writeStartElement("accounts");
      writer.writeStartElement("account");
      writer.writeCharacters("123");
      writer.writeEndElement();
      writer.writeStartElement("account");
      writer.writeCharacters("124");
      writer.writeEndElement();

      writer.writeEndElement();
      writer.writeEndElement();
      writer.writeEndDocument();

      writer.flush();
      writer.close();

    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
