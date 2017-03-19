/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
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

    @Override
    public void init() {
        cml = new ConverterModel();
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
        // get the search parameter
        String searchAmount = request.getParameter("searchAmount");
        String searchFrom = request.getParameter("searchFrom");
        String searchTo = request.getParameter("searchTo");
//
//        // determine what type of device our user is
//        String ua = request.getHeader("User-Agent");
//
//        boolean mobile;
//        // prepare the appropriate DOCTYPE for the view pages
//        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
//            mobile = true;
//            /*
//             * This is the latest XHTML Mobile doctype. To see the difference it
//             * makes, comment it out so that a default desktop doctype is used
//             * and view on an Android or iPhone.
//             */
//            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
//        } else {
//            mobile = false;
//            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
//        }
//
////        String nextView;
//        /*
//         * Check if the search parameter is present.
//         * If not, then give the user instructions and prompt for a search string.
//         * If there is a search parameter, then do the search and return the result.
//         */
        //return json
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("{result:");
        if (searchAmount != null) {
            cml.doRatesSearch(searchAmount, searchFrom, searchTo);
            sb.append(cml.getResult());
        } else {
            sb.append("");
        }
        sb.append("}");
        out.print(sb.toString());
        out.flush();
    }
}
