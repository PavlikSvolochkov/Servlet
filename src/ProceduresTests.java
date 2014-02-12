
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ProceduresTests {

  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    DBConnection conn = new DBConnection();
    conn.connect();

    ClientSaxParser clientSaxParser = new ClientSaxParser("d:\\temp\\data\\clients.xml");
    clientSaxParser.printData();

    int[] cards = new int[4];
    for (int i = 0; i < cards.length; i++) {
      cards[i] = i * 1024;
      System.out.println(cards[i]);
    }

    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    sdf.format(date);
    System.out.println(sdf.format(date));

    CallableStatement new_client = conn.getConnection().prepareCall("{call NEW_CLIENT(?, ?, ?)}");
    new_client.setString(1, "Tosha");
    new_client.setString(2, "Kengur");
    new_client.setString(3, sdf.format(date));
    new_client.execute();
    new_client.close();
    
    CallableStatement new_client_cards_acc = conn.getConnection().prepareCall("{call NEW_CLIENT_CARDS_ACCOUNTS(?, ?, ?, ?, ?)}");
    new_client_cards_acc.setString(1, "Benedict");
    new_client_cards_acc.setString(2, "Ermolaev");
    new_client_cards_acc.setString(3, sdf.format(date));
    new_client_cards_acc.setArray(4, cards);
    new_client_cards_acc.setObject(5, cards[1]);
    new_client_cards_acc.execute();
    
    conn.close();
  }
}
