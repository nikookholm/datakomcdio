package FTPZybo;

import java.util.ArrayList;

public class TestFTPClient {
	
	public static void main(String[] args) {
		String host = "1";
		FTPClient ftp = new FTPClient(host);
		String file = "testting.txt";
		
		ArrayList<String> arg = new ArrayList<String>();
		
		try {
			ftp.list();
			//ftp.retr(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
