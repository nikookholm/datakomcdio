package FTPZybo;

public class FTPZybo {
	
	private TUI		  tui;
	private Zybo	  zybo;
	private FTPClient ftp;

	public static void main(String[] args) {
		
		FTPZybo fz = new FTPZybo(new TUI(), new Zybo(), new FTPClient());

	}
	
	public FTPZybo(TUI tui, Zybo zybo, FTPClient ftp)
	{
		this.tui  = tui;
		this.zybo = zybo;
		this.ftp  = ftp;
		
		operations(tui.printMenu());
	}
	
	
	public void operations(int menuChoice)
	{
		
		switch (menuChoice) {
		case 1:
			
			break;

		default:
			break;
		}
		
	}

}
