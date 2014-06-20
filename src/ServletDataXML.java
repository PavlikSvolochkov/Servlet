
import java.io.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import org.apache.log4j.Logger;

public class ServletDataXML extends HttpServlet {

  private static final Logger logger = Logger.getLogger(ServletDataXML.class);

  /**
   * Name of the directory where uploaded files will be saved, relative to the web application directory.
   */
  private static final String SAVE_DIR = "data";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    try {
      response.setContentType("text/xml");
      PrintWriter out = response.getWriter();
      String filePath = "d:/temp/data/new_clients.xml";
      response.setContentType("APPLICATION/OCTET-STREAM");
      response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath + "\"");

      String file = getServletContext().getInitParameter("file-upload");

      NewQueue queue = new NewQueue();

      NewDataXML dataXML = new NewDataXML(queue);
      System.out.println("FILE PATH >>> " + filePath);
      ToXML toXML = new ToXML(queue, filePath);

      Thread t1 = new Thread(dataXML);
      Thread t2 = new Thread(toXML);

      t1.start();
      t2.start();
      
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(ServletDataXML.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(ServletDataXML.class.getName()).log(Level.SEVERE, null, ex);
    } catch (XMLStreamException ex) {
      java.util.logging.Logger.getLogger(ServletDataXML.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  }
}
