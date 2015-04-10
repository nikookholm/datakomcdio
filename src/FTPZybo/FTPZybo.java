package FTPZybo;

import java.util.ArrayList;

public class FTPZybo {
	
	private TUI		  tui;
	private Zybo	  zybo;
	private FTPClient ftp;

	public static void main(String[] args) {
		
		FTPZybo fz = new FTPZybo(new TUI(), new Zybo(), new FTPClient("localhost"));

	}
	
	public FTPZybo(TUI tui, Zybo zybo, FTPClient ftp)
	{
		this.tui  = tui;
		this.zybo = zybo;
		this.ftp  = ftp;
		while (true)
		{
			operations(tui.printMenu());
		}
	}
	
	
	public void operations(String menuChoice)
	{
		
		switch (menuChoice) {
		case "1":
			ArrayList<String> files = new ArrayList<String>();
			files.add("File 1");
			files.add("File 2");
			
			tui.listFiles(files);
		break;

		default:
			break;
		}
		
	}
	
}
