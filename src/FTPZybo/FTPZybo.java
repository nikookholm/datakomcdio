package FTPZybo;

import java.util.ArrayList;

public class FTPZybo {
	
	private TUI		  tui;
	private Zybo	  zybo;
	private FTPClient ftp;

	public static void main(String[] args) {
	
		
//		if (args.length > 1)
//		{
//			String host = args[0];
			String host = "localhost";
			FTPZybo fz = new FTPZybo(new TUI(), new Zybo(), new FTPClient(host));
//		}
//		else
//		{
//			System.out.println("Du har ikke angivet IP adresse til FTP serveren som det første argument, programmet lukkes!");
//		}

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
			String getFile = tui.listFiles(ftp.list());
			if (getFile != null)
			{
				tui.downloadStatus(ftp.retr(getFile), getFile);
			}
		break;
		case "2":
			int sensorNumberW = tui.writeSensor();
			zybo.writeToSensor(sensorNumberW, 1);
			tui.printMessage("Skrevet værdi (1) til sensor " + sensorNumberW);
		break;
		case "3":
			int sensorNumberR = tui.readFromSensor();
			int readValue = zybo.readFromSensor(sensorNumberR);
			tui.printMessage("Læst fra sensor " + sensorNumberR + " - Værdi: " + readValue);
		break;
		case "4":
			int sensorNumberSS = tui.readFromSensor();
			zybo.setSensorStatus(sensorNumberSS, true);
		break;
		case "5":
			int sensorNumberSS2 = tui.readFromSensor();
			zybo.setSensorStatus(sensorNumberSS2, false);
		break;
		case "6":
			tui.listAllSensors(zybo.listAllSensors());
		break;
		case "0":
			tui.printMessage("Programmet er afsluttet! - God dag!");
			System.exit(0);
		break;
		default:
		break;
		}
		
	}
	
}
