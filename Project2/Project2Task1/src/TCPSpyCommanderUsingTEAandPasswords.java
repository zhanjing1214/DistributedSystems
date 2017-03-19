
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author zhanjing
 */
public class TCPSpyCommanderUsingTEAandPasswords {

    public static void main(String args[]) {
        try {
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            initialKML();
            System.out.println("Enter summetric key for TEA (taking first sixteen bytes):");
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
            String serverKey = typed.readLine(); //enter the symmetric key
            System.out.println("Waiting for spies to visit...");
            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket, serverKey);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }

    }

    /**
     * initialize the KML with default data
     */
    static void initialKML() {
        try {
            FileWriter fw = new FileWriter("SecretAgent.kml");
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                    + "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n"
                    + "<Document>\n"
                    + "<Style id=\"style1\">\n"
                    + "<IconStyle>\n"
                    + "<Icon>\n"
                    + "<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/bluedot.\n"
                    + "png</href>\n"
                    + "</Icon>\n"
                    + "</IconStyle>\n"
                    + "</Style>\n"
                    + "<Placemark>\n"
                    + "<name>seanb</name>\n"
                    + "<description>Spy Commander</description>\n"
                    + "<styleUrl>#style1</styleUrl>\n"
                    + "<Point>\n"
                    + "<coordinates>-79.945289,40.44431,0.00000</coordinates>\n"
                    + "</Point>\n"
                    + "</Placemark>\n"
                    + "<Placemark>\n"
                    + "<name>jamesb</name>\n"
                    + "<description>Spy</description>\n"
                    + "<styleUrl>#style1</styleUrl>\n"
                    + "<Point>\n"
                    + "<coordinates>-79.940450,40.437394,0.0000</coordinates>\n");
            fw.write("</Point>\n"
                    + "</Placemark>\n"
                    + "<Placemark>\n"
                    + "<name>joem</name>\n"
                    + "<description>Spy</description>\n"
                    + "<styleUrl>#style1</styleUrl>\n"
                    + "<Point>\n"
                    + "<coordinates>-79.945389,40.444216,0.00000</coordinates>\n"
                    + "</Point>\n"
                    + "</Placemark>");
            fw.write("<Placemark>\n"
                    + "<name>mikem</name>\n"
                    + "<description>Spy</description>\n"
                    + "<styleUrl>#style1</styleUrl>\n"
                    + "<Point>\n"
                    + "<coordinates>-79.948460,40.444501,0.00000</coordinates>\n"
                    + "</Point>\n"
                    + "</Placemark>\n"
                    + "</Document>\n"
                    + "</kml>");
            fw.flush();
        } catch (IOException ex) {

        }
    }
}


