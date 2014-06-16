
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class NewDataXML implements Runnable {

  static Logger logger = Logger.getLogger(NewDataXML.class);

  private String select
          = "SELECT CLIENTS.ID, CLIENTS.NAME, CLIENTS.SURNAME, CLIENTS.DATEOFBIRTH, ACCOUNTS.ACCOUNT, ACCOUNTS.ID_CLIENT, CARDS.CARD\n"
          + "FROM CLIENTS\n"
          + "LEFT JOIN ACCOUNTS ON CLIENTS.ID = ACCOUNTS.ID_CLIENT\n"
          + "LEFT JOIN CARDS ON CLIENTS.ID = CARDS.ID_CLIENT\n"
          + "ORDER BY clients.id";

  private Client client = null;
  private List<Client> clientList = null;

  private Statement statement = null;
  private DBConnection conn = null;
  private ResultSet result = null;

  public void build() throws SQLException, ClassNotFoundException {
    clientList = new ArrayList<>();
    conn = new DBConnection();
    conn.connect();

    logger.debug("Creating statement...");
    statement = conn.getConnection().createStatement();

    logger.debug("Set fetchsize for statement...");
    statement.setFetchSize(10);
    logger.debug("Fetchsize: " + statement.getFetchSize());

    logger.debug("Get the resultset...");
    result = statement.executeQuery(select);
  }

  @Override
  public void run() {

    int id = 0;
    int cur_id = 1;
    String curr_acc = "";
    int rowCount = 0;

    try {
      build();
      while (result.next()) {
        cur_id = result.getInt("ID");
        String name = result.getString("NAME");
        String surname = result.getString("SURNAME");
        String date = result.getString("DATEOFBIRTH");
        String card = result.getString("CARD");
        String account = result.getString("ACCOUNT");

        if (id != cur_id) {
          client = new Client();
          client.setId(cur_id);
          client.setName(name);
          client.setSurname(surname);
          client.setDateOfBirth(date);
          client.setCard(card);
          client.setAcc(account);
          curr_acc = account;
        }
        if (id == cur_id) {
          client.setCard(card);
          if (account == curr_acc) {
            client.setAcc(account);
          }
        } else {
          clientList.add(client);
          logger.debug(client.toString());
          rowCount++;
        }
        id = cur_id;
      }
    } catch (SQLException e) {
      logger.debug("SQLException", e);
    } catch (Exception e) {
      logger.debug("NullPointer", e);
    }
  }

  public void printClientList() {
    logger.debug("Print clientlist.");
    for (Client c : clientList) {
      logger.debug(c.toString());
    }
  }

  public List<Client> getClientList() {
    return clientList;
  }
}
