
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ServletDataXML extends HttpServlet {

  private static final Logger logger = Logger.getLogger(ServletDataXML.class);

  private static final String SAVE_DIR = "data";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//    try {
//      response.setContentType("text/xml");
//      PrintWriter out = response.getWriter();
//      String filePath = "d:/temp/data/new_clients.xml";
//      response.setContentType("APPLICATION/OCTET-STREAM");
//      response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath + "\"");
//
//      String file = getServletContext().getInitParameter("file-upload");
//
//      NewQueue queue = new NewQueue();

//      NewDataXML dataXML = new NewDataXML(queue);
//      System.out.println("FILE PATH >>> " + filePath);
//      ToXML toXML = new ToXML(queue, filePath);
//
//      Thread t1 = new Thread(dataXML);
//      Thread t2 = new Thread(toXML);
//
//      t1.start();
//      t2.start();
//
//    } catch (SQLException ex) {
//      logger.info("SQLException", ex);
//    } catch (ClassNotFoundException ex) {
//      logger.info("ClassNotFoundException", ex);
//    } catch (XMLStreamException ex) {
//      logger.info("XMLStreamException", ex);
//    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  }
}
