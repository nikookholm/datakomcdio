//Oprettet klassen her, så kan den flyttes hvor den skal være.


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

/*
 * Klassen fungerer som en database, og skriver til de to filer log.txt og store.txt.
 * log.txt er en log fil og alt hvad operatøren gør skal logges.
 * store.txt er varelager databasen. Indeholder et antal råvarer, og det er mængden af disse der skal
 * holdes styr på.
 */

public class FakeDB {
	private String path;
	private boolean append = false;

	// der skal være en metode til at skrive på logfilen, og en metode til at skrive på store filen.

	// konstruktør til at skrive i loggen, denne fil skal altid forlænges
	public FakeDB(String logFilePath){
		path = logFilePath;	
		append = true;
	}

	public FakeDB(String storeFilePath, boolean append_this){
		path = storeFilePath;
		append = append_this;
	}


	public void writer(String text) throws IOException{
		FileWriter fw = new FileWriter(path, append);
		PrintWriter printText = new PrintWriter(fw);

		printText.printf("%s" + "%n", text);
		printText.close();
	}

	// kan lave en DBexception, der viser fejl hvis commodity ikke find

	public void changeStoreText(String commodity,int amountTaken) throws IOException{
		try {
		String lineToBeChanged, newLine;
		int amountBefore, amountNow;
		
		StringBuilder sb = new StringBuilder();
		FileInputStream textIn =  new FileInputStream("store.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(textIn));

		try{
			
		while((lineToBeChanged = br.readLine()) != null){
			String seperatedInfo[] = lineToBeChanged.split(",");
			
		
			if(seperatedInfo[0].equals(commodity)){
				
			
				amountBefore = Integer.parseInt(seperatedInfo[1]);
				amountNow = amountBefore - amountTaken;
				newLine = seperatedInfo[0] + "," + amountNow;
				sb.append(newLine);
				sb.append("\n");
			}
			else{
				sb.append(lineToBeChanged);
				sb.append("\n");
			}
		}
		}
		catch(Exception e){
			System.out.println("råvare kan ikke findes");
		}
		FileWriter fstreamWrite = new FileWriter("store.txt");
        BufferedWriter out = new BufferedWriter(fstreamWrite);
        out.write(sb.toString());
        out.close(); // closes writer
        
       
        textIn.close(); //This closes inputStream
        
    } catch (Exception e) {// skal nok lave en bedre exception end dette
        System.err.println("Fejl: " + e.getMessage());
    }
	}
}
