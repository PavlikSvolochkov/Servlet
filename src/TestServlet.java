
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

public class TestServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    doPost(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");

    PrintWriter out = response.getWriter();
    out.println("Hello<br/>");

    boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
    if (!isMultipartContent) {
      out.println("You are not trying to upload<br/>");
      return;
    }
    out.println("You are trying to upload<br/>");

    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    try {
      List<FileItem> fields = upload.parseRequest(request);
      out.println("Number of fields: " + fields.size() + "<br/><br/>");

      Iterator<FileItem> it = fields.iterator();

      if (!it.hasNext()) {
        out.println("No fields found");
        return;
      }

      FileItem fileItem = it.next();
      //  Сохраняем файл в catalina.base\webapps\data\ 
      File testFile = new File(System.getProperty("catalina.base") + "\\webapps\\data\\" + fileItem.getName());
      testFile.createNewFile();
      FileWriter fw = new FileWriter(testFile);
      fw.append(fileItem.getString());
      fw.flush();
    } catch (FileUploadException e) {
      e.printStackTrace();
    }
  }
}
