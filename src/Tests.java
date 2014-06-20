
import java.io.IOException;
import java.sql.SQLException;
import javax.xml.stream.XMLStreamException;

class Tests {

  public static void main(String[] args) throws InterruptedException, XMLStreamException, IOException, SQLException, ClassNotFoundException {

    String fileLocation = "d:/temp/data/new_clients.xml";
    NewQueue queue = new NewQueue();

    NewDataXML dataXML = new NewDataXML(queue);
    ToXML toXML = new ToXML(queue, fileLocation);

    Thread t1 = new Thread(dataXML);
    Thread t2 = new Thread(toXML);

    t1.start();
    t2.start();
  }
}
