package temp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class NewDataXML extends Thread {

  private static final Logger logger = Logger.getLogger(NewDataXML.class);

  private int FETCH_SIZE = 10;

  private final String newSelect
          = "select * from (SELECT CLIENTS.ID client_id, CLIENTS.NAME, CLIENTS.SURNAME, CLIENTS.DATEOFBIRTH, ACCOUNTS.ACCOUNT, NULL CARD\n"
          + "FROM CLIENTS\n"
          + "LEFT JOIN ACCOUNTS ON CLIENTS.ID = ACCOUNTS.ID_CLIENT\n"
          + "UNION\n"
          + "SELECT CLIENTS.ID client_id, CLIENTS.NAME, CLIENTS.SURNAME, CLIENTS.DATEOFBIRTH, NULL, CARDS.CARD CARD\n"
          + "FROM CLIENTS\n"
          + "LEFT JOIN CARDS ON CLIENTS.ID = CARDS.ID_CLIENT)\n"
          + "order by client_id";

  private NewClient client;
  private List<NewClient> clientList;

  private NewQueue queue;

  private Statement statement;
  private DBConnect conn;
  private ResultSet result;

  public NewDataXML(NewQueue queue) throws SQLException, ClassNotFoundException {
    clientList = new ArrayList<>();
    conn = new DBConnect();
    conn.connect();

    logger.info("Creating statement...");
    statement = conn.getConnection().createStatement();

    logger.info("Set fetchsize for statement...");
    statement.setFetchSize(FETCH_SIZE);
    logger.info("Fetchsize: " + statement.getFetchSize());

    logger.info("Get the resultset...");
    result = statement.executeQuery(newSelect);

    this.queue = queue;
  }

  @Override
  public void run() {

    int id = 0;
    int curId = 1;
    int clientCount = 0;

    try {
      while (result.next()) {

        if (clientCount == FETCH_SIZE) {
          notify();
        }

        curId = result.getInt("CLIENT_ID");
        String card = result.getString("CARD");
        String account = result.getString("ACCOUNT");

        if (id != curId) {
          client = new NewClient();
          client.setId(curId);
          client.setName(result.getString("NAME"));
          client.setSurname(result.getString("SURNAME"));
          client.setDateOfBirth(result.getString("DATEOFBIRTH"));
          setNotNull(card, "CARD");
          setNotNull(account, "ACC");
          id = curId;
//          clientList.add(client);
          queue.put(client);
          clientCount++;
        } else {
          setNotNull(card, "CARD");
          setNotNull(account, "ACC");
        }
        id = curId;
      }
      logger.info("Clients count >>> " + clientCount);
    } catch (SQLException e) {
      logger.info("SQLException", e);
    } catch (InterruptedException ex) {
      logger.info("InterruptedException", ex);
    }
  }

  private void setNotNull(String str, String key) {
    if (str != null && "CARD".equals(key)) {
      client.setCard(str);
    }
    if (str != null && "ACC".equals(key)) {
      client.setAcc(str);
    }
  }

  public List<NewClient> getClientList() {
    return clientList;
  }
}