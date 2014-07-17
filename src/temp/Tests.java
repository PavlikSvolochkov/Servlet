package temp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Exchanger;
import javax.xml.stream.XMLStreamException;

class Tests {

  public static void main(String[] args) throws InterruptedException, XMLStreamException, IOException, SQLException, ClassNotFoundException {

    String fileLocation = "d:/temp/data/new_clients.xml";
    
    Exchanger<List<NewClient>> exgr = new Exchanger<List<NewClient>>();
    NewQueue queue = new NewQueue();

    ToXML toXML = new ToXML(exgr, fileLocation);
    NewDataXML dataXML = new NewDataXML(toXML, exgr);
    
  }
}
