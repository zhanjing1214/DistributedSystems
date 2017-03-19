
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhanjing
 */
public class FlagDescriptionModel {
    private String countryCode; // The code of the desired flag
    private String flagURL; //flag picture url
    private String flagDescription;// flag description
    private String country;//flag country
    
    /**
     * this method screen scrap the flag URL, flag description and country on cia website
     * @param code the country code selected
     */
    public void doFlagSearch(String code)  {
        //if no code selected, then return
        if(code.length()<8) {
            flagURL = null;
            flagDescription = null;
            country = null;
            return;
        }
        //take the two letters country code
        countryCode = code.substring(8);
        // Create a URL for the page to be screen scraped
        String ciaURL =
                "https://www.cia.gov/library/publications/the-world-factbook/geos/"
                + countryCode;
        // Fetch the page html
        String response = fetch(ciaURL);
        
        // Search the page to find the flag URL, flag description, flag country
        int flagLeft = response.indexOf("FlagModal\"><img src=\"../");
        //if no result, then return
        if (flagLeft < 0) {
            flagURL = countryCode = flagDescription = null;
            return;
        }
        int descLeft = response.indexOf("flag_description_text\">");
        int countryLeft = response.indexOf("region_name1 countryName \">");
        //find the left start point
        flagLeft += "FlagModal\"><img src=\"../".length();
        descLeft += "flag_description_text\">".length();
        countryLeft += "region_name1 countryName \">".length();
        //find right ending point
        int flagRight = response.indexOf("\">", flagLeft);
        int descRight = response.indexOf("</span>", descLeft);
        int countryRight = response.indexOf("</span>", countryLeft);
        //set the url, decription and country
        flagURL = "https://www.cia.gov/library/publications/the-world-factbook/"+response.substring(flagLeft, flagRight);
        flagDescription = response.substring(descLeft , descRight);
        country = response.substring(countryLeft, countryRight);     
    }
    
    /**
     * Make an HTTP request to a given URL
     * fetch urlString The URL of the request
     * @param urlString input url
     * @return A string of the response from the HTTP GET. This is identical
     */
    public String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            //Create an HttpURLConnection
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
            // Do something reasonable. This is left for students to do.
        }
        return response;
    }
    
    public String getCountryCode() {
        return (countryCode);
    }
    
    public String getFlagURL() {
        return (flagURL);
    }
    
    public String getFlagDescription() {
        return (flagDescription);
    }
    
    public String getCountry() {
        return (country);
    }
}
