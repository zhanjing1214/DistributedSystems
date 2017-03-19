/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
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
@WebServlet(name = "surveyServlet",urlPatterns = {"/surveyServlet", "/getResults", "/submit"})
public class surveyServlet extends HttpServlet {

    SurveyModel sm = null;
    
    /*
    initialize the surveyModel
    */
    @Override
    public void init() {
        sm = new SurveyModel();
    }
    
   

     /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ////determine the devices type an set to the right doctype
        String ua = request.getHeader("User-Agent");
        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }
        
        
        String nextView;
        String url = request.getServletPath();
        //show the results if requsted, otherwise forward to index.jsp
        if(url.equalsIgnoreCase("/getResults")) {
            request.setAttribute("result", sm.getResults());
            nextView = "getResults.jsp";
        } else {
            nextView = "index.jsp";
        }
        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ////determine the devices type an set to the right doctype
        String ua = request.getHeader("User-Agent");
        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }
        
        String nextView;
        String aw = request.getParameter("answer");
        //if submit an answer then forward to the submit page; otherwise forward to the index.jsp
        if (aw != null) {
            request.setAttribute("answer", sm.submitResult(aw));
            nextView = "submit.jsp";
        } else {
            nextView = "index.jsp";
        }
        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}
