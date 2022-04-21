package utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.UUID;

public class Utils {
    private final static Scanner scanner = new Scanner(System.in);
    private final static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final static String fileCount = "src/main/resources/Count.txt";
    //method
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
    public static SimpleDateFormat getSimpleDateFormat(){
        return sdf;
    }
    public static String getFileCountPath(){
        return Utils.fileCount;
    }
    public static Scanner getScanner(){
        return Utils.scanner;
    }
    public static void setCount(int pos) throws IOException {
        File file = new File(Utils.getFileCountPath());
        Scanner scan = new Scanner(file);
        String fileCount = scan.nextLine();
        String countString = fileCount.split("#")[pos];
        String[] parts = countString.split(":");
        int count = Integer.parseInt(parts[1]) + 1;
        fileCount = fileCount.replaceFirst(countString,parts[0] + ":" + count);
        scan.close();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(fileCount);
        fileWriter.close();
    }
    public static int getCount(int pos) throws FileNotFoundException {
        File file = new File(Utils.getFileCountPath());
        Scanner scan = new Scanner(file);
        String fileCount = scan.nextLine();
        scan.close();
        return Integer.parseInt(fileCount.split("#")[pos].split(":")[1]);
    }
    public static Boolean checkValidDate(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
