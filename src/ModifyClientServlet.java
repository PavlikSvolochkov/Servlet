
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ModifyClientServlet extends HttpServlet {

  static Logger logger = Logger.getLogger(ModifyClientServlet.class.getName());

  private String add_name;
  private String add_surname;
  private String add_dateofbirth;

  private String update_id;
  private String update_name;
  private String update_surname;
  private String update_dateofbirth;

  private String del_id;

  private String title = "AddClientServlet";
  private String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";

  private PrintWriter out;
  private DBConnection conn;
  private QueryManager manager;

  @Override
  public void init() throws ServletException {
    super.init(); //To change body of generated methods, choose Tools | Templates.

    try {
      conn = new DBConnection();
      conn.connect();
      manager = new QueryManager(conn.getConnection());
    } catch (SQLException ex) {
      logger.info("ERROR INSERT CLIENT");
    } catch (ClassNotFoundException ex) {
      logger.info("ERROR IN CONNECTION");
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    out = response.getWriter();
//----------------------------------------------------------------------------------------------------------------------
    //Variables for inserting client
    add_name = request.getParameter("add_name");
    add_surname = request.getParameter("add_surname");
    add_dateofbirth = request.getParameter("add_dateofbirth");
//----------------------------------------------------------------------------------------------------------------------
    //Variables for updating client
    update_id = request.getParameter("update_id");
    update_name = request.getParameter("update_name");
    update_surname = request.getParameter("update_surname");
    update_dateofbirth = request.getParameter("update_dateofbirth");
//----------------------------------------------------------------------------------------------------------------------
    //ID for deleting client
    del_id = request.getParameter("delete_id");
//----------------------------------------------------------------------------------------------------------------------

    if (request.getParameter("add") != null) {
      try {
        manager.insertClient(add_name, add_surname, add_dateofbirth);
        printAddHTML(add_name, add_surname, add_dateofbirth);
      } catch (SQLException ex) {
        logger.info("ERROR INSERT CLIENT!");
      }
    } else if (request.getParameter("upd") != null) {
      try {
        manager.updateClient(update_id, update_name, update_surname, update_dateofbirth);
        printUpdateHTML(update_id, update_name, update_surname, update_dateofbirth);
      } catch (SQLException ex) {
        logger.info("ERROR UPDATING CLIENT!");
      }
    } else if (request.getParameter("del") != null) {
      try {
        manager.deletClient(del_id);
        printDelHTML(del_id);
      } catch (SQLException ex) {
        logger.info("ERROR DELETING CLIENT!");
      }
    }
  }

  public void printAddHTML(String name, String surname, String dateofbirth) throws SQLException {
    out.println(docType
            + "<html>\n<head><title>" + title + "</title></head>\n"
            + "<body bgcolor=\"#f0f0f0\">\n"
            + "<h1 align=\"center\">" + title + "</h1>\n"
            + "<b>Added Client is:</b>"
            + "<ul>\n<li><b>First Name<b/>: " + name + "\n"
            + "<li><b>Last Name<b/>: " + surname + "\n"
            + "<li><b>Date of birth<b/>: " + dateofbirth + "\n"
            + "</ul>\n" + "</body></html>");
  }

  public void printUpdateHTML(String id, String name, String surname, String dateofbirth) throws SQLException {
    out.println(docType
            + "<html>\n<head><title>" + title + "</title></head>\n"
            + "<body bgcolor=\"#f0f0f0\">\n"
            + "<h1 align=\"center\">" + title + "</h1>\n"
            + "<b>Added Client is:</b>"
            + "<ul>\n"
            + "<li><b>Client ID:</b> " + id + "\n"
            + "<li><b>First Name</b>: " + name + "\n"
            + "<li><b>Last Name</b>: " + surname + "\n"
            + "<li><b>Date of birth</b>: " + dateofbirth + "\n"
            + "</ul>\n" + "</body></html>");
  }

  public void printDelHTML(String id) throws SQLException {
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
