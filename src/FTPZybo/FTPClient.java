package FTPZybo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Common.TCPConnector;

public class FTPClient {

	private ArrayList 	files = new ArrayList();
	private String 		host = "localhost";
	private int 		port = 21;
	private TCPConnector tcp = new TCPConnector(host, port); 
	private String 		read = null;
	
	//
	private FTPClient(){
	
	}
	
	//returns infomation about the current file or directory
	//LIST
	private void list() throws Exception{
			
		try{	
			initiateConnection();
			tcp.send("LIST");
			System.out.println("Client: LIST");
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + read);
		}catch(IOException e){
			e.printStackTrace();
		}
			tcp.disconnect();
	}

	//downloads the specified file from the server
	private void retr(File whatever) throws Exception{
		
		try{
			initiateConnection();
			tcp.send("RETR");
			System.out.println("Client: RETR");
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + read);
		} catch (IOException e){
			e.printStackTrace();
		}
			tcp.disconnect();
	}
	
	//Opretter forbindelse og logger ind med bruger og kodeord
	private void initiateConnection() throws Exception{
		String user = "user";
		String pswd = "qwerty";
		
		try {
			tcp.connect();
			System.out.println("Client connector: " + host + " ved port:" + port);
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + read);
			
			tcp.send("USER " + user);
			System.out.println("Client sends USER " + user);
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + read);
			
			tcp.send("PASS " + pswd);
			System.out.println("Client sends PASS " + pswd);
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + read);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
