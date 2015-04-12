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
            //    ChangeAmount(1,1);
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
    
    }

    public void SendInstruction(String instruc) {
        System.out.println("Her");
        TCPC.send("D \"Hi\" \r \n");

    }

    public void ChangeAmount(int itemNumber, int amount) {
        Items Item = DB.getItem(itemNumber);
        if (!TUI.CorrectItem(Item)) {
            OperateWeight();
        }
        TUI.ChangeAmount(Item);
    }

    public void CorrectItem(Items Item) {
        TCPC.send("RM20 8 \"Correct item? \"  \"text2 \" \"&3 \"\r\n");
        TCPC.receive();
        System.out.println("The item you got was " + Item.getItem());
        System.out.println("The amount: " + Item.getAmount());
        System.out.println("Item number: " + Item.getItemNo());
        String temp = TCPC.receive();
        if(!temp.equals("Yes")){
            
        checkItem();
        }
        
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
