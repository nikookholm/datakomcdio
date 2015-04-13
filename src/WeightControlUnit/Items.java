package WeightControlUnit;



public class Items {

	String item;
	double amount;
	int itemNo;

	public Items(int itemNo, String item, int amount){
		this.itemNo = itemNo;
		this.amount = amount;
		this.item = item;
	}

	public String getItem(){
		return item;
	}

	public double getAmount(){
		return amount;
	}
	public String toString(){
		return itemNo + " " + item + " " + amount;
	}

	public void setAmount(double amount){
		this.amount = amount;
	}
	public int getItemNo(){
		return itemNo;
	}
        public String getItemName(){
            return item;
        }


}

