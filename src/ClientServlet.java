
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientServlet extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet ClientServlet</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet ClientServlet at " + request.getContextPath() + "</h1>");
//----------------------------------------------------------------------------------------------------------------------
      out.println("Add new Client in DataBase:");
      out.println("<form name='addClient' action=''>");
      out.println("<input type='text' name='name' value='name'/>");
      out.println("<br/>");
      out.println("<input type='text' name='surname' value='surname'/>");
      out.println("<br/>");
      out.println("<input type='text' name='dateofbirth' value='dateofbirth'/>");
      out.println("<br/>");
      out.println("<input type='submit' name='del' value='AddClient'/>");
//      out.println("<br/>");
//      out.println("<br/>");
      out.println("<br/>----------------------------------------------------------------------------------------------------------------------");
      out.println("<br/>");
//----------------------------------------------------------------------------------------------------------------------
      out.println("Update Client in DataBase:");
      out.println("<br/>");
      out.println("<form name='updClient' action=''>");
      out.println("<input type='text' name='add' value='idClient'/>");
      out.println("<br/>");
      out.println("<input type='text' name='add' value='name'/>");
      out.println("<br/>");
      out.println("<input type='text' name='upd' value='surname'/>");
      out.println("<br/>");
      out.println("<input type='text' name='del' value='dateofbirth'/>");
      out.println("<br/>");
      out.println("<input type='submit' name='del' value='UpdClient'/>");
//      out.println("<br/>");
//      out.println("<br/>");
      out.println("<br/>----------------------------------------------------------------------------------------------------------------------");
      out.println("<br/>");
//----------------------------------------------------------------------------------------------------------------------
      out.println("Delete Client from DataBase:");
      out.println("<br/>");
      out.println("<form name='delClient' action=''>");
      out.println("<input type='text' name='add' value='idClient'/>");
//      out.println("<br/>");
//      out.println("<input type='text' name='upd' value='UpdClient'/>");
//      out.println("<br/>");
//      out.println("<input type='text' name='del' value='DelClient'/>");
      out.println("<br/>");
      out.println("<input type='submit' name='del' value='DelClient'/>");
//      out.println("<br/>");
//      out.println("<br/>");
      out.println("<br/>----------------------------------------------------------------------------------------------------------------------");
      out.println("<br/>");
//----------------------------------------------------------------------------------------------------------------------      
      out.println("</form>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }
}