class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    static String serverKey; 
    static int count = 1; //count for spies visited
    //user info with ID salt, hash and password salt, hash
    ArrayList<byte[]> user1; 
    ArrayList<byte[]> user2;
    ArrayList<byte[]> user3;

    /**
     * this method starts the connection with client socket
     * @param aClientSocket client socket
     * @param key symmetric key
     */
    public Connection(Socket aClientSocket, String key) {
        try {
            serverKey = key;
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    /**
     * this method runs when a client socket connects
     */
    public void run() {
        try {
            setIDPw(); //initialize the 3 spies info
            out.writeUTF("Enter summetric key for TEA (taking first sixteen bytes):");
            out.writeUTF("Enter your ID: ");
            byte[] id = new byte[100]; // store encrypted id
            in.read(id);
            out.writeUTF("Enter your Password: ");
            byte[] pw = new byte[100]; //encrypted password
            in.read(pw);
            out.writeUTF("Enter your location: ");
            byte[] location = new byte[100]; // encrypted location
            in.read(location);
            TEA tea = new TEA(serverKey.getBytes()); //TEA of the symmetric key
            
            if (!isBadKey(tea, id, pw, location)) {
                String clearID = new String(tea.decrypt(id));
                String clearPw = new String(tea.decrypt(pw));
                if (!isValidIDPw(clearID, clearPw)) {
                    System.out.println("Got visit " + count + " from " + clearID + ". Illegal Password attempt. This may be an attack.");
                    count++;
                    out.writeUTF("Not a valid user-id or password");
                } else {
                    System.out.println("Got visit " + count + " from " + clearID + ".");
                    count++;
                    refreshKML(getSpyIndex(clearID), new String(tea.decrypt(location)));
                    out.writeUTF("Thank you. You location was securely transmitted to Intelligence Headquaters.");
                }
            } else {
                clientSocket.close();
                System.out.println("Got visit " + count + "illegal symmetric key used. This may be an attack.");
                count++;
            }
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/
            }
        }
    }

    /**
     * this method initialize the user info of the 3 spies
     */
    void setIDPw() {
        PasswordHash ph = new PasswordHash();
        user1 = new ArrayList();
        byte[] saltID_1 = ph.generateSalt();
        byte[] hashID_1 = ph.hash(saltID_1, "jamesb");
        byte[] saltPw_1 = ph.generateSalt();
        byte[] hashPw_1 = ph.hash(saltPw_1, "james");
        user1.add(saltID_1);
        user1.add(hashID_1);
        user1.add(saltPw_1);
        user1.add(hashPw_1);

        user2 = new ArrayList();
        byte[] saltID_2 = ph.generateSalt();
        byte[] hashID_2 = ph.hash(saltID_2, "joem");
        byte[] saltPw_2 = ph.generateSalt();
        byte[] hashPw_2 = ph.hash(saltPw_2, "joe");
        user2.add(saltID_2);
        user2.add(hashID_2);
        user2.add(saltPw_2);
        user2.add(hashPw_2);

        user3 = new ArrayList();
        byte[] saltID_3 = ph.generateSalt();
        byte[] hashID_3 = ph.hash(saltID_3, "mikem");
        byte[] saltPw_3 = ph.generateSalt();
        byte[] hashPw_3 = ph.hash(saltPw_3, "mike");
        user3.add(saltID_3);
        user3.add(hashID_3);
        user3.add(saltPw_3);
        user3.add(hashPw_3);
    }

    /**
     * this method checks whether the key is bad
     * @param tea TEA of the symmetric key
     * @param b1 encrypted message1
     * @param b2 encrypted message2
     * @param b3 encrypted message3
     * @return true if there is any char ascii larger than 128, false if not
     */
    boolean isBadKey(TEA tea, byte[] b1, byte[] b2, byte[] b3) {
        boolean isBad = false;
        String decrypt = new String(tea.decrypt(b1));
        int asc;
        for (int i = 0; i < decrypt.length(); i++) {
            asc = (int) decrypt.charAt(i);
            if (asc > 128) {
                isBad = true;
                break;
            }
        }
        decrypt = new String(tea.decrypt(b2));
        for (int i = 0; i < decrypt.length(); i++) {
            asc = (int) decrypt.charAt(i);
            if (asc > 128) {
                isBad = true;
                break;
            }
        }
        decrypt = new String(tea.decrypt(b3));
        for (int i = 0; i < decrypt.length(); i++) {
            asc = (int) decrypt.charAt(i);
            if (asc > 128) {
                isBad = true;
                break;
            }
        }
        return isBad;
    }

    /**
     * this method refresh the location of a specified spy in KML
     * @param index the index of the spy
     * @param location the location of the spy
     */
    void refreshKML(int index, String location) {
        try {
            FileReader file = new FileReader("SecretAgent.kml");
            BufferedReader br = new BufferedReader(file);
            StringBuilder sb = new StringBuilder();
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (line.contains("<coordinates>")) {
                    if (i == index) {
                        line = "<coordinates>" + location + "</coordinates>";
                    }
                    i++;
                }
                sb.append(line + "\n");
            }
            file.close();
            br.close();

            PrintWriter pw = new PrintWriter("SecretAgent.kml");
            pw.write(sb.toString());
            pw.flush();
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * this method checks if the input id and key is right
     * @param id the input id
     * @param pw the input password
     * @return true if id and passwords are right, false if not
     */
    private boolean isValidIDPw(String id, String pw) {
        boolean valid = false;
        PasswordHash ph = new PasswordHash();
        if(ph.isEqual(ph.hash(user1.get(0), id), user1.get(1))) {
            if(ph.isEqual(ph.hash(user1.get(2), pw), user1.get(3))) {
                valid = true;
            }
        }
        if(ph.isEqual(ph.hash(user2.get(0), id), user2.get(1))) {
            if(ph.isEqual(ph.hash(user2.get(2), pw), user2.get(3))) {
                valid = true;
            }
        }
        if(ph.isEqual(ph.hash(user3.get(0), id), user3.get(1))) {
            if(ph.isEqual(ph.hash(user3.get(2), pw), user3.get(3))) {
                valid = true;
            }
        }
        return valid;
    }
    
    /**
     * this method gets the corresponding index of the spy from the input id
     * @param id the input id
     * @return the index of the spy
     */
    int getSpyIndex(String id) {
        int i = 0;
        PasswordHash ph = new PasswordHash();
        if(ph.isEqual(ph.hash(user1.get(0), id), user1.get(1))) {
            i = 1;
        }
        if(ph.isEqual(ph.hash(user2.get(0), id), user2.get(1))) {
            i = 2;
        }
        if(ph.isEqual(ph.hash(user3.get(0), id), user3.get(1))) {
            i = 3;
        }
        return i;
    }
}
