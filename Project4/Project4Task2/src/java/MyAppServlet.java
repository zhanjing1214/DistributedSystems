/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhanjing
 */
@WebServlet(urlPatterns = "/MyAppServlet")
public class MyAppServlet extends HttpServlet {

    ConverterModel cml;
    MongoDB md;

    @Override
    public void init() {
        cml = new ConverterModel();
        md = new MongoDB();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ua = request.getHeader("User-Agent");
        String searchAmount = request.getParameter("searchAmount");
        String searchFrom = request.getParameter("searchFrom");
        String searchTo = request.getParameter("searchTo");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("{result:");
        String result = "";
        if (searchAmount != null) {
            // use model to do the search and choose the result view
            cml.doRatesSearch(searchAmount, searchFrom, searchTo);
            result = cml.getResult();
            sb.append(result + "}");
            out.write(sb.toString());
            out.close();
            md.insert(searchAmount, searchFrom, searchTo, cml.getFromRate(), cml.getToRate(), cml.getResult(), ua, cml.getDate());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String pw = request.getParameter("pw");
        String nextView;
        if (id.equals("jzhan1") && pw.equals("1a2b1c4d")) {
            // use model to do the search and choose the result view
            md.getDashboard();
            request.setAttribute("all", md.getDashboard()[0]);
            request.setAttribute("a1", md.getDashboard()[1]);
            request.setAttribute("a2", md.getDashboard()[2]);
            request.setAttribute("a3", md.getDashboard()[3]);
            request.setAttribute("a4", md.getDashboard()[4]);
            request.setAttribute("a5", md.getDashboard()[5]);
            nextView = "result.jsp";
        } else {
            request.setAttribute("error", "Incorrect id/pw");
            nextView = "login.jsp";
        }
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }

}
