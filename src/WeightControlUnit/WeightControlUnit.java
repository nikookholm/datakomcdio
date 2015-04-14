package WeightControlUnit;

import Common.*;

public class WeightControlUnit {

    FakeDB 		 DB;
    TCPConnector TCPC;
    boolean		 loggedIn = false;
    //WCU_TUI TUI = new WCU_TUI();
    
    public static void main(String[] args) {

        WeightControlUnit WCU = new WeightControlUnit();

    }
    
    public WeightControlUnit() {
        DB   		= new FakeDB();
        String host = "localhost";
        int    port = 4567;								// Skal sendes ned oppefra
        TCPC 		= new TCPConnector("localhost", 4567);
        
        try {
            if (TCPC.connect())
            {
            	System.out.println("Forbindelse oprettet til " + host + " (Port " + port + ")");
            }            
        } catch (Exception E) {
        	System.out.println("Forbindelse kunne ikke oprettes ...");
        }
        
        login();
        OperateWeight();

    }
    
    private String rm20Request(int type, String text)
    {
    	String rm20RequestString = "RM20 " + type + " \"" + text + "\" \" \" \" \"\r\n";
    	TCPC.send("RM20 forspørgelse: " + rm20RequestString);
    	System.out.println(rm20RequestString);
    	
    	System.out.println("RM20 svar: " + TCPC.receive());
    	
    	String receivedAnswer = null;
    	do
    	{
    		receivedAnswer = TCPC.receive();
    	} while (receivedAnswer.trim() == "");
    	
    	System.out.println("RM20 Svar: " + receivedAnswer);
    	return receivedAnswer;
    }

    public boolean login() {

	    String temp  = rm20Request(4, "Operator");
        int    split = temp.lastIndexOf(" ");
        
        try {
            int oprNR = Integer.parseInt(temp.substring(split + 1));
            loggedIn = true;
            return true;
        } catch (Exception E) {
        	loggedIn = false;
            return false;
        }

    }

    public void OperateWeight() {
    	
        while (loggedIn) {
            System.out.println("Here?");        	
            checkItem();
            SendInstruction("Hej");
            
        }
    }

    public void checkItem() {
    	
    	int itemNo;
    	
        String temp = rm20Request(4, "ItemNumber");
        System.out.println("Modtaget svar: " + temp);
        
        int split = temp.lastIndexOf(" ");
        try {
            itemNo = Integer.parseInt(temp.substring(split + 1));
            Items Item = DB.getItem(itemNo);
            TCPC.send("D \""+ Item.getItemName() +" \" \r\n");
            CorrectItem(Item);
        } catch (Exception e){}
    
    }

    public void SendInstruction(String instruc) {
        TCPC.send("D \"Hi\" \r \n");
        TCPC.receive();
    }

    public void ChangeAmount(Items Item) {
       
        double previousAmount = Item.getAmount();
        TCPC.send("S\r\n");
        
        String inc_weight = TCPC.receive();
        String[] weight_array = inc_weight.split(" ");
        
        double weight = Double.parseDouble(weight_array[2]); 
        
        DB.changeAmount(Item.getItemName(), weight);
        try{
        	DB.changeStoreText();
        } catch(Exception E ){}
        
    }

    public void CorrectItem(Items Item) {
    	
        String temp = rm20Request(8, "CorrectItem?");
        
        if(!temp.equals("Yes")){
	        checkItem();
        }
        
        ChangeAmount(Item);
        
    }

}