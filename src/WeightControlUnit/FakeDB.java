package WeightControlUnit;




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/*
 * Klassen fungerer som en database, og skriver til de to filer log.txt og store.txt.
 * log.txt er en log fil og alt hvad operatøren gør skal logges.
 * store.txt er varelager databasen. Indeholder et antal råvarer, og det er mængden af disse der skal
 * holdes styr på.
 */

public class FakeDB {

	
	private ArrayList<Items> stock  = new ArrayList<Items>();
	private String			 filepathEclipse   = "";

	private String			 filepath = filepathEclipse;

	// konstruktør til at skrive i loggen, denne fil skal altid forlænges
	public FakeDB(){
		try {
			loadList();
		} catch (IOException e) {
			System.out.println("Database filen findes ikke");
		}
	}

	
	public void logWriter(int oprID, String itemName, double netto, double remaining) throws IOException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		
		
		
		FileWriter fw = new FileWriter("log.txt", true);
		PrintWriter printText = new PrintWriter(fw);

		printText.printf("%s" + "%n", dateFormat.format(cal.getTime()) + "," +oprID + "," + netto + "," + remaining );
		printText.close();
	}

	// kan lave en DBexception, der viser fejl hvis commodity ikke find

	public void changeStoreText() throws IOException{
		try {

			StringBuilder	sb	   = new StringBuilder();
		

				for (int i = 0; i < stock.size(); i++) {
					sb.append(stock.get(i).getItemNo() + "," + stock.get(i).getItem() + "," + stock.get(i).getAmount() );
					sb.append("\n");
				}


			FileWriter fstreamWrite = new FileWriter(filepath + "store.txt");
			BufferedWriter out = new BufferedWriter(fstreamWrite);
			out.write(sb.toString());

			out.close(); // closes writer

		

		} catch (Exception e) {// skal nok lave en bedre exception end dette
			System.err.println("Fejl: " + e.getMessage());
		}
	}

	public void loadList() throws IOException{
		String[] items = new String[8];
		String[] parts;

		FileInputStream textIn =  new FileInputStream(filepath + "store.txt");
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
			double amount = Double.parseDouble(parts[2]);
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


