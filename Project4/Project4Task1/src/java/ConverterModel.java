////

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose Licrense Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author zhanjing
 */
public class ConverterModel {

    private String amount; // amount to convert
    private String from; //from currency
    private String to; //to currency
    private String result; //convert result

    public String getResult() {
        return result;
    }

    public String getAmount() {
        return amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void doRatesSearch(String searchAmount, String searchFrom, String searchTo) {
        
        try {
            amount = searchAmount;
            from = searchFrom;
            to = searchTo;
            /*
            * URL encode the searchTag, e.g. to encode spaces as %20
            *
            * There is no reason that UTF-8 would be unsupported.  It is the
            * standard encoding today.  So if it is not supported, we have
            * big problems, so don't catch the exception.
             */
            amount = URLEncoder.encode(amount, "UTF-8");
            from = URLEncoder.encode(from, "UTF-8");
            to = URLEncoder.encode(to, "UTF-8");

            String response = "";

            // Create a URL for the page get currency rate
            String exchangeRatesURL
                    = "http://api.fixer.io/latest?symbols=" + from + "," + to;

            // Fetch the page
            response = fetch(exchangeRatesURL);

            System.out.println(response);

            //get the rate of the two country and compute convert result
            boolean isEqual = (from.equalsIgnoreCase(to));
            if (!isEqual) {
                if (from.equalsIgnoreCase("EUR")) {
                    int cutLeft = response.indexOf(to) + 5;
                    int cutRight = response.indexOf("}");
                    Double toRate = Double.parseDouble(response.substring(cutLeft, cutRight));
                    result = (Double.parseDouble(amount) * toRate) + "";
                } else if (to.equalsIgnoreCase("EUR")) {
                    int cutLeft = response.indexOf(from) + 5;
                    int cutRight = response.indexOf("}");
                    Double toRate = Double.parseDouble(response.substring(cutLeft, cutRight));
                    result = (Double.parseDouble(amount) / toRate) + "";
                } else {
                    int fromLeft = response.indexOf(from) + 5;
                    int toLeft = response.indexOf(to) + 5;
                    Double fromRate = Double.parseDouble(response.substring(fromLeft, fromLeft + 6));
                    Double toRate = Double.parseDouble(response.substring(toLeft, toLeft + 6));
                    result = (Double.parseDouble(amount) * toRate / fromRate) + "";
                }
            } else {
                result = amount;
            }
            System.out.println("result: " + result);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConverterModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //referenced from Interesting Picture
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which 
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");
        }
        return response;
    }
}
