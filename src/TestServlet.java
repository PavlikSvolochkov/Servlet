
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class TestServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  
  static Logger logger = Logger.getLogger(TestServlet.class.getName());

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    doPost(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");

    PrintWriter out = response.getWriter();
    out.println("Здарова!<br/>");

    boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
    if (!isMultipartContent) {
      out.println("You are not trying to upload<br/>");
      return;
    }
    out.println("Пытаешься закачать файл на сервер? О.о<br/>");

    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    try {
      List<FileItem> fields = upload.parseRequest(request);
      out.println("Количество закаченных файлов: " + fields.size() + "<br/><br/>");

      Iterator<FileItem> it = fields.iterator();

      if (!it.hasNext()) {
        out.println("Файл не указан");
        return;
      }

      FileItem fileItem = it.next();
      //  Сохраняем файл в catalina.base\webapps\data\ 
      //File testFile = new File(System.getProperty("catalina.base") + "\\webapps\\data\\" + fileItem.getName());
      File testFile = new File("/TEMP_DATA/" + fileItem.getName());
      testFile.createNewFile();
      FileWriter fw = new FileWriter(testFile);
      fw.append(fileItem.getString());
      fw.flush();
      out.println("Файл создан.<br/>");

      System.out.println("TEST_FILE_NAME >>>> " + testFile.getName() + "\n------------------------------\n");

      DBConnection conn = new DBConnection();
      conn.connect();
      
      ClientSaxParser saxParser = new ClientSaxParser("/TEMP_DATA/" + fileItem.getName());

      XMLData insert = new XMLData();
      insert.setConn(conn.getConnection());
      insert.setClientList(saxParser.getSyncClientList());
      insert.insert();

      conn.close();
    } catch (ParseException | SQLException | ClassNotFoundException | FileUploadException ex) {
      logger.info("ERROR INSERT DATA FROM XML");
    }
  }
}
