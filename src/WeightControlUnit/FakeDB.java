package WeightControlUnit;




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Klassen fungerer som en database, og skriver til de to filer log.txt og store.txt.
 * log.txt er en log fil og alt hvad operatøren gør skal logges.
 * store.txt er varelager databasen. Indeholder et antal råvarer, og det er mængden af disse der skal
 * holdes styr på.
 */

public class FakeDB {
	private String path;
	private boolean append = false;
	private ArrayList<Items> stock = new ArrayList<Items>();

	// der skal være en metode til at skrive på logfilen, og en metode til at skrive på store filen.

	// konstruktør til at skrive i loggen, denne fil skal altid forlænges
	public FakeDB(){
		try {
			loadList();
		} catch (IOException e) {
		     System.out.println("Database filen findes ikke");
                     
		}
	}



	public void logWriter(String text) throws IOException{
		FileWriter fw = new FileWriter(path, append);
		PrintWriter printText = new PrintWriter(fw);

		printText.printf("%s" + "%n", text);
		printText.close();
	}

	// kan lave en DBexception, der viser fejl hvis commodity ikke find

	public void changeStoreText() throws IOException{
		try {

			StringBuilder sb = new StringBuilder();
			FileInputStream textIn =  new FileInputStream("C:\\Users\\Thomas Elbo\\Documents\\GitHub\\datakomcdio/store.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(textIn));

			try{

				for (int i = 0; i < stock.size(); i++) {
					sb.append(stock.get(i).getItemNo() + "," + stock.get(i).getItem() + "," + stock.get(i).getAmount() );
					sb.append("\n");
				}
			}
			catch(Exception e){// skal nok se på en bedre  exception.
				System.out.println("råvare kan ikke findes");
			}
			FileWriter fstreamWrite = new FileWriter("C:\\Users\\Thomas Elbo\\Documents\\GitHub\\datakomcdio/store.txt");
			BufferedWriter out = new BufferedWriter(fstreamWrite);
			out.write(sb.toString());
			out.close(); // closes writer


			textIn.close(); //This closes inputStream

		} catch (Exception e) {// skal nok lave en bedre exception end dette
			System.err.println("Fejl: " + e.getMessage());
		}
	}

	public void loadList() throws IOException{
		String[] items = new String[16];
		String[] parts;

		FileInputStream textIn =  new FileInputStream("C:\\Users\\Thomas Elbo\\Documents\\GitHub\\datakomcdio/store.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(textIn));
		int i = 0;
		String lineRead;
		while((lineRead = br.readLine()) != null){
			items[i] = lineRead;
			i++;
		}

		for (int j = 0; j < items.length; j++) {
			parts = items[j].split(",");
			int itemNo = Integer.parseInt(parts[0]);
			int amount = Integer.parseInt(parts[2]);
			stock.add(new Items(itemNo,parts[1],amount));
		}
		br.close();
	}

	public void changeAmount(String items,double amount){
		for (int i = 0; i < stock.size(); i++) {
			if(items.equals(stock.get(i).getItem())){
				stock.get(i).setAmount(amount);
			}
		}
	}

	public Items getItem(int itemNo){
		for (int i = 0; i < stock.size(); i++) {
			if(itemNo == stock.get(i).getItemNo()){
				return stock.get(i);
			}

		}
		return null;
	}


}