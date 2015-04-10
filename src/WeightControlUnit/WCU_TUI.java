/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WeightControlUnit;

import java.util.Scanner;
/**
 *
 * @author Thomas Elbo
 */
public class WCU_TUI {
    
    FakeDB DB = new FakeDB();
    
    public boolean CorrectItem(Items Item){
        System.out.println("The item you got was" + Item.getItem());
        System.out.println("The amount: " + Item.getAmount());
        System.out.println("Item number: " + Item.getItemNo());
        
        String CorrectItem = KeyPress.nextLine();
        if(CorrectItem.equals("Yes")){
        return true;
        }else{
        return false;
        }
    }
    
    public void ChangeAmount(Items Item){
        System.out.println("Choose the correct amount: ");
        String CorrectAmount = KeyPress.nextLine();
        DB.changeAmount(Item.getItemName(), Integer.parseInt(CorrectAmount));
        try{
        DB.changeStoreText();
        }catch(Exception U){
            
        }
    }
    
    Scanner KeyPress = new Scanner(System.in);
}
