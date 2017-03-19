
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author zhanjing
 */
public class PasswordHash {
    /**
     * this method generates a 16 bites salt randomly
     * @return the salt
     */
    public byte[] generateSalt() {
        Random r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);
        return salt;
    }
    
    /**
     * this method hash a string with the salt concatenated together using MD5
     * @param salt the salt to concatenated
     * @param str the string to hash
     * @return the hash
     */
    public byte[] hash(byte[] salt, String str){
        byte[] hash = null;
        try {
            byte[] strByte = str.getBytes();
            int length = salt.length + strByte.length;
            byte[] result = new byte[length]; //concatenate the salt and string and turn into byte[]
            for(int i = 0; i < strByte.length; i ++) {
                result[i] = strByte[i];
            }
            for(int j = 0; j < salt.length; j ++) {
                result[j + strByte.length] = salt[j];
            }
            MessageDigest md = MessageDigest.getInstance("MD5"); //hash with MD5
            md.update(result);
            hash = md.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordHash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hash;
    }
    
    /**
     * this method checks whether two hashes are equal
     * @param b1 input hash1 
     * @param b2 input hash2
     * @return true if they are equal; false if not
     */
    public boolean isEqual(byte[] b1, byte[] b2) {
        boolean isequal = true;
        if(b1.length != b2.length) {
            isequal = false;
        } else {
            for(int i = 0; i < b1.length; i ++) {
                if(b1[i] != b2[i]) {
                    isequal = false;
                    break;
                }
            }
        }
        return isequal;
    }
}
