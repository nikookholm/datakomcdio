package FTPZybo;

import java.util.ArrayList;
import java.util.Scanner;

public class TUI {
	
	private Scanner scanner = new Scanner(System.in);
	
	public int printMenu()
	{
		System.out.println("***********************************************");
		System.err.println("*                                             *");
		System.out.println("* Super sej FTP/Zybo-ting                     *");
		System.err.println("*                                             *");
		System.out.println("***********************************************");
		System.err.println("*                                             *");
		System.err.println("* Menu:                                       *");
		System.err.println("*                                             *");
		System.err.println("* 1. List filer på FTP                    ... *");
		System.err.println("*                                             *");
		System.err.println("* 2. Skriv til sensor                     ... *");
		System.err.println("* 3. Læs fra sensor nr.                   ... *");
		System.err.println("* 4. Start sensor                         ... *");
		System.err.println("* 5. Stop sensor                          ... *");
		System.err.println("* 6. List sensorer                            *");
		System.err.println("*                                             *");
		System.out.println("***********************************************");
		
		System.out.println("Indtast valg: ");		
		
		return scanner.nextInt();
	}
	
	public void listFiles(ArrayList<String> files)
	{
		
		for (String file : files)
		{
			System.out.println(file);
		}
		
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
