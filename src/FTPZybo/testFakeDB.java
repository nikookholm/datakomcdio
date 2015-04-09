package FTPZybo;
import java.io.IOException;


public class testFakeDB {

	public static void main(String[] args) throws IOException {
		FakeDB fdb = new FakeDB();
		fdb.loadList();
		fdb.changeAmount("text1", 100);
		fdb.changeStoreText();
		System.out.println(fdb.getItem(144628));
        

	}

}
