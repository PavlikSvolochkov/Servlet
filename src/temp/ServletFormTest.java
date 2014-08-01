package temp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletFormTest extends HttpServlet {

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
    performTask(req, resp);
  }

  private void performTask(HttpServletRequest req, HttpServletResponse resp) {
    RequestOutput.generate(resp, req);
  }
}

class RequestOutput {

  public static void generate(HttpServletResponse resp, HttpServletRequest req) {
    try {

      String name, value;
      resp.setContentType("text/html; charset=UTF-8");
      PrintWriter out = resp.getWriter();

      out.print("<HTML><HEAD>");
      out.print("<TITLE>Результат</TITLE>");
      out.print("</HEAD><BODY>");
      out.print("<TABLE BORDER=3>");
      Enumeration names = req.getParameterNames();
      while (names.hasMoreElements()) {
        name = (String) names.nextElement();
        value = req.getParameterValues(name)[0];
        /*
         name = new String(name.getBytes("ISO-8859-1"), "utf-8");
         value = new String(value.getBytes("ISO-8859-1"), "utf-8");
         */
        out.print("<TR>");
        out.print("<TD>" + name + "</TD>");
        out.print("<TD>" + value + "</TD>");
        out.print("</TR>");
      }
      out.print("</TABLE></BODY></HTML>");
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
