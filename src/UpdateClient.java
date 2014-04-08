
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateClient extends HttpServlet {

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
    String title = "UpdateClientServlet";
    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";

    String id = request.getParameter("update_id");
    String name = request.getParameter("update_name");
    String surname = request.getParameter("update_surname");
    String dateofbirth = request.getParameter("update_dateofbirth");

    try {
      conn.connect();
      manager.setStatement(conn.getConnection().createStatement());
      manager.updateClient(id, name, surname, dateofbirth);
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
            + "<b>Added Client is:</b>"
            + "<ul>\n"
            + "<li><b>Client ID:</b> " + id +"\n"
            + "<li><b>First Name</b>: " + name + "\n"
            + "<li><b>Last Name</b>: " + surname + "\n"
            + "<li><b>Date of birth</b>: " + dateofbirth + "\n"
            + "</ul>\n" + "</body></html>");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
