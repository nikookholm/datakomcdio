package FTPZybo;

public class Items {

	String item;
	int amount;
	int itemNo;

	public Items(int itemNo, String item, int amount){
		this.itemNo = itemNo;
		this.amount = amount;
		this.item = item;
	}

	public String getItem(){
		return item;
	}

	public int getAmount(){
		return amount;
	}
	public String toString(){
		return itemNo + " " + item + " " + amount;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}
	public int getItemNo(){
		return itemNo;
	}


}

