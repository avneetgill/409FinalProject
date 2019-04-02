package Client.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Provides A client for the user to interact with, allows user to send messages to
 * the client and receive messages from the server.
 * 
 * @author Shamin Rahman
 * @version 1.0
 * @since March 25, 2019
 */
public class Client {
	/**
	 * Writer to allow the client to write to the socket
	 */
	private PrintWriter socketOut;
	
	/**
	 * Socket that is used to communicate to server
	 */
	private Socket palinSocket;

	/**
	 * Reader to get input from client's console
	 */
	private BufferedReader stdIn;

	/**
	 * Reader to get what is written to the socket by the server
	 */
	private BufferedReader socketIn;
	
	private GUIController controller;

	/**
	 * Constructs a Client with the specified port name and number, opens and initializes sockets and I/O streams required for operation. 
	 * @param port port number for the socket
	 * @param name port name for the socket
	 */
    public Client(int port, String name){
        try{
            palinSocket = new Socket(name, port);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(palinSocket.getInputStream()));
			socketOut = new PrintWriter((palinSocket.getOutputStream()), true);
        }catch(IOException a){
            a.printStackTrace();
        }
	}

	/**
	 * Allows communication between the server and the client, by sending and receiving messages through the socket. 
	 */
	public void communicate(){
		try{
			String read;
			while(true){
				read = "";
				while(true){
					read = socketIn.readLine();
					if(read.contains("\1")){
						read = read.replaceAll("\1", "");
						System.out.println(read);
						return;
					}
					if(read.contains("\0")){
						read = read.replaceAll("\0", "");
						System.out.println(read);
						break;
					}
					if(read.contains("quit")){
						if(!read.equalsIgnoreCase("quit")){
							System.out.println(read);
						}
						System.out.println("Goodbye");
						return;
					}
					System.out.println(read);
				}
				read = stdIn.readLine();
				socketOut.println(read);
				socketOut.flush();
			}
		}catch(IOException a){
			System.err.println("IOException caught");
			a.printStackTrace();
		} catch(Exception a){
			System.err.println("unknown error caught");
			a.printStackTrace();
		} finally{
			try{
				stdIn.close();
				palinSocket.close();
			}catch(IOException b){
				b.printStackTrace();
			}
		}
	}
	/**
	 * runs the client with port localhost 8988
	 * @param args command line arguments. 
	 */
	public static void main(String[] args) {
        Client c = new Client(8988, "localhost");
		c.communicate();
    }
}