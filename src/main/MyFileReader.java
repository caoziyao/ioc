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


    public static byte[] readFileByByte(String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream stream = new FileInputStream(file);

            int len = (int) file.length();
            byte[] buf = new byte[len];
            stream.read(buf);
            stream.close();
            return buf;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }
}
