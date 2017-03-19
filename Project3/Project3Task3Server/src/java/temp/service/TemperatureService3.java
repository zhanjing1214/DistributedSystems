package temp.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhanjing
 */
@WebServlet(name = "TemperatureService3", urlPatterns = {"/TemperatureService3/*"})
public class TemperatureService3 extends HttpServlet {
    //temp records for sensor1
    static Stack<String> sensor1_st = new Stack();
    //temp records for sensor2
    static Stack<String> sensor2_st = new Stack();
    //public key for s1
    static BigInteger e1 = new BigInteger("65537");
    static BigInteger n1 = new BigInteger("2688520255179015026237478731436571621031218154515572968727588377065598663770912513333018006654248650656250913110874836607777966867106290192618336660849980956399732967369976281500270286450313199586861977623503348237855579434471251977653662553");
    //public key for s2
    static BigInteger e2 = new BigInteger("65537");
    static BigInteger n2 = new BigInteger("3377327302978002291107433340277921174658072226617639935915850494211665206881371542569295544217959391533224838918040006450951267452102275224765075567534720584260948941230043473303755275736138134129921285428767162606432396231528764021925639519");

    //return the temperature list or specific sensor last record
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Console: doGET visited");
        System.out.println(request.getHeader("Accept"));
        String records = "";

        String sensorID = (request.getPathInfo()).substring(1);
        //if request xml then return the form of xml
        if (request.getHeader("Accept").endsWith("xml")) {
            records += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<Message>\n";
            if (sensorID.equals("")) {
                for (int i = 0; i < sensor1_st.size(); i++) {
                    records += "<sensor>"
                            + "        <sensorID>" + sensor1_st.get(i).split(";")[0] + "</sensorID>\n"
                            + "        <timeStamp>" + sensor1_st.get(i).split(";")[1] + "</timeStamp>\n"
                            + "        <type>" + sensor1_st.get(i).split(";")[2] + "</type>\n"
                            + "        <temperature>" + sensor1_st.get(i).split(";")[3] + "</temperature>\n"
                            + "</sensor>";
                }
                for (int i = 0; i < sensor2_st.size(); i++) {
                    records += "<sensor>"
                            + "        <sensorID>" + sensor2_st.get(i).split(";")[0] + "</sensorID>\n"
                            + "        <timeStamp>" + sensor2_st.get(i).split(";")[1] + "</timeStamp>\n"
                            + "        <type>" + sensor2_st.get(i).split(";")[2] + "</type>\n"
                            + "        <temperature>" + sensor2_st.get(i).split(";")[3] + "</temperature>\n"
                            + "</sensor>";
                }
                
            } else if (sensorID.equalsIgnoreCase("1")) {
                if (!sensor1_st.isEmpty()) {
                    records += "<sensor>" 
                            + "        <sensorID>" + sensor1_st.peek().split(";")[0] + "</sensorID>\n"
                            + "        <timeStamp>" + sensor1_st.peek().split(";")[1] + "</timeStamp>\n" 
                            + "        <type>" + sensor1_st.peek().split(";")[2] + "</type>\n"
                            + "        <temperature>" + sensor1_st.peek().split(";")[3] + "</temperature>\n"
                            + "</sensor>";
                }
            } else if (sensorID.equalsIgnoreCase("2")) {
                if (!sensor2_st.isEmpty()) {
                     records += "<sensor>" 
                            + "        <sensorID>" + sensor2_st.peek().split(";")[0] + "</sensorID>\n"
                            + "        <timeStamp>" + sensor2_st.peek().split(";")[1] + "</timeStamp>\n" 
                            + "        <type>" + sensor2_st.peek().split(";")[2] + "</type>\n"
                            + "        <temperature>" + sensor2_st.peek().split(";")[3] + "</temperature>\n"
                            + "</sensor>";
                }
            }
            records += "</Message>";
        } else //else return plain text
         if (sensorID.equals("")) {
                for (int i = 0; i < sensor1_st.size(); i++) {
                    records = records + sensor1_st.get(i) + "\n";
                }
                for (int i = 0; i < sensor2_st.size(); i++) {
                    records = records + sensor2_st.get(i) + "\n";
                }
            } else if (sensorID.equalsIgnoreCase("1")) {
                if (!sensor1_st.isEmpty()) {
                    records = sensor1_st.peek();
                }
            } else if (sensorID.equalsIgnoreCase("2")) {
                if (!sensor2_st.isEmpty()) {
                    records = sensor2_st.peek();
                }
            }
        
        
        // Things went well so set the HTTP response code to 200 OK
        response.setStatus(200);
        // tell the client the type of the response
        response.setContentType("text/plain;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println(records);
    }

