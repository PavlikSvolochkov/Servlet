
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class TestServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

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
      Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
    }

    out.println("Файл создан.<br/>");
    System.out.println("\nTEST_FILE_NAME >>>> " + testFile.getAbsolutePath());

    BlockingQueue<Client> clientsQueue = new ArrayBlockingQueue(4096);

    try {
      DBConnection conn = new DBConnection();
      conn.connect();
      ClientSaxParser parser = new ClientSaxParser(testFile.getAbsolutePath(), clientsQueue);
      XMLData data = new XMLData(clientsQueue, conn.getConnection());
      data.setConn(conn.getConnection());

      parser.run();
      data.run();
    } catch (SQLException | ClassNotFoundException | ParseException ex) {
      ex.printStackTrace();
    }
  }
}
