
import java.net.*;
import java.io.*;
/**
 *
 * @author zhanjing
 */
public class TCPSpyUsingTEAandPasswords {

    public static void main(String args[]) {
        Socket s = null;
        try {
            int serverPort = 7896;
            s = new Socket(args[0], serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(in.readUTF());
            String key;
            TEA tea = null;
            //check valid key
            do {
                key = typed.readLine();
                if (key.getBytes().length < 16) {
                    System.out.println("please make sure your key is longer than sixteen bytes.");
                } else {
                    tea = new TEA(key.getBytes());
                }

            } while (key.getBytes().length < 16);
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
            String pw = "";
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
            
            System.out.println(result);
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println( "Exception: Error" /*EOF:"+e.getMessage()*/);
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
