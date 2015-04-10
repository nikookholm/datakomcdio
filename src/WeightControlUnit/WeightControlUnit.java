package WeightControlUnit;

import java.util.Scanner;

public class WeightControlUnit {

    private int password = 1234;
    private int cOprNR = 998;
    FakeDB DB = new FakeDB();
    WCU_TUI TUI = new WCU_TUI();
    public static void main(String[] args) {

     WeightControlUnit WCU = new WeightControlUnit();
    
     Scanner keyPress = new Scanner(System.in);
    
        int OprNR = keyPress.nextInt();
        int temp_pass = keyPress.nextInt();
        
        
        if(WCU.login(OprNR, temp_pass)){
            
            
        }
       
    }

    public boolean login(int oprNR, int pw) {
        return true;
    }

    public void OperateWeight(){
        while(login(cOprNR, password)){
            checkItem(1);
            SendInstruction("Hej");
            ChangeAmount(1,1);
        }
    }
    public void checkItem(int itemNumber) {
        
        Items Item = DB.getItem(itemNumber);
       
        if(!TUI.CorrectItem(Item)){
          OperateWeight();  
        }    
        

        
    }

    public void SendInstruction(String instruc) {

    }

    public void ChangeAmount(int itemNumber,int amount) {
           Items Item = DB.getItem(itemNumber);
           if(!TUI.CorrectItem(Item)){
               OperateWeight();
           }
          TUI.ChangeAmount(Item);
    }

}
