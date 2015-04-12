package WeightControlUnit;

import java.util.Scanner;
import Common.*;

public class WeightControlUnit {

    private int password = 1234;
    private int cOprNR = 22;
    private int oprNR;
    
    FakeDB DB;
    WCU_TUI TUI = new WCU_TUI();
    TCPConnector TCPC;

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
            oprNR = Integer.parseInt(temp.substring(split + 1));
            return true;
        } catch (Exception E) {
            return false;
        }

    }

    public void OperateWeight() {
        while (login()) {
               checkItem();
            System.out.println("Hertil");
            SendInstruction("Hej");
            
        }
    }

    public void checkItem() {
         TCPC.send("RM20 4 \"CheckItem\" \"text2\" \"&3\"\r\n");
         System.out.println("CheckItem");
        TCPC.receive();
        String temp = TCPC.receive();
        int split = temp.lastIndexOf(" ");
        int itemNo = 0;
        try {
            itemNo = Integer.parseInt(temp.substring(split + 1));
        }catch(Exception E){
            
        }
        
        Items Item = DB.getItem(itemNo);
        TCPC.send("D \""+ Item.getItemName() +" \" \r\n");
        CorrectItem(Item);
    
    }

    public void SendInstruction(String instruc) {
        System.out.println("Her");
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
        }catch(Exception E ){
            
        }
        
    }

    public void CorrectItem(Items Item) {
        TCPC.send("RM20 8 \""+ Item.getItemName() +"\"  \"correct? \" \"&3 \"\r\n");
   
        System.out.println("The item you got was " + Item.getItem());
        System.out.println("The amount: " + Item.getAmount());
        System.out.println("Item number: " + Item.getItemNo());
        String temp1 =TCPC.receive();
        String temp = TCPC.receive();
        System.out.println(temp1);
        System.out.println(temp);
        if(!temp.equals("Yes")){
            
        checkItem();
        }
        ChangeAmount(Item);
        
    }

    public WeightControlUnit() {
        DB = new FakeDB();
        TCPC = new TCPConnector("localhost", 4567);
        try {
            TCPC.connect();

        } catch (Exception E) {

        }
        OperateWeight();

    }
}
