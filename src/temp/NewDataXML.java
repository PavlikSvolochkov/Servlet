package temp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
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

//  private NewQueue queue;
  private ToXML toXML;

  private Statement statement;
  private DBConnect conn;
  private ResultSet result;

  private Exchanger<List<NewClient>> ex;

  public NewDataXML(ToXML toXml, Exchanger<List<NewClient>> exList) throws SQLException, ClassNotFoundException {
    clientList = new ArrayList<>();
    conn = new DBConnect();
    conn.connect();

    logger.info("Creating statement...");
    statement = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

    logger.info("Set fetchsize for statement...");
    statement.setFetchSize(FETCH_SIZE);
    logger.info("Fetchsize: " + statement.getFetchSize());

    logger.info("Get the resultset...");
    result = statement.executeQuery(newSelect);
    ex = exList;
    this.toXML = toXml;
    new Thread(this).start();
//    this.queue = queue;
  }

//  ResultSet rs = ps.executeQuery(sql);
//  boolean hasNext = rs.next();
//  while (hasNext) {
//   // init local data ie:
//   // long id = rs.getLong("id");
//   hasNext = rs.next();
//   if (hasNext) {
//     // processing for rows that aren't last
//   } else {
//     // processing for last row
//   }
//  }
  @Override
  public void run() {

    int id = 0;
    int curId = 1;
    int clientCount = 0;
    int count = 0;

    try {
      boolean hasNext = result.next();
      
      while (hasNext) {

        if (hasNext) {

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
            clientList.add(client);
            count++;
//          queue.put(client);
          } else {
            setNotNull(card, "CARD");
            setNotNull(account, "ACC");
          }
          id = curId;
          clientCount++;
          
          hasNext = result.next();

          // Передаем лист в другой поток и очищаем его
          if ((clientCount == FETCH_SIZE)) {
            System.out.println("NewDataXML >> clientList.size(): " + clientList.size());
            clientList = ex.exchange(clientList);
            System.out.println("Good bye, list!");
            clientCount = 0;
          }
        } else {
          clientList = ex.exchange(clientList);
          toXML.setLast(false);
        }
      }
      logger.info("Clients count from NewDataXML: " + count);
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
