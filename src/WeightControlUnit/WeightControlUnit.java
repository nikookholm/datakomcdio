package WeightControlUnit;

import Common.*;

public class WeightControlUnit {

    FakeDB 		 db;
    TCPConnector tcp;
    boolean		 loggedIn = false;
    //WCU_TUI TUI = new WCU_TUI();
    
    public static void main(String[] args) {

        new WeightControlUnit();

    }
    
    public WeightControlUnit() {
        db   		= new FakeDB();
        String host = "localhost";
        int    port = 4567;								// Skal sendes ned oppefra
        tcp 		= new TCPConnector("localhost", 4567);
        
        try {
            if (tcp.connect())
            {
            	System.out.println("Forbindelse oprettet til " + host + " (Port " + port + ")");
            }            
        } catch (Exception e) {
        	System.out.println("Forbindelse kunne ikke oprettes ...");
        }
        
        login();
        operateWeight();

    }
    
    private String rm20Request(int type, String text)
    {
    	String rm20RequestString = "RM20 " + type + " \"" + text + "\" \" \" \" \"\r\n";
    	tcp.send("RM20 forspørgelse: " + rm20RequestString);
    	System.out.println(rm20RequestString);
    	
    	System.out.println("RM20 svar: " + tcp.receive());
    	
    	String receivedAnswer = null;
    	do
    	{
    		receivedAnswer = tcp.receive();
    	} while (receivedAnswer.trim() == "");
    	
    	System.out.println("RM20 Svar: " + receivedAnswer);
    	return receivedAnswer;
    }

    public boolean login() {

	    String temp  = rm20Request(4, "Operator");
        int    split = temp.lastIndexOf(" ");
        
        try {
            int oprNr = Integer.parseInt(temp.substring(split + 1));
            loggedIn = true;
            return true;
        } catch (Exception e) {
        	loggedIn = false;
            return false;
        }

    }

    public void operateWeight() {
    	
        while (loggedIn) {        	
        	
            checkItem();
            sendInstruction("Hej");
            
        }
    }

    public void checkItem() {
    	
    	int itemNo;
    	
        String temp = rm20Request(4, "ItemNumber");
        System.out.println("Modtaget svar: " + temp);
        
        int split = temp.lastIndexOf(" ");
        try
        {
            itemNo = Integer.parseInt(temp.substring(split + 1));
            Items item = db.getItem(itemNo);
            tcp.send("D \""+ item.getItemName() +" \" \r\n");
            correctItem(item);
            
        } catch (Exception e) {}
    
    }

    public void sendInstruction(String instruction) {
        tcp.send("D \"" + instruction + "\" \r \n");
        tcp.receive();
    }

    public void changeAmount(Items item) {
       
        double previousAmount = item.getAmount();
        tcp.send("S\r\n");
        
        String inc_weight = tcp.receive();
        String[] weight_array = inc_weight.split(" ");
        
        double weight = Double.parseDouble(weight_array[2]); 
        
        db.changeAmount(item.getItemName(), weight);
        try
        {
        	db.changeStoreText();
        } catch (Exception e) {}
        
    }

    public void correctItem(Items item) {
    	
        String temp = rm20Request(8, "CorrectItem?");
        
        if(!temp.equals("Yes")){
	        checkItem();
        }
        
        changeAmount(item);
        
    }

}