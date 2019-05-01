package nl.hu.v1wac.template.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/DynamicServlet.do")
public class DynamicServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("username");

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<title>Dynamic Example</title>");
        out.println("<body>");
        out.println("<h2>Dynamic webapplication example</h2>");
        out.println("<h2>Hello " + name + "!</h2>");
        out.println("</body>");
        out.println("</html>");
    }
}