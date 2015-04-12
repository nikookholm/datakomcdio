package WeightControlUnit;

import java.util.Scanner;
import Common.*;

public class WeightControlUnit {

    private int password = 1234;
    private int cOprNR = 22;
    private int oprNR;
    FakeDB DB = new FakeDB();
    WCU_TUI TUI = new WCU_TUI();
    TCPConnector TCPC;
    
    public static void main(String[] args) {
        
     WeightControlUnit WCU = new WeightControlUnit();
        
    }

    public boolean login() {
        
        
        TCPC.send("RM20 4 \"Iner\" \"text2\" \"&3\"\r\n");
        TCPC.receive();
        String temp = TCPC.receive();
        int split = temp.lastIndexOf(" ");
        try{
        oprNR = Integer.parseInt(temp.substring(split+1));
        }catch(Exception E){
            
        }
        System.out.println(oprNR);
        
    }

    public void OperateWeight(){
        while(login()){
         //   checkItem();
            System.out.println("Hertil");
            SendInstruction("Hej");
        //    ChangeAmount(1,1);
        }
    }
    public void checkItem() {
    //    TCPC.send();
    //    Items Item = DB.getItem(itemNumber);
       
     // if(!CorrectItem(Item)){
      //    OperateWeight();  }
       }    
        

 
    

    public void SendInstruction(String instruc) {
        System.out.println("Her");
        TCPC.send("D \"Hi \" \r \n");
        
    }

    public void ChangeAmount(int itemNumber,int amount) {
           Items Item = DB.getItem(itemNumber);
           if(!TUI.CorrectItem(Item)){
               OperateWeight();
           }
          TUI.ChangeAmount(Item);
    }

    public boolean CorrectItem(Items Item){
        System.out.println("The item you got was" + Item.getItem());
        System.out.println("The amount: " + Item.getAmount());
        System.out.println("Item number: " + Item.getItemNo());
        
      
      return true;  
    }
    
    public WeightControlUnit(){
       TCPC = new TCPConnector("localhost",4567);
     try{
       TCPC.connect();
    
     }catch(Exception E){
               
               }
     OperateWeight();
     
    }
}
