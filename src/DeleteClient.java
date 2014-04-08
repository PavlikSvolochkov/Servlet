
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteClient extends HttpServlet {

  static Logger logger = Logger.getLogger(ModifyClientServlet.class.getName());

  DBConnection conn;
  QueryManager manager;

  @Override
  public void init() throws ServletException {
    super.init(); //To change body of generated methods, choose Tools | Templates.

    conn = new DBConnection();
    manager = new QueryManager();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");

    PrintWriter out = response.getWriter();
    String title = "DeleteClientServlet";
    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";

    String id = request.getParameter("delete_id");

    try {
      conn.connect();
      manager.setStatement(conn.getConnection().createStatement());
      manager.deletClient(id);
      manager.closeStatement();
      conn.close();
    } catch (SQLException ex) {
      logger.info("ERROR INSERT CLIENT");
    } catch (ClassNotFoundException ex) {
      logger.info("ERROR IN CONNECTION");
    }

    out.println(docType
            + "<html>\n<head><title>" + title + "</title></head>\n"
            + "<body bgcolor=\"#f0f0f0\">\n"
            + "<h1 align=\"center\">" + title + "</h1>\n"
            + "<b>Client whith id " + id + " deleted.</b>"
            + "</body></html>");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
