import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * @author czakian
 * copyright Christopher Zakian 2010
 */

public class DataServer extends Thread {

    public ServerSocket server;
    public Socket client;
    public boolean accepting;

    public DataServer() throws IOException{

        server = new ServerSocket(5015);
        System.out.println("server listening on port 5015");
        System.out.println(server.getLocalSocketAddress());
        accepting = true;
        this.start();
    }

    //listen for incoming client requests and accept.
    public void run() {
        while(accepting){
            try {
                System.out.println("Waiting for connections.");
                client = server.accept();
                Connect c = new Connect(client);
                System.out.println("accepted a connection from: " + client.getInetAddress());
            } catch (IOException e) {
		e.printStackTrace();
            }
        }
    }

    //put the connect method in its own class so that each client has its own thread.
    private class Connect extends Thread implements Runnable {
        private Socket client;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private DataServer dServer;
        private Data d = null;

        public Connect(Socket clientSocket) throws IOException {
            client = clientSocket;

            //keep this ordering or the streams won't connect with the server.
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());
            this.start();
        }

        public void run() {

            //keep looping when you read an object.
            while (true) {
                //put stuff you want the server to do here.
                try {
		    d = (Data) ois.readObject();
                    d.i += Integer.valueOf(JOptionPane.showInputDialog("add how much to " + d.i));
                    oos.writeObject(d);
                    oos.flush();
                    // close connections
                    //ois.close();
                    //oos.close();
                    //client.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
		    e.printStackTrace();
                }
            }
        }
    }

    public void close(){

        try {
            client.close();
            server.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        DataServer d;
        try {
            d = new DataServer();

        } catch (IOException e) {
            System.err.println("IO Exception!");
            e.printStackTrace();
        }
    }
}