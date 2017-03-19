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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The servlet will compute the appropriate cryptographic (MD5 or SHA-1) hash value
 * from the text transmitted by the browser.
 * The hash values sent back to the browser should be displayed in two forms: 
 * as hexadecimal text and as base 64 notation.
 *
 * @author zhanjing
 */
@WebServlet(urlPatterns = {"/getComputeHashes"})
public class ComputeHashesServlet extends HttpServlet {


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
        //request the input string
        String str = request.getParameter("rawText");
        //request the selected hash function
        String fun = request.getParameter("function");
        
        //initialize the string for the hashes
        String base = null;
        String hex = null;
        
        //compute the hashes regarding the the selected function
        try {
            MessageDigest md = MessageDigest.getInstance(fun);
            md.update(str.getBytes());
            byte[] digesta=md.digest();
            base = javax.xml.bind.DatatypeConverter.printBase64Binary(digesta);
            hex = javax.xml.bind.DatatypeConverter.printHexBinary(digesta);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComputeHashesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        //set the attributes send back to the jsp
        request.setAttribute("function", fun);
        request.setAttribute("hex", hex);
        request.setAttribute("base", base);
        request.setAttribute("rawText", str);
        
        //forward to index.jsp
        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response);
    }
}
