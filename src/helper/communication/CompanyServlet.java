package helper.communication;
import javax.servlet.*;
import javax.servlet.http.*;

import object.Company;

import java.io.*;


public class CompanyServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Company company = new Company("test2");
        String id = company.getId();

        PrintWriter out = response.getWriter();
        out.println(id);
    }
}
