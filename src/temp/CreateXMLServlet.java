package temp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Exchanger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import org.apache.log4j.Logger;

public class CreateXMLServlet extends HttpServlet {

  private static final Logger logger = Logger.getLogger(CreateXMLServlet.class.getName());

  public static final int BYTES_DOWNLOAD = 4096;
  private File xmlFile;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.debug("Run doGet method...");
    response.setContentType("text/xml");
    response.setHeader("Content-Disposition", "attachment; filename=clients.xml");

    Exchanger<List<NewClient>> exchanger = new Exchanger();

    try {

      ToXML toXML = new ToXML(exchanger);
      NewDataXML dataXML = new NewDataXML(toXML, exchanger);

      toXML.join();
      dataXML.join();

      xmlFile = toXML.getFile();

      logger.debug("XML File >>> " + xmlFile.getAbsolutePath());
      logger.debug("XML file length >>> " + xmlFile.length());

    } catch (XMLStreamException ex) {
      logger.debug("XMLStreamException");
    } catch (SQLException ex) {
      logger.debug("SQLException");
    } catch (ClassNotFoundException ex) {
      logger.debug("ClassNotFoundException");
    } catch (InterruptedException ex) {
      logger.debug("InterruptedException");
    }
    logger.debug("doGet method was finished");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    processRequest(request, response);
  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet CreateXMLServlet</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet CreateXMLServlet at " + request.getContextPath() + "</h1>");
      out.println("<h3>Servlet CreateXMLServlet " + request.getParameter("createXML") + "</h3>");
      out.println("<h4>" + getServletInfo() + "</h4>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }
}
