
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ServletDownloadDemo extends HttpServlet {

  private static final int BYTES_DOWNLOAD = 1024 * 1024 * 40;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

    response.setContentType("text/xml");
    response.setHeader("Content-Disposition", "attachment;filename=clients_" + dateFormat.format(date) + ".xml");
    ServletContext ctx = getServletContext();
    ctx.getAttributeNames();
    System.out.println(ctx.getAttribute("downFile"));

//----------------------------------------------------------------------------------------------------------------------
    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    try {
      PrintWriter out = response.getWriter();
      List<FileItem> fields = upload.parseRequest(request);
      Iterator<FileItem> it = fields.iterator();

      if (!it.hasNext()) {
        //out.println("Файл не указан");
        return;
      }
      FileItem fileItem = it.next();
      System.out.println(fileItem.getName());

      InputStream is = ctx.getResourceAsStream("/TEMP_DATA/" + fileItem.getName());

      int read = 0;
      byte[] bytes = new byte[BYTES_DOWNLOAD];
      
      OutputStream os = response.getOutputStream();
      
      while ((read = is.read()) != -1) {
        os.write(bytes, 0, read);
        
      }
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + os.toString());
      
      os.flush();
      os.close();
    } catch (FileUploadException e) {
      e.printStackTrace();
    }
//----------------------------------------------------------------------------------------------------------------------
  }
}
