
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.xml.stream.XMLStreamException;
import org.apache.log4j.Logger;

public class DownloadServlet extends HttpServlet {

  static Logger logger = Logger.getLogger(XMLData.class.getName());

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    DBConnection conn = new DBConnection();
    
    try {
      conn.connect();
    } catch (SQLException | ClassNotFoundException ex) {
      logger.error("ERROR CONNECTION");
    }

    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();
    String fileName = "clients.xml";
    String filePath = "/TEMP_DATA/";
    response.setContentType("APPLICATION/OCTET-STREAM");
    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

    File inputFile = new File(filePath + fileName);

    if (!inputFile.isFile()) {
      logger.info("Creating inputFile");
      inputFile.createNewFile();
      logger.info("inputFiel is created");
    }

    FileInputStream fileInputStream = new FileInputStream(inputFile);
    
    try {
      DataXML dataXML = new DataXML(filePath, fileName);
      dataXML.setConnection(conn.getConnection());
      dataXML.build();
      dataXML.toXML();
    } catch (XMLStreamException | SQLException | ClassNotFoundException ex) {
      logger.error("ERROR BUILD XML");
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
