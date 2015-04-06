package Common;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPConnector implements Runnable {
	
	private int				  port;
	private ServerSocket	  tcpServer;
	private ArrayList<String> receivedData;
	private Socket			  tcpClient;
	
	public TCPConnector(int port)
	{
		this.port = port;
		
		try {
			connect();
		} catch (Exception e) {}
	}
	
	public boolean connect() throws Exception
	{
		tcpServer = new ServerSocket(port);
		tcpServer.setSoTimeout(10000);
		receivedData = new ArrayList<String>();
		
		return !tcpServer.isClosed();
	}
	
	public boolean disconnect()
	{
		try {
			tcpServer.close();
		}
		catch (IOException e) {}
		
		return !tcpServer.isClosed();
	}
	
	public void run()
	{
		
		while (tcpClient == null)
		{
			waitForClient();
		}
		
		while (tcpClient != null)
		{
			listenForData();
		}
		
	}
	
	public void waitForClient()
	{
		while (tcpClient == null)
		{
			try
			{
				tcpClient = tcpServer.accept();
			} catch (IOException e) {}
		}
	}
	
	public void listenForData()
	{
		String readString = null;
		
		if (!tcpServer.isClosed())
		{
			try
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(tcpClient.getInputStream()));
				while (readString == null)
				{
					readString = reader.readLine();
					receivedData.add(readString);
				}
			}
			catch (Exception e) {}
		}
	}
	
	public void send(String data)
	{
		try
		{
			if ((!tcpServer.isClosed()) && (tcpClient != null))
			{
				DataOutputStream dataOut = new DataOutputStream(tcpClient.getOutputStream());
		
				dataOut.writeBytes(data + "\r\n");
				dataOut.flush();
			}
		}
		catch (Exception e) {}
	}
	
	
	public String getReceivedData()
	{
		String data = null;
		if (receivedData.size() > 0)
		{
			data = receivedData.get(0);
			receivedData.remove(0);
		}
		
		return data;
	}
	
}