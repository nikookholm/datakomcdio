package Common;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

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
		tcpClient.setSoTimeout(200);
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
		String result = "";
		
		try
		{
			if (tcpClient.isConnected())
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(tcpClient.getInputStream()));
				while ((readString = reader.readLine()) != null)
				{
					result = result + "\r\n" + readString;
				}
			}
		}
		catch (Exception e) {}
		
		return result.trim();
	}
}