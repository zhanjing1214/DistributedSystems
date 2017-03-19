package jzhan1.cmu.edu.project4android;


import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by zhanjing on 16/11/10.
 */

public class GetConvertResult {
    CurrencyConverter cc = null;

    public void search(String searchAmount, String searchFrom, String searchTo, CurrencyConverter cc) {
        this.cc = cc;
        new AsyncConvertSearch().execute(searchAmount, searchFrom, searchTo);
    }

    private class AsyncConvertSearch extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return search(urls[0], urls[1], urls[2]);
        }

        protected void onPostExecute(String result) {
            cc.resultReady(result);
        }

        /*
         * Search my web service to get convert result
         */
        private String search(String searchAmount, String searchFrom, String searchTo) {
            String response = "";
            try {

            /*
            * URL encode the searchTag, e.g. to encode spaces as %20
            *
            * There is no reason that UTF-8 would be unsupported.  It is the
            * standard encoding today.  So if it is not supported, we have
            * big problems, so don't catch the exception.
             */
                searchAmount = URLEncoder.encode(searchAmount, "UTF-8");
                searchFrom = URLEncoder.encode(searchFrom, "UTF-8");
                searchTo = URLEncoder.encode(searchTo, "UTF-8");



                // Create a URL for the convert result
                String exchangeRatesURL= "https://glacial-plains-94265.herokuapp.com/MyAppServlet?searchAmount=" + searchAmount + "&searchFrom=" + searchFrom + "&searchTo=" + searchTo;
                System.out.println(exchangeRatesURL);
                // Fetch the page
                response = fetch(exchangeRatesURL);
                System.out.println(response);
            } catch (UnsupportedEncodingException ex) {
                System.out.println("error");
            }
            return response;
        }

        /**
         * this method fetch the result by connecting the url using post method
         * referenced from Interesting Picture APP
         */
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
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput( true );
                conn.setInstanceFollowRedirects( false );
                conn.setRequestMethod( "POST" );
                conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty( "charset", "utf-8");
                conn.setUseCaches( false );
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                // Read each line of "in" until done, adding each to "response"
                while ((str = in.readLine()) != null) {
                    // str is one line of text readLine() strips newline characters
                    response += str;
                }
                in.close();
            } catch (IOException e) {
                System.out.println("There is an exception");
            }
            //parse return json
            int left = response.indexOf(":");
            int right = response.indexOf("}");
            String result = response.substring(left+1, right);
            return result;

        }
    }
}
