import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 * @author czakian
 * copyright Christopher Zakian 2010
 */

public class DataClient {

    Socket client;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    String ip;
    Data d;
    public DataClient() throws UnknownHostException, IOException{
	d = new Data(0);
	client = new Socket("127.0.0.1", 5015);

	//keep order or the client won't connect to the server.
	oos = new ObjectOutputStream(client.getOutputStream());
	ois = new ObjectInputStream(client.getInputStream());
    }

    //put the stuff you want to step from the client here.
    public void step(int i) throws IOException {

	try {
	    d.i = i;
	    oos.writeObject(d);
	    oos.flush();

	    d = (Data) ois.readObject(); 
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	d.i += 5;
	// oos.writeObject(d);
	// oos.flush();
	// System.out.println("data sent from client");
	System.out.println(d.i);
	step(d.i);
	System.out.println("stepping");
    }

    public static void main(String[] args) {
	DataClient d;
	try {
	    d = new DataClient();
	    d.step(0);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    } 
}