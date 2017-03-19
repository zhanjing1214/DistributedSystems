
import java.net.*;
import java.io.*;
import java.math.BigInteger;
import java.util.Random;

public class TCPSpyUsingTEAandPasswordsAndRSA {
    static BigInteger e = new BigInteger("65537");
    static BigInteger n = new BigInteger("3505705840777118070941314405624091192167066396528600220814390368457878138942988620357892400138236107062676458227242859055026442120634919004896935435679693577881456373416505242211730107285475860708053432268945454344370847417594414883111667761");
            
    public static void main(String args[]) {
        // arguments supply message and hostname
        Socket s = null;
        try {
            int serverPort = 7896;
            s = new Socket(args[0], serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
            
            Random rnd = new Random();
            BigInteger key;
            do {
                key = new BigInteger(16*8, rnd);
            } while(key.toByteArray().length != 16);
            BigInteger encryptKey = key.modPow(e, n);
            out.writeUTF(encryptKey.toString());
            
            TEA tea = new TEA(key.toByteArray());
            //id
            System.out.println(in.readUTF());
            String ID = ""; 
            int i = 0;
            while(ID.equals("")){ 
                if(i != 0) {
                    System.out.println("value missing, please retype it.");
                }
                ID = typed.readLine();
                i ++;
            }
            byte[] encryptID = tea.encrypt(ID.getBytes());
            out.write(encryptID);
            //pw
            System.out.println(in.readUTF());
            String pw= "";
            i = 0;
            while(pw.equals("")){ 
                if(i != 0) {
                    System.out.println("value missing, please retype it.");
                }
                pw = typed.readLine();
                i ++;
            }
            byte[] encryptPw = tea.encrypt(pw.getBytes());
            out.write(encryptPw);
            //location
            System.out.println(in.readUTF());
            String location = "";
            i = 0;
            while(location.equals("")){ 
                if(i != 0) {
                    System.out.println("value missing, please retype it.");
                }
                location = typed.readLine();
                i ++;
            }
            byte[] encryptLocation = tea.encrypt(location.getBytes());
            out.write(encryptLocation);
            String result = in.readUTF();
            if (result != null) {
                System.out.println(result);
            }
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println( "Error" /*EOF:"+e.getMessage()*/);
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }
}
