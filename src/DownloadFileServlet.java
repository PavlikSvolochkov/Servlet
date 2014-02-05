import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DownloadFileServlet")
public class DownloadFileServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  String fileLocation = null;
  final String[] contentTypes = { "xml", "text/xml" };

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Object reqObject = request.getParameter("filename");
    fileLocation = reqObject.toString();
    System.out.println(fileLocation);
    String fileName = (String) reqObject;
    
    if (request.getParameter("upload") != null)
      //do something
    if (request.getParameter("download") != null)
      //do something

    if (reqObject != null) {
      String contentType = fileName.split("\\.")[1];

      File file = new File(fileLocation);

      response.setContentType(contentType);
      response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
      response.setContentLength((int) file.length());

      ServletOutputStream servletOutputStream = response.getOutputStream();
      BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

      int bytesRead = bufferedInputStream.read();
      while (bytesRead != -1) {
        servletOutputStream.write(bytesRead);
        bytesRead = bufferedInputStream.read();
      }

      if (servletOutputStream != null)
        servletOutputStream.close();
      if (bufferedInputStream != null)
        bufferedInputStream.close();
    }
  }

  private String getContentType(String fileType) {
    String returnType = null;
    for (int i = 0; i < contentTypes.length; i++) {
      if (fileType.equals(contentTypes[i]))
        returnType = contentTypes[i];
    }
    return returnType;
  }
}
