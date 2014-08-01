package temp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletTest extends HttpServlet {

  // счётчик подключений к сервлету 
  private int count = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
    performTask(req, resp);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
    performTask(req, resp);
  }

  private void performTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

    try {
      // установка MIME-типа содержания ответа 
      resp.setContentType("text/html; charset=Windows-1251");

      // поток для данных ответа 
      PrintWriter out = resp.getWriter();
      count = ClickOutput.printClick(out, count);
      
      //обращение к классу бизнес-логики 
      RequestInfo.printToBrowser(out, req);

      // закрытие потока 
      out.close();
    } catch (UnsupportedEncodingException e) {
      System.err.print("UnsupportedEncoding");
    } catch (IOException e) {
      System.err.print("IOErroR");
    }
  }
}

class RequestInfo {

  static String br = "<br>";

  public static void printToBrowser(PrintWriter out, HttpServletRequest req) {
    out.println("Method: " + req.getMethod());
    out.print(br + "Request URI: " + req.getRequestURI());
    out.print(br + "Protocol: " + req.getProtocol());
    out.print(br + "PathInfo: " + req.getPathInfo());
    out.print(br + "Remote Address: " + req.getRemoteAddr());
    // извлечение имен заголовочной информации запроса 
    Enumeration e = req.getHeaderNames();
    out.print(br + "Header INFO: ");
    while (e.hasMoreElements()) {
      String name = (String) e.nextElement();
      String value = req.getHeader(name);
      out.print(br + name + " = " + value);
    }
  }
}

class ClickOutput {

  public static int printClick(PrintWriter out, int count) {
    out.print(++count + " -е обращение." + "<br>");
    return count;
  }

}
