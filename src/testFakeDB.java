import java.io.IOException;


public class testFakeDB {

	public static void main(String[] args) throws IOException {
		FakeDB fdb = new FakeDB("store.txt", false);

		 fdb.changeStoreText("text3", 5);

	}

}
