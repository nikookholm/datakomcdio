package FTPZybo;

import java.util.ArrayList;
import java.util.Scanner;

public class TUI {
	
	private Scanner scanner = new Scanner(System.in);
	
	public int printMenu()
	{
		System.out.println("***********************************************");
		System.out.println("*                                             *");
		System.out.println("* Super sej FTP/Zybo-ting                     *");
		System.out.println("*                                             *");
		System.out.println("***********************************************");
		System.out.println("*                                             *");
		System.out.println("* Menu:                                       *");
		System.out.println("*                                             *");
		System.out.println("* 1. List filer på FTP                    ... *");
		System.out.println("*                                             *");
		System.out.println("* 2. Skriv til sensor                     ... *");
		System.out.println("* 3. Læs fra sensor nr.                   ... *");
		System.out.println("* 4. Start sensor                         ... *");
		System.out.println("* 5. Stop sensor                          ... *");
		System.out.println("* 6. List sensorer                            *");
		System.out.println("*                                             *");
		System.out.println("* 0. Afslut program                           *");
		System.out.println("*                                             *");
		System.out.println("***********************************************");
		
		System.out.println("Indtast valg: ");		
		
		return scanner.nextInt();
	}
	
	public void listFiles(ArrayList<String> files)
	{
		System.out.println("Alle filer på FTP serveren:");
		
		for (int i = 0; i < files.size(); i++)
		{
			System.out.println("  " + (i+1) + ": " + files.get(i));
		}
		
		System.out.println("\nFor at hente fil, indtast filens nummer, eller tryk enter ...");
		
		String input = scanner.next();
		
	}
	
	public void printMessage(String message)
	{
		System.out.println(message);
	}
	
	public int readFromSensor(int sensorNumber)
	{
		//return Zybo.readSensor(sensorNumber); 
		return 0;
	}
	
	public void writeSensor(int sensorNumber)
	{
		System.out.println("Send to sensor:");
		int input = scanner.nextInt();
		//Zybo.writeSensor(sensorNumber, input);
	}
	
	public void listAllSensors(ArrayList<Integer> sensors)
	{
		for (int sensor : sensors)
		{
			System.out.println("Sensor nr: " + sensor);
		}
	}

}
