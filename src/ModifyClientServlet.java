
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModifyClientServlet extends HttpServlet {

  static Logger logger = Logger.getLogger(ModifyClientServlet.class.getName());

  String title = "AddClientServlet";
  String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";

  PrintWriter out;
  DBConnection conn;
  QueryManager manager;

  @Override
  public void init() throws ServletException {
    super.init(); //To change body of generated methods, choose Tools | Templates.

    conn = new DBConnection();
    manager = new QueryManager();

    try {
      conn.connect();
      manager.setStatement(conn.getConnection().createStatement());
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
    String add_name = request.getParameter("add_name");
    String add_surname = request.getParameter("add_surname");
    String add_dateofbirth = request.getParameter("add_dateofbirth");
//----------------------------------------------------------------------------------------------------------------------
    //Variables for updating client
    String update_id = request.getParameter("update_id");
    String update_name = request.getParameter("update_name");
    String update_surname = request.getParameter("update_surname");
    String update_dateofbirth = request.getParameter("update_dateofbirth");
//----------------------------------------------------------------------------------------------------------------------
    //ID for deleting client
    String del_id = request.getParameter("delete_id");
//----------------------------------------------------------------------------------------------------------------------

    if (add_name.equalsIgnoreCase("name")) {
      logger.info("Renaming client...");
      add_name += new Date(System.currentTimeMillis()).toString();
      logger.info("Client is renamed.");
    }

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

  public void printAddHTML(String name, String surname, String dateofbirth) {

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

  public void printUpdateHTML(String id, String name, String surname, String dateofbirth) {
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

  public void printDelHTML(String id) {
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
