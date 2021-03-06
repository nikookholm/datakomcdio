package FTPZybo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import Common.TCPConnector;
/**
 * 
 * A FTP-Clients, which supports a listing of files on the server,
 * and the retrieval of a specified file.
 *
 */
public class FTPClient {

	private String 		host;
	private int 		port = 21;
	private String		user;
	private String		pswd;
	private TCPConnector tcp;
	
	public FTPClient(String hostAddress , String userName , String password){
		this.host = hostAddress;
		this.user = userName;
		this.pswd = password;
		tcp = new TCPConnector(host, port);
	}
	
	//LIST
  	public ArrayList<String> list(){
		int otherPort 	= 0;
		String answer 	= "";
		String data 	= "";
		ArrayList<String> ls;
		
		try{	
			initiateConnection(user , pswd);
		
			tcp.send("PASV\r\n");
			answer = tcp.receive();
			if(answer.isEmpty() == false){
				otherPort = calculatePort(answer); //Calculating IP address and the new port number
			}
			tcp.send("LIST\r\n");
			
			//Opens the data connection
			TCPConnector tcpData = new TCPConnector(host , otherPort);
			tcpData.connect();
			data = tcpData.receive();
			tcpData.disconnect();
			
			String[] strValues = data.split("\r\n");
			ls = new ArrayList<String>(Arrays.asList(strValues));
			
			tcp.disconnect();
			
		}catch(Exception e){
			ls = new ArrayList<String>();
		}
			return ls;
	}

	//RETR
	public boolean retr(String str){
		boolean bool 	= false;
		int otherPort 	= 0;
		String answer 	= "";
		String data 	= "";
		String strName 	= str;
		
		try{
			initiateConnection(user , pswd);
			
			tcp.send("PASV\r\n");
			answer = tcp.receive();
			if(answer.isEmpty() == false){
				otherPort = calculatePort(answer); //Calculating IP address and new port number
			}
			tcp.send("RETR " + str + "\r\n");
			answer = tcp.receive();
			
			TCPConnector tcpData = new TCPConnector(host , otherPort);
			tcpData.connect();
			data = tcpData.receive();
			
			if((answer.startsWith("550")) == false){
				try {
					File file = new File(strName);
					FileWriter writer = new FileWriter(file);
					writer.write(data);
					writer.close();
				}catch(IOException e1) { }
				tcpData.disconnect();
				
				answer = tcp.receive();
				if(answer.endsWith("226 Transfer complete") == true){
					bool = true;
				}
			}

		}catch(Exception e){}
			tcp.disconnect();
			
			return bool;
	}
	
	//Initiates connection to server and logs in with user and password
	private void initiateConnection(String user , String pswd){
		user = user + "\r\n";
		pswd = pswd + "\r\n";
		
		try {
			tcp.connect();
			tcp.send("USER " + user);
			tcp.send("PASS " + pswd);
			tcp.receive();
			
		} catch (Exception e) { }
	}
	
	//Calculates the new IP address and port number, when connecting to PASV mode
	private int calculatePort(String ip){
		int 	newPort;
		int 	pi1, pi2;
		String 	h1, p1, p2;
		
		ip = ip.substring(0, ip.indexOf(')'));
		
		if(ip.charAt(0) != '1'){
			for(int i=0 ; i<4 ; i++){
				ip = ip.substring(ip.indexOf(',')+1);
			}

			p1 = ip.substring(0, ip.indexOf(','));
			p2 = ip.substring(ip.indexOf(',')+1);
			
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
