package WeightControlUnit;

import java.util.Scanner;

public class WeightControlUnit {

    static int cOprNR = 998;
    static int password = 1234;
    static Scanner keyPress = new Scanner(System.in);

    public static void main(String[] args) {

        int OprNR = keyPress.nextInt();
        int temp_pass = keyPress.nextInt();

        if (login(OprNR, temp_pass) == true) {

        }
    }

    public static boolean login(int oprNR, int pw) {
        if ((oprNR == cOprNR) && (pw == password)) {
            return true;
        } else {
            return false;
        }
    }

    public static int checkItem(int itemNumber) {
        return 0;
    }

    public void SendInstruction(String instruc) {

    }

    public void registerItem(int amount) {

    }

}

}
