
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.xml.stream.XMLStreamException;

public class DownloadServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    DBConnection connection = new DBConnection();
    try {
      connection.connect();
    } catch (SQLException | ClassNotFoundException ex) {
      Logger.getLogger(DownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
    }

    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();
    String filename = "clients.xml";
    String filepath = "/TEMP_DATA/";
    response.setContentType("APPLICATION/OCTET-STREAM");
    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

    FileInputStream fileInputStream = new FileInputStream(filepath + filename);
    try {
      DataXML dataXML = new DataXML(filepath, filename);
      dataXML.conn = connection.getConnection();
      dataXML.build();
      dataXML.toXML();
    } catch (XMLStreamException | SQLException | ClassNotFoundException ex) {
      Logger.getLogger(DownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
    }

    int i;
    while ((i = fileInputStream.read()) != -1) {
      out.write(i);
    }
    fileInputStream.close();
    out.close();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  }
}
