/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author zhanjing
 */
@WebService(serviceName = "TemeratureService")
public class TemeratureService2 {
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

    //parseXML and call the corresponding method
    @WebMethod(operationName = "parseXML")
    public String parseXML(@WebParam(name = "XMLmessage") String XMLmessage) {
        String result = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(XMLmessage.getBytes());
            org.w3c.dom.Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();
            Element e = document.getDocumentElement();
            String task = e.getElementsByTagName("Task").item(0).getTextContent();
            if (task.equals("highTemperature") || task.equals("lowTemperature")) {
                String sensorID = e.getElementsByTagName("sensorID").item(0).getTextContent();
                String timeStamp = e.getElementsByTagName("timeStamp").item(0).getTextContent();
                String type = e.getElementsByTagName("type").item(0).getTextContent();
                String temperature = e.getElementsByTagName("temperature").item(0).getTextContent();
                String signature = e.getElementsByTagName("signature").item(0).getTextContent();
                if(task.equals("highTemperature")) {
                    result = highTemperature(sensorID, timeStamp, type, temperature, signature);
                } else {
                    result = lowTemperature(sensorID, timeStamp, type, temperature, signature);
                }
            } else if(task.equals("getTemperatures")) {
                result = getTemperatures();
            } else if(task.equals("getLastTemperature")) {
                String sensorID = e.getElementsByTagName("sensorID").item(0).getTextContent();
                result = getLastTemperature(sensorID);
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TemeratureService2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TemeratureService2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TemeratureService2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // highTemperature method: Checks signature and if valid, saves this
    // temperature report.
    @WebMethod(operationName = "highTemperature")
    public String highTemperature(@WebParam(name = "sensorID") String sensorID,
            @WebParam(name = "timeStamp") String timeStamp,
            @WebParam(name = "type") String type,
            @WebParam(name = "temperature") String temperature,
            @WebParam(name = "signature") String signature) {

        String data = sensorID + timeStamp + type + temperature;
        byte[] digest = null;
        try {
            byte[] dataByte = data.getBytes();
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
            Logger.getLogger(TemeratureService2.class.getName()).log(Level.SEVERE, null, ex);
        }
        String feedback = "Invalid signature!";
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
                
                if (sensorID.equalsIgnoreCase("1")) {
                    sensor1_st.push(data);
                    feedback = "Successfully transfered high temperature of sensor1!";
                } else if (sensorID.equalsIgnoreCase("2")) {
                    sensor2_st.push(data);
                    feedback = "Successfully transfered high temperature of sensor2!";
                }
            }
        }
        return feedback;
    }

    // lowTemperature method: Checks signature and if valid, saves this
    // temperature report.
    @WebMethod(operationName = "lowTemperature")
    public String lowTemperature(@WebParam(name = "sensorID") String sensorID,
            @WebParam(name = "timeStamp") String timeStamp,
            @WebParam(name = "type") String type,
            @WebParam(name = "temperature") String temperature,
            @WebParam(name = "signature") String signature) {
        String data = sensorID + timeStamp + type + temperature;
        byte[] digest = null;
        try {
            byte[] dataByte = data.getBytes();
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
            Logger.getLogger(TemeratureService2.class.getName()).log(Level.SEVERE, null, ex);
        }
        String feedback = "Invalid signature!";
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
                if (sensorID.equalsIgnoreCase("1")) {
                    sensor1_st.push(data);
                    feedback = "Successfully transfered low temperature of sensor1!";
                } else if (sensorID.equalsIgnoreCase("2")) {
                    sensor2_st.push(data);
                    feedback = "Successfully transfered low temperature of sensor2!";
                }
            }
        }
        return feedback;
    }

    // getTemperatures method: returns a String representation of all temperatures
    // recorded.
    @WebMethod(operationName = "getTemperatures")
    public String getTemperatures() {
        String records = "";
        for (int i = 0; i < sensor1_st.size(); i++) {
            records = records + sensor1_st.get(i) + "\n";
        }
        for (int i = 0; i < sensor2_st.size(); i++) {
            records = records + sensor2_st.get(i) + "\n";
        }
        return records;
    }

    // getLastTemperature method: returns the last temperature of a particular
    // sensor (sensor 1 or sensor 2)
    @WebMethod(operationName = "getLastTemperature")
    public String getLastTemperature(@WebParam(name = "sensorID") String sensorID) {
        String record = null;
        if (sensorID.equalsIgnoreCase("1")) {
            if (!sensor1_st.isEmpty()) {
                record = sensor1_st.peek();
            }
        } else if (sensorID.equalsIgnoreCase("2")) {
            if (!sensor2_st.isEmpty()) {
                record = sensor2_st.peek();
            }
        }
        return record;
    }
}
