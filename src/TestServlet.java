
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

  private List<FileItem> fields;
  private Iterator<FileItem> iterator;

  private FileItemFactory factory;
  private ServletFileUpload upload;

  private File testFile;
  private FileWriter fw;
  private FileItem fileItem;

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

    factory = new DiskFileItemFactory();
    upload = new ServletFileUpload(factory);

    try {
      fields = upload.parseRequest(request);
      out.println("Количество закаченных файлов: " + fields.size() + "<br/><br/>");
      iterator = fields.iterator();
      if (!iterator.hasNext()) {
        out.println("Файл не указан");
        return;
      }
      fileItem = iterator.next();
      testFile = new File("/TEMP_DATA/" + fileItem.getName());
      testFile.createNewFile();
      fw = new FileWriter(testFile);
      fw.append(fileItem.getString());
      fw.flush();
    } catch (FileUploadException ex) {
      logger.error("FILEUPLOAD EXCEPTION", ex);
    }

    out.println("Файл создан.<br/>");
    System.out.println("\nTEST_FILE_NAME >>>> " + testFile.getAbsolutePath());
    
//----------------------------------------------------------------------------------------------------------------------
    
    Queue1 queue = new Queue1();

    try {
      
      DBConnection conn = new DBConnection();
      conn.connect();

      ClientSaxParser parser = new ClientSaxParser(testFile.getAbsolutePath(), queue);
      XMLData data = new XMLData(queue, conn.getConnection());

      
      Thread t2 = new Thread(data);
      t2.start();
      Thread t1 = new Thread(parser);
      t1.start();
      
    } catch (SQLException | ClassNotFoundException | ParseException ex) {
      logger.error("SQL/ClassNotFound/ParseException", ex);
    }
  }
}
