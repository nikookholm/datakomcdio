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
	private String h1, p1, p2;
	
	//
	public FTPClient(String hostAddress){
		host = hostAddress;
	}
	
	//returns infomation about the current file or directory
	//LIST
	public ArrayList<String> list() throws Exception{
		int otherPort = 0;
		String read = "";
		String read2 = "";
		ArrayList<String> ls = new ArrayList<String>();
		
		try{	
			initiateConnection();
		
			//Enter passive mode
			tcp.send("PASV\r\n");
			System.out.println("Client: PASV");
			read = tcp.receive();
			System.out.println("Server: " + read);
			
			//Calculating IP address and port number
			if(read.isEmpty()==false){
				otherPort = calculatePort(read);
				System.out.println("Calculates new port " + otherPort);
			}
			//Sends a list of files in the current directory
			tcp.send("LIST\r\n");
			System.out.println("Client: LIST");
			//read = tcp.receive();
			System.out.println("Server: " + read);
			
			// Åbn dataforbindelse
			System.out.println("Host: " + host);
			TCPConnector tcp2 = new TCPConnector("Localhost", otherPort);
			tcp2.connect();
			read2 = tcp2.receive();
			System.out.println("Server2: " + read2);
			
			read = tcp.receive();
			System.out.println("Server: " + read);
			
			//ls = read;
			
		}catch(IOException e){
			e.printStackTrace();
		}
			tcp.disconnect();
			
			return ls;
	}

	//downloads the specified file from the server
	//RETR
/*	public File retr(String str) throws Exception{
		ArrayList<String> file = new ArrayList<String>();
		int otherPort 	= 0;
		String read = "";
		
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
			//file = read;
			
		}catch(IOException e){
			e.printStackTrace();
		}
			tcp.disconnect();
			return file;
	}
*/	
	//Opretter forbindelse til FTP-serveren og logger ind med bruger og kodeord
	private void initiateConnection() throws Exception{
		String 	user = "user\r\n";
		String 	pswd = "qwerty\r\n";
		String	read = "";
		
		try {
			tcp.connect();
			System.out.println("Client connector til: " + host + ", ved port: " + port);
			do{
				read = tcp.receive();
			}while (read == null);
			System.out.println("Server: " + read);
			
			tcp.send("USER " + user);
			System.out.println("Client sends USER " + user);
			read = tcp.receive();
			System.out.println("Server: " + read);
			
			tcp.send("PASS " + pswd);
			System.out.println("Client sends PASS " + pswd);
			read = tcp.receive();
			System.out.println("Server: " + read);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//Calculates the new IP address and port number, when connecting to PASV mode
	private int calculatePort(String ip){
		int newPort;
		int pi1, pi2;
		
		System.out.println("calculatePorts ip " + ip);
		
		ip = ip.substring(0, ip.indexOf(')'));
		
		if(ip.charAt(0)!='1'){
			for(int i=0 ; i<4 ; i++){
				ip = ip.substring(ip.indexOf(',')+1);
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
		System.out.println("newPort: " + newPort);
		
		return newPort;
		
	}
	
	
	
	
}
