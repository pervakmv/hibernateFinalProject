package utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Utils {
    private static Scanner in = new Scanner(System.in);

    public static long readKeyboardWithScannerLong(String prefStr) {
        Scanner scanner = new Scanner(System.in);

        //usinscanner
        System.out.println(prefStr);
        Long lng = in.nextLong();

        lng = in.nextLong();
        return lng;
    }

    public static String readKeyboardWithScannerString(String prefStr) {
        System.out.println(prefStr);
        String str = new String();
        str = in.next();
        return str;
    }

    public static void GetScores() {
        in.close();
    }

    public static String readFromKeyboardString(String prefStr) {


        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(reader);

        String str = new String();
        try {
            System.out.print(prefStr);
            str = br.readLine();


        } catch (IOException e) {
            System.err.println("readFromKeyboardString: Reading from keyboard failed " + prefStr);
        } finally {

            System.out.println("Без осовобождения ресурсов");
        }
        return str;
    }

}
