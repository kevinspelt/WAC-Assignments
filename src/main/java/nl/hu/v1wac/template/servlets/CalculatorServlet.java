package nl.hu.v1wac.template.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/CalculatorServlet.do")
public class CalculatorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        float numberOne = Float.parseFloat(req.getParameter("numberOne"));
        float numberTwo = Float.parseFloat(req.getParameter("numberTwo"));

        String formula = req.getParameter("formula");

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        if(formula.equals("addition")) {
            out.println(numberOne + numberTwo);
        } if(formula.equals("deduction")) {
            out.println(numberOne - numberTwo);
        } if(formula.equals("multiplication")) {
            out.println(numberOne * numberTwo);
        } if(formula.equals("division")) {
            out.println(numberOne / numberTwo);
        }
    }
}