    // POST is used to create a new variable
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Console: doPost visited");
        // Read what the client has placed in the PUT data area
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String data = br.readLine();

        String[] variables = data.split(";");
        String sensorID = variables[1];
        String timeStamp = variables[2];
        String type = variables[3];
        String temperature = variables[4];
        String signature = variables[5];

        String data2 = sensorID + timeStamp + type + temperature;
        byte[] digest = null;
        try {
            byte[] dataByte = data2.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-1"); //hash with MD5
            md.update(dataByte);
            byte[] dataDigest = md.digest();

            int length = dataDigest.length + 1;
            digest = new byte[length];
            digest[0] = 0x1;
            for (int i = 0; i < dataDigest.length; i++) {
                digest[i + 1] = dataDigest[i];
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TemperatureService3.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean isNum = true;
        for (int i = 0; i < signature.length(); i++) {
            if (!Character.isDigit(signature.charAt(i))) {
                isNum = false;
            }
        }
        if (isNum) {
            BigInteger m = new BigInteger(signature);
            BigInteger c = null;
            if (sensorID.equalsIgnoreCase("1")) {
                c = m.modPow(e1, n1);
            } else if ((sensorID.equalsIgnoreCase("2"))) {
                c = m.modPow(e2, n2);
            }

            if (c.compareTo(new BigInteger(digest)) == 0) {
                response.setStatus(200);
                if (sensorID.equalsIgnoreCase("1")) {
                    sensor1_st.push(sensorID + ";" + timeStamp + ";" + type + ";" + temperature);
                } else if (sensorID.equalsIgnoreCase("2")) {
                    sensor2_st.push(sensorID + ";" + timeStamp + ";" + type + ";" + temperature);
                }
                return;
            }
        } else {
            response.setStatus(401);
            return;
        }
    }

    // PUT is used to create a new variable
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Console: doPut visited");
        // Read what the client has placed in the PUT data area
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String data = br.readLine();
        System.out.println(data);
        String[] variables = data.split(";");
        String sensorID = variables[1];
        String timeStamp = variables[2];
        String type = variables[3];
        String temperature = variables[4];
        String signature = variables[5];

        String data2 = sensorID + timeStamp + type + temperature;
        byte[] digest = null;
        try {
            byte[] dataByte = data2.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-1"); //hash with MD5
            md.update(dataByte);
            byte[] dataDigest = md.digest();

            int length = dataDigest.length + 1;
            digest = new byte[length];
            digest[0] = 0x1;
            for (int i = 0; i < dataDigest.length; i++) {
                digest[i + 1] = dataDigest[i];
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TemperatureService3.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean isNum = true;
        for (int i = 0; i < signature.length(); i++) {
            if (!Character.isDigit(signature.charAt(i))) {
                isNum = false;
            }
        }
        if (isNum) {
            BigInteger m = new BigInteger(signature);
            BigInteger c = null;
            if (sensorID.equalsIgnoreCase("1")) {
                c = m.modPow(e1, n1);
            } else if ((sensorID.equalsIgnoreCase("2"))) {
                c = m.modPow(e2, n2);
            }

            if (c.compareTo(new BigInteger(digest)) == 0) {
                response.setStatus(200);
                if (sensorID.equalsIgnoreCase("1")) {
                    sensor1_st.push(sensorID + ";" + timeStamp + ";" + type + ";" + temperature);
                } else if (sensorID.equalsIgnoreCase("2")) {
                    sensor2_st.push(sensorID + ";" + timeStamp + ";" + type + ";" + temperature);
                }
                return;
            }
        } else {
            response.setStatus(401);
            return;
        }
    }
}
