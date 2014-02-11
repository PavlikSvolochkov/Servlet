
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class TestServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

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

      System.out.println("\n------------------------------\nTEST_FILE_NAME >>>> " + testFile.getName()
              + "\n------------------------------\n");

      DBConnection conn = new DBConnection();
      conn.connect();
      out.println("Пытаемся записать данные в файл из БД...<br/>");
      DataXML dataXML = new DataXML(System.getProperty("catalina.base") + "\\webapps\\data\\", fileItem.getName());
      dataXML.setConnection(conn.getConnection());
      dataXML.build();
      dataXML.toXML();
      out.println("Вроде получилось. =)<br/>");
      out.println("Даныне из файла:<br/><br/>");
      out.append(fileItem.getString());

      ClientSaxParser saxParser = new ClientSaxParser(System.getProperty("catalina.base") + "\\webapps\\data\\" + fileItem.getName());
      List<Client> clientList = saxParser.getSyncClientList();
      System.out.println("\n------------------------------\nCLIENT_LIST >>>> " + clientList.toString()
              + "\n------------------------------\n");

      conn.close();

    } catch (XMLStreamException | SQLException | ClassNotFoundException | FileUploadException ex) {
      ex.printStackTrace();
    }
  }
}
