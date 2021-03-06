
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.log4j.Logger;

public class DataXML {

  private static final Logger logger = Logger.getLogger(DataXML.class);

  private String clientQuery = "SELECT * FROM CLIENTS";
  private String cardsQuery = "SELECT * FROM CARDS";
  private String accountQuery = "SELECT * FROM ACCOUNTS";
  private String fileLocation = "d:/temp/";
  private String fileName = "clients.xml";

  private Connection conn;

  private ResultSet clientRS;
  private ResultSet cardsRS;
  private ResultSet accountsRS;

  private Statement stmtClient;
  private Statement stmtCards;
  private Statement stmtAccounts;

  private XMLOutputFactory outputFactory;
  private XMLStreamWriter writer;

  public DataXML(String loc, String name) throws XMLStreamException, IOException {
    logger.info("Set filelocation and filename");
    this.fileLocation = loc;
    this.fileName = name;
    this.outputFactory = XMLOutputFactory.newInstance();
    logger.info("Create writer for file: " + fileLocation + fileName);
    this.writer = outputFactory.createXMLStreamWriter(new FileWriter(fileLocation + fileName));
    logger.info("writer is created");
  }

  void build() throws SQLException, ClassNotFoundException {
    logger.info("Creating statements and resultsets");
    logger.info("Oracle FetchSize >>> " + stmtClient.getFetchSize());
    this.stmtClient = conn.createStatement();
    this.stmtCards = conn.createStatement();
    this.stmtAccounts = conn.createStatement();

    this.clientRS = stmtClient.executeQuery(getClientQuery());
    this.cardsRS = stmtCards.executeQuery(getCardsQuery());
    this.accountsRS = stmtAccounts.executeQuery(getAccountQuery());
    logger.info("statements and resultsets is created");
  }

  public void toXML() throws XMLStreamException, IOException, SQLException, ClassNotFoundException {

    logger.info("Inserting data in file");
    writer.writeStartElement("clients");

    while (clientRS.next()) {

      int idClient = clientRS.getInt("ID");
      String name = clientRS.getString("NAME");
      String surname = clientRS.getString("SURNAME");
      String birthDate = clientRS.getString("DATEOFBIRTH");

      // print the results
      System.out.format("%s, %s, %s, %s\n", idClient, name, surname, birthDate);

      writer.writeStartElement("client");
      writer.writeAttribute("id", String.valueOf(clientRS.getInt("ID")));

      writer.writeStartElement("name");
      writer.writeCharacters(clientRS.getString("NAME"));
      writer.writeEndElement();

      writer.writeStartElement("surname");
      writer.writeCharacters(clientRS.getString("SURNAME"));
      writer.writeEndElement();

      writer.writeStartElement("dateOfBirth");
      writer.writeCharacters(clientRS.getString("DATEOFBIRTH"));
      writer.writeEndElement();

      writer.writeStartElement("cards");

      cardsQuery = "SELECT * FROM CARDS WHERE ID_CLIENT=" + idClient;
      cardsRS = stmtCards.executeQuery(cardsQuery);

      while (cardsRS.next()) {
        writer.writeStartElement("card");
        writer.writeCharacters(cardsRS.getString("CARD"));
        writer.writeEndElement();
      }
      writer.writeEndElement();

      accountQuery = "SELECT * FROM ACCOUNTS WHERE ID_CLIENT=" + idClient;
      accountsRS = stmtAccounts.executeQuery(accountQuery);

      writer.writeStartElement("accounts");
      while (accountsRS.next()) {
        writer.writeStartElement("account");
        writer.writeCharacters(accountsRS.getString("ACCOUNT"));
        writer.writeEndElement();
      }
      writer.writeEndElement();
      writer.writeEndElement(); // EndElement <client>

      writer.flush();
    }
    writer.writeEndElement();
    writer.writeEndDocument();
    writer.close();
    logger.info("Insert data in file is complited");
  }

  public void setFileName(String fileName) {
    if (!"".equals(fileName)) {
      this.fileName = fileName;
    }
  }

  public void setFileLocation(String fileLoc) {
    if (!"".equals(fileLoc)) {
      this.fileLocation = fileLoc;
    }
  }

  public String getClientQuery() {
    return clientQuery;
  }

  public String getCardsQuery() {
    return cardsQuery;
  }

  public String getAccountQuery() {
    return accountQuery;
  }

  public ResultSet getClientRS() {
    return clientRS;
  }

  public ResultSet getCardsRS() {
    return cardsRS;
  }

  public ResultSet getAccountsRS() {
    return accountsRS;
  }

  public void setClienQuery(String clientQ) {
    this.clientQuery = clientQ;
  }

  public void setCardsQuery(String cardsQ) {
    this.cardsQuery = cardsQ;
  }

  public void setAccountQuery(String accountQ) {
    this.accountQuery = accountQ;
  }

  public void setConnection(Connection conn) {
    logger.info("Set connection");
    this.conn = conn;
    if (this.conn == null) {
      logger.error("Connection is null");
    }
  }
}
