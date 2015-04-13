package WeightControlUnit;

import Common.*;

public class WeightControlUnit {

    //private int password = 1234;
    //private int cOprNR = 22;
    //private int oprNR;
    
    FakeDB 		 DB;
    TCPConnector TCPC;
    boolean		 loggedIn = false;
    //WCU_TUI TUI = new WCU_TUI();
    
    public WeightControlUnit() {
        DB   = new FakeDB();
        TCPC = new TCPConnector("localhost", 4567);
        
        try {
            TCPC.connect();
        } catch (Exception E) {}
        
        OperateWeight();

    }
    

    public static void main(String[] args) {

        WeightControlUnit WCU = new WeightControlUnit();

    }

    public boolean login() {

        TCPC.send("RM20 4 \"Operator\" \"text2\" \"&3\"\r\n");
        System.out.println("Operator sendt");
        
        TCPC.receive();
        String temp = TCPC.receive();
        
        int split = temp.lastIndexOf(" ");
        try {
            int oprNR = Integer.parseInt(temp.substring(split + 1));
            return true;
        } catch (Exception E) {
            return false;
        }

    }

    public void OperateWeight() {
        while (loggedIn) {
        	
            checkItem();
            SendInstruction("Hej");
            
        }
    }

    public void checkItem() {
    	
    	int itemNo;
    	
    	TCPC.send("RM20 4 \"CheckItem\" \"text2\" \"&3\"\r\n");
    	
        TCPC.receive();
        String temp = TCPC.receive();
        
        int split = temp.lastIndexOf(" ");
        try {
            itemNo = Integer.parseInt(temp.substring(split + 1));
            Items Item = DB.getItem(itemNo);
            TCPC.send("D \""+ Item.getItemName() +" \" \r\n");
            CorrectItem(Item);
        }catch(Exception e){}
    
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
    	
        TCPC.send("RM20 8 \""+ Item.getItemName() +"\"  \"correct? \" \"&3 \"\r\n");
   
        System.out.println("The item you got was " + Item.getItem());
        System.out.println("The amount: " + Item.getAmount());
        System.out.println("Item number: " + Item.getItemNo());
        
        String temp1 =TCPC.receive();
        String temp = TCPC.receive();
        
        if(!temp.equals("Yes")){
	        checkItem();
        }
        
        ChangeAmount(Item);
        
    }

}