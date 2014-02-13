
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

public class ProceduresTests {

  static Logger logger = Logger.getLogger(ProceduresTests.class.getName());

  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    
    BasicConfigurator.configure();
    
    NDC.push("Client #111");
//    logger.info("\nINFO >>> Connecting to database...");
//    logger.debug("DEBUG >>> Hello, world");
    
    DBConnection conn = new DBConnection();
    conn.connect();
    ClientSaxParser clientSaxParser = new ClientSaxParser("d:\\temp\\data\\clients.xml");
    clientSaxParser.printData();

    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    sdf.format(date);
    System.out.println(sdf.format(date));

    logger.info("CallableStatement call NEW_CLIENT procedure");
    CallableStatement new_client = conn.getConnection().prepareCall("{call NEW_CLIENT(?, ?, ?)}");
    new_client.setString(1, "Tosh");
    new_client.setString(2, "Kengur");
    new_client.setString(3, sdf.format(date));
    new_client.execute();
    new_client.close();
//    PreparedStatement new_client_cards_acc = conn.getConnection().prepareStatement("{call NEW_CLIENT_CARDS_ACCOUNTS(?, ?, ?, ?, ?)}");
//    new_client_cards_acc.setString(1, "Benedict");
//    new_client_cards_acc.setString(2, "Ermolaev");
//    new_client_cards_acc.setString(3, sdf.format(date));
//    new_client_cards_acc.setObject(4, cards);
//    new_client_cards_acc.setObject(5, cards);
//    new_client_cards_acc.execute();

    conn.close();
  }
}
