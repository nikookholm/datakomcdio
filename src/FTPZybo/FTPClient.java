package FTPZybo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
  	public ArrayList<String> list(){
		int otherPort 	= 0;
		String read 	= "";
		String read2 	= "";
		//ArrayList<String> ls = new ArrayList<String>();
		
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
			
			// Åbner dataforbindelse
			System.out.println("Host: " + host);
			TCPConnector tcp2 = new TCPConnector(host, otherPort);
			tcp2.connect();
			
			read = tcp.receive();
			System.out.println("Server: " + read);
			
			read2 = tcp2.receive();
			System.out.println("Server2: " + read2);
			tcp2.disconnect();
			
			read = tcp.receive();
			System.out.println("Server: " + read);
			
		}catch(IOException e){
			e.printStackTrace();
		}
			tcp.disconnect();
			
			//Omformer read til en ArrayList
			String[] strValues = read2.split("\r\n");
			ArrayList<String> ls = new ArrayList<String>(Arrays.asList(strValues));
			
			System.out.println("StrVal: " + strValues.length);
			System.out.println("LS: " + ls);
			
			return ls;
	}


	//downloads the specified file from the server
	//RETR
	public ArrayList<String> retr(String str){
		ArrayList<String> file = new ArrayList<String>();
		ArrayList<String> list = new ArrayList<String>();
		int otherPort 	= 0;
		String read 	= "";
		String read2 	= "";
		str = "testting.txt"; //Korrekt
		//str = "fejl.txt";
		
		try{
			initiateConnection();
			
			tcp.send("PASV\r\n");
			System.out.println("Client: " + read);
			read = tcp.receive();
			System.out.println("Server: " + read);
			
			//Calculating IP address and port number
			if(read.isEmpty()==false){
				otherPort = calculatePort(read);
				System.out.println("Calculates new port " + otherPort);
			}
			
			//Initiates retreval of file
			tcp.send("RETR " + str);
			System.out.println("Client: RETR " + str + "\r\n");
			read = tcp.receive();
			System.out.println("Server: " + read);
		
			TCPConnector tcp2 = new TCPConnector(host, otherPort);
			tcp2.connect();
			
			read = tcp.receive();
			System.out.println("Server: " + read);
			
			read2 = tcp2.receive();
			System.out.println("Server2: " + read2);
			tcp2.disconnect();
			
			read = tcp.receive();
			System.out.println("Server: " + read);
			
		}catch(Exception e){
			e.printStackTrace();
		}
			tcp.disconnect();
			
			return file;
	}


	//Opretter forbindelse til FTP-serveren og logger ind med bruger og kodeord
	private void initiateConnection(){
		String 	user = "user\r\n";
		String 	pswd = "qwerty\r\n";
		String	read = "";
		
		try {
			tcp.connect();
			System.out.println("Client: Connector til: " + host + ", ved port: " + port);
			read = tcp.receive();
			System.out.println("Server: " + read);
			
			tcp.send("USER " + user);
			System.out.println("Client: Sends USER " + user);
			read = tcp.receive();
			System.out.println("Server: " + read);
			
			tcp.send("PASS " + pswd);
			System.out.println("Client: Sends PASS " + pswd);
			read = tcp.receive();
			System.out.println("Server: " + read);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//Calculates the new IP address and port number, when connecting to PASV mode
	private int calculatePort(String ip){
		int newPort;
		int pi1, pi2;
		
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
