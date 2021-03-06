/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zhanjing
 */
public class TemperatureClient1 {
    //private key for s1
    static BigInteger e1 = new BigInteger("65537");
    static BigInteger d1 = new BigInteger("339177647280468990599683753475404338964037287357290649639740920420195763493261892674937712727426153831055473238029100340967145378283022484846784794546119352371446685199413453480215164979267671668216248690393620864946715883011485526549108913");
    static BigInteger n1 = new BigInteger("2688520255179015026237478731436571621031218154515572968727588377065598663770912513333018006654248650656250913110874836607777966867106290192618336660849980956399732967369976281500270286450313199586861977623503348237855579434471251977653662553");
    
    //private key for s2
    static BigInteger e2 = new BigInteger("65537");
    static BigInteger d2 = new BigInteger("3056791181023637973993616177812006199813736824485077865613630525735894915491742310306893873634385114173311225263612601468357849028784296549037885481727436873247487416385339479139844441975358720061511138956514526329810536684170025186041253009");
    static BigInteger n2 = new BigInteger("3377327302978002291107433340277921174658072226617639935915850494211665206881371542569295544217959391533224838918040006450951267452102275224765075567534720584260948941230043473303755275736138134129921285428767162606432396231528764021925639519");
    
    public static void main(String[] args) {
        //sensor1 call highTemperature
        String s1 = "1", d1 = new Date().toString(), t1 = "Celsius", temp1 = "33.2";
        String signature1 = generateSignature(s1, d1, t1, temp1);
        System.out.println(highTemperature(s1, d1, t1, temp1, signature1));
        //sensor2 call lowTemperature
        String s2 = "2", d2 = new Date().toString(), t2 = "Celsius", temp2 = "20.5";
        String signature2 = generateSignature(s2, d2, t2, temp2);
        System.out.println(lowTemperature(s2, d2, t2, temp2, signature2));
        //sensor1 call highTemperature
        String s3 = "1", d3 = new Date().toString(), t3 = "Celsius", temp3 = "32.5";
        String signature3 = generateSignature(s3, d3, t3, temp3);
        System.out.println(highTemperature(s3, d3, t3, temp3, signature3));
        //sensor1 call highTemperature with invalid signature
        String s4 = "1", d4 = new Date().toString(), t4 = "Celsius", temp4 = "34.5";
        String signature4 = "wrongsignature";
        System.out.println(highTemperature(s4, d4, t4, temp4, signature4));
        //get all temperature recod
        System.out.println(getTemperatures());
        //get last temperature record;
        System.out.println(getLastTemperature("1"));
    }
    
    /**
     * this method generates the signature for the data using private key
     * @param sensorID sensor id
     * @param date the date
     * @param type degree type
     * @param temp temperature
     * @return the signature
     */
    public static String generateSignature(String sensorID, String date, String type, String temp) {
        String data = sensorID + date + type + temp;
        byte[] digest = null;
        BigInteger signature = null;
        try {
            byte[] dataByte = data.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-1"); //hash with MD5
            md.update(dataByte);
            byte[] dataDigest = md.digest();
            int length = dataDigest.length + 1;
            digest = new byte[length]; 
            //copy the data bytes to a n+1 array and set 0'th byte as 0
            digest[0] = 0x1;
            for(int i = 0; i < dataDigest.length; i ++) {
                digest[i + 1] = dataDigest[i];
            }
            BigInteger m = new BigInteger(digest);
            signature = m.modPow(d1, n1); 
            
            if(sensorID.equalsIgnoreCase("1")) {
               signature = m.modPow(d1, n1); 
            } else if(sensorID.equalsIgnoreCase("2")) {
               signature = m.modPow(d2, n2);
            }
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TemperatureClient1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return signature.toString();
    }

    private static String getLastTemperature(java.lang.String sensorID) {
        andrew.cmu.edu.jzhan1.TemperatureService_Service service = new andrew.cmu.edu.jzhan1.TemperatureService_Service();
        andrew.cmu.edu.jzhan1.TemperatureService port = service.getTemperatureServicePort();
        return port.getLastTemperature(sensorID);
    }

    private static String getTemperatures() {
        andrew.cmu.edu.jzhan1.TemperatureService_Service service = new andrew.cmu.edu.jzhan1.TemperatureService_Service();
        andrew.cmu.edu.jzhan1.TemperatureService port = service.getTemperatureServicePort();
        return port.getTemperatures();
    }

    private static String highTemperature(java.lang.String sensorID, java.lang.String timeStamp, java.lang.String type, java.lang.String temperature, java.lang.String signature) {
        andrew.cmu.edu.jzhan1.TemperatureService_Service service = new andrew.cmu.edu.jzhan1.TemperatureService_Service();
        andrew.cmu.edu.jzhan1.TemperatureService port = service.getTemperatureServicePort();
        return port.highTemperature(sensorID, timeStamp, type, temperature, signature);
    }

    private static String lowTemperature(java.lang.String sensorID, java.lang.String timeStamp, java.lang.String type, java.lang.String temperature, java.lang.String signature) {
        andrew.cmu.edu.jzhan1.TemperatureService_Service service = new andrew.cmu.edu.jzhan1.TemperatureService_Service();
        andrew.cmu.edu.jzhan1.TemperatureService port = service.getTemperatureServicePort();
        return port.lowTemperature(sensorID, timeStamp, type, temperature, signature);
    }
    
    
    
}
