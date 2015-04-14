package WeightControlUnit;

import Common.*;

public class WeightControlUnit {

    FakeDB db;
    TCPConnector tcp;
    boolean loggedIn = false;
    //WCU_TUI TUI = new WCU_TUI();
    int loggedOperator = 0;
    double tarer = 0;
    double brutto = 0;
    Items Weight_item;

    public static void main(String[] args) {

        new WeightControlUnit();

    }

    public WeightControlUnit() {
        db = new FakeDB();
        String host = "localhost";
        int port = 4567;								// Skal sendes ned oppefra
        tcp = new TCPConnector("localhost", 4567);
        Thread input = new Thread(new InputThread(), "");
        input.start();

        try {
            if (tcp.connect()) {
                System.out.println("Forbindelse oprettet til " + host + " (Port " + port + ")");
            }
        } catch (Exception e) {
            System.out.println("Forbindelse kunne ikke oprettes ...");
        }

        login();
        operateWeight();

    }

    private String rm20Request(int type, String text) {
        String rm20RequestString = "RM20 " + type + " \"" + text + "\" \" \" \" \"\r\n";
        tcp.send(rm20RequestString);
        System.out.println("RM20 forspørgelse: " + rm20RequestString);

        System.out.println("RM20 svar: " + tcp.receive());

        String receivedAnswer = null;
        do {
            receivedAnswer = tcp.receive();
        } while (receivedAnswer.trim() == "");

        System.out.println("RM20 Svar: " + receivedAnswer);
        return receivedAnswer;
    }

    public boolean login() {

        String temp = rm20Request(4, "Operator");
        int split = temp.lastIndexOf(" ");

        try {
            loggedOperator = Integer.parseInt(temp.substring(split + 1));
            loggedIn = true;
            return true;
        } catch (Exception e) {
            loggedIn = false;
            return false;
        }

    }

    public void operateWeight() {

        while (loggedIn) {

            checkItem();
            sendInstruction("Place bowl");
            tarerWeight(true);
            sendInstruction("Remove bowl");
            NettoWeight();
            sendInstruction("Remove tara and netto");
            tarerWeight(false);
            // Minus brutto
            BruttoControl();
            logItem();

        }
    }

    public void tarerWeight(boolean save) {
        tcp.send("T\r\n");
        String inc_tarer = tcp.receive();

        if (save) {
            int pos = inc_tarer.lastIndexOf(" ");
            inc_tarer = inc_tarer.substring(0, pos);
            pos = inc_tarer.lastIndexOf(" ");
            inc_tarer = inc_tarer.substring(pos);
            System.out.println(inc_tarer);
            tarer = Double.parseDouble(inc_tarer);
        }

    }

    public void NettoWeight() {
        tcp.send("S\r\n");
        String inc_brutto = tcp.receive();
        int pos = inc_brutto.lastIndexOf(" ");
        inc_brutto = inc_brutto.substring(0, pos);
        pos = inc_brutto.lastIndexOf(" ");
        inc_brutto = inc_brutto.substring(pos);
        System.out.println(inc_brutto);
        brutto = Double.parseDouble(inc_brutto);
    }

    public void logItem() {
        double netto = brutto - tarer;
        if (netto >= 0) {
            rm20Request(8, "BRUTTO KONTROL OK");
            tcp.receive();
            System.out.println(Weight_item.getAmount());
            double totalRemaining = Weight_item.getAmount() - netto;
            try {
                db.changeAmount(Weight_item.getItemName(), totalRemaining);
                db.logWriter(loggedOperator, Weight_item.getItemName(), netto, totalRemaining);
            } catch (Exception E) {

            }
            changeStoreText();
        } else {
            tcp.send("D \"FEJL \"\r\n");

        }

    }

    public void BruttoControl() {
        String temp = rm20Request(8, brutto + " brutto korrekt? y/n");
        String[] message = temp.split(" ");
        if (!message[2].equals("y")) {
            if (message[2].equals("n")) {
                checkItem();
            }
            BruttoControl();

        }
    }

    public void checkItem() {

        int itemNo;

        String temp = rm20Request(4, "Press item number");
        System.out.println("Modtaget svar: " + temp);

        int split = temp.lastIndexOf(" ");
        try {
            itemNo = Integer.parseInt(temp.substring(split + 1));
            Items item = db.getItem(itemNo);
            System.out.println(item.toString() + " STRING");
            if (item == null) {
                checkItem();
            }
            tcp.send("D \"" + item.getItemName() + " \" \r\n");
            correctItem(item);

        } catch (Exception e) {
            checkItem();

        }

    }

    public void sendInstruction(String instruction) {
        rm20Request(8, instruction);
        tcp.receive();
    }

    public void changeStoreText() {

        try {
            db.changeStoreText();
        } catch (Exception e) {
        }

    }

    public void correctItem(Items item) {

        String temp = rm20Request(8, item.getItemName() + " correct item? y/n");
        System.out.println("svar " + temp);
        String[] message = temp.split(" ");

        if (message[2].equals("y")) {
            System.out.println(item.getAmount() + " SVAAR");
            Weight_item = item;

        } else {
            checkItem();
        }

    }

}
