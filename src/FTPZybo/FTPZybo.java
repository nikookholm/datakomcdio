package FTPZybo;

public class FTPZybo {
	
	private TUI  tui;
	private Zybo zybo;

	public static void main(String[] args) {
		
		FTPZybo fz = new FTPZybo(new TUI(), new Zybo());

	}
	
	public FTPZybo(TUI tui, Zybo zybo)
	{
		this.tui  = tui;
		this.zybo = zybo;
		
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
