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
@WebServlet(name = "FlagDescriptionServlet",
        urlPatterns = {"/getFlagDescriptionServlet", "/result"})
public class FlagDescriptionServlet extends HttpServlet {
    //model for this application
    FlagDescriptionModel fdm = null;
    
    // Initiate this servlet by instantiating the model that it will use
    @Override
    public void init() {
        fdm = new FlagDescriptionModel();
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
        //get selected country
        String search = request.getParameter("selected_country");
        String nextView;
        /*
         * Check if the search parameter is present.
         * If not, then give the user instructions and prompt for a search page
         * If there is a search parameter, then do the search and return the result.
         */
        if (search!= null) {
            fdm.doFlagSearch(search);
            //pass the flag pic url
            request.setAttribute("flagURL",fdm.getFlagURL());
            //pass the flag description 
            request.setAttribute("flagDesc", fdm.getFlagDescription());
            //pass the country name
            request.setAttribute("country", fdm.getCountry());
            nextView = "result.jsp";
        } else {
            // no search parameter so choose the prompt view
            nextView = "prompt.jsp";
        }
        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}
