package main;

import java.io.*;

public class MyFileReader {
    public static String readFileByChars(String fileName) {
        try {
            String all = "";
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str;
            while ((str = in.readLine()) != null) {

                all += str;
            }

            System.out.println(all);
            return all;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
