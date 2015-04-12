package Common;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

public class TCPConnector {
	
	private String 	host;
	private int		port;
	public Socket	tcpClient;
	
	public TCPConnector(String host, int port)
	{
		this.host = host;
		this.port = port;
	}
	
	public boolean connect() throws Exception
	{
		tcpClient = new Socket(host, port);
		return tcpClient.isConnected();
	}
	
	public boolean disconnect()
	{
		try
		{
			if (tcpClient.isConnected())
			{
				tcpClient.close();
			}
		}
		catch (Exception e) {}
		return tcpClient.isClosed();
	}
	
	public void send(String data)
	{
		try
		{
			if (tcpClient.isConnected())
			{
				DataOutputStream dataOut = new DataOutputStream(tcpClient.getOutputStream());
			
				dataOut.writeBytes(data);
			}
		}
		catch (Exception e) {}
	}
	
	public String receive()
	{
		String readString = null;
		
		try
		{
			if (tcpClient.isConnected())
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(tcpClient.getInputStream(), "UTF-8"));
				while (readString == null)
				{
					readString = reader.readLine();
				}
			}
		}
		catch (Exception e) {}
		
		return readString;
	}
}