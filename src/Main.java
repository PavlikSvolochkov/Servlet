

public class Main {

  public static void main(String[] args) throws Exception {
    
    DBConnection conn = new DBConnection();
    conn.connect();
    
    TestDataXML dataXML = new TestDataXML();
    dataXML.setConnection(conn.getConnection());
    dataXML.toXML();
    
    conn.close();
  }
}
