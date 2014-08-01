package temp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Exchanger;
import javax.xml.stream.XMLStreamException;

class Tests {

  public static void main(String[] args) throws InterruptedException, XMLStreamException, IOException, SQLException, ClassNotFoundException {

    Exchanger<List<NewClient>> exchanger = new Exchanger();
    
    ToXML toXML = new ToXML(exchanger);
    NewDataXML dataXML = new NewDataXML(toXML, exchanger);

  }
}
