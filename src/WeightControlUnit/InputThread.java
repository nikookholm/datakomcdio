package WeightControlUnit;

import java.util.Scanner;

public class InputThread implements Runnable {
	
	Scanner scan = new Scanner(System.in);

	public void run()
	{
		while (scan.nextLine().toLowerCase().equals("q"))
		{
			System.out.println("Programmet afsluttes ...");
			System.exit(0);
		}
	}
	
}
