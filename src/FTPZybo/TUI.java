package FTPZybo;

import java.util.ArrayList;
import java.util.Scanner;

public class TUI {
	
	private Scanner scanner = new Scanner(System.in);
	
	public String printMenu()
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
		
		return scanner.nextLine();
	}
	
	public String listFiles(ArrayList<String> files)
	{
		System.out.println("Alle filer på FTP serveren:");
		
		for (int i = 1; i <= files.size(); i++)
		{
			System.out.println("  " + i + ": " + files.get(i));
		}
		
		System.out.println("\nFor at hente fil, indtast filens nummer, eller tryk enter ...");
		
		String input = scanner.nextLine();
		int fileNumber = -1;
		
		try
		{
			fileNumber = Integer.parseInt(input);
		} catch (Exception e) {}
		
		if ((fileNumber > 0) && (fileNumber <= files.size()))
		{
			int splitHere = files.get(fileNumber).lastIndexOf(" ");
			return files.get(fileNumber).substring(splitHere).trim();
		}
		else
		{
			return null;
		}
		
	}
	
	public void printMessage(String message)
	{
		System.out.println(message);
	}
	
	public int readFromSensor()
	{
		int returnValue = -1;
		while ((returnValue < 0) || (returnValue > 15))
		{
			System.out.println("Indtast sensornummer mellem 0 og 15:");
			try
			{
				returnValue = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {}
		}
		
		return returnValue;
		
	}
	
	public int writeSensor()
	{
		int returnValue = -1;
		while ((returnValue < 0) || (returnValue > 15))
		{
			System.out.println("Indtast sensornummer (mellem 0 og 15) som skal sendes værdi(1) til:");
			try
			{
				returnValue = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {}
		}
		
		return returnValue;
	
	}
	
	public void listAllSensors(ArrayList<Integer> sensors)
	{
		for (int sensor : sensors)
		{
			System.out.println("Aktive sensorer - Sensor nr: " + sensor);
		}
	}
	
	public void downloadStatus(boolean status, String filename)
	{
		if (status)
		{
			System.out.println(filename + " er overført!");
		}
		else
		{
			System.out.println(filename + " kunne ikke hentes");
		}
	}

}
