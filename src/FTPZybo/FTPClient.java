package FTPZybo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Common.TCPConnector;

public class FTPClient {

	private ArrayList 	filesLs = new ArrayList();
	private String 		host;
	private int 		port = 21;
	private TCPConnector tcp = new TCPConnector(host, port); 
	private String 		read = null;
	private String h1, p1, p2;
	
	//
	public FTPClient(String hostAddress){
		host = hostAddress;
	}
	
	//returns infomation about the current file or directory
	//LIST
	public ArrayList<String> list() throws Exception{
		int otherPort = 0;
		ArrayList<String> ls = new ArrayList<String>();
		
		try{	
			initiateConnection();
		
			//Enter passive mode
			tcp.send("PASV");
			System.out.println("Client: PASV");
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + read);
			
			//Calculating IP address and port number
			if(read.isEmpty()==false){
				otherPort = calculatePort(read);
			}
			
			//Sends a list of files in the current directory
			tcp.send("lLSI");
			System.out.println("Client: LIST");
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + read);
			
			// Åbn dataforbindelse
			TCPConnector tcp2 = new TCPConnector(host, otherPort);
			tcp2.connect();
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + read);
			ls = read;
			
		}catch(IOException e){
			e.printStackTrace();
		}
			tcp.disconnect();
			
			return ls;
	}

	//downloads the specified file from the server
	//RETR
	public File retr(String str) throws Exception{
		File file 		= null;
		int otherPort 	= 0;
		
		try{
			initiateConnection();
			tcp.send("PASV");
			System.out.println("Client: PASV");
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + "PASV");
			
			//Calculating IP address and port number
			if(read.isEmpty()==false){
				otherPort = calculatePort(read);
			}
			
			//Initiates retreval of file
			tcp.send("RETR " + str);
			System.out.println("Client: RETR " + str);
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + read);
		
			TCPConnector tcp2 = new TCPConnector(host, otherPort);
			tcp2.connect();
			while (read == null){
				read = tcp.receive();
			}
			System.out.println("Server: " + read);
			file = read;
			
		}catch(IOException e){
			e.printStackTrace();
		}
			tcp.disconnect();
			return file;
	}
	
	//Opretter forbindelse til FTP-serveren og logger ind med bruger og kodeord
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
	
	//Calculates the new IP address and port number, when connecting to PASV mode
	private int calculatePort(String ip){
		int newPort;
		int pi1, pi2;
		
		if(ip.charAt(0)!='1'){
			for(int i=0 ; i<4 ; i++){
				ip = ip.substring(ip.indexOf(',')+1);
				System.out.println(ip);
			}

			String p1 = ip.substring(0, ip.indexOf(','));
			String p2 = ip.substring(ip.indexOf(',')+1);
			
			pi1 = Integer.parseInt(p1);
			pi2 = Integer.parseInt(p2);
			
		}else{
			h1 = ip.substring(0, ip.indexOf(','));
			ip = ip.substring(h1.length()+1);
			p1 = ip.substring(0, ip.indexOf(','));
			ip = ip.substring(p1.length()+1);
			p2 = ip;
			
			pi1 = Integer.parseInt(p1);
			pi2 = Integer.parseInt(p2);
		}
		
		newPort = pi1*256+pi2;
		
		return newPort;
		
	}
	
	
	
	
}
