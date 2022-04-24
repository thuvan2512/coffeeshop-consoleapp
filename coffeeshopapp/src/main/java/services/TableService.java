package services;

import models.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TableService {
    private static ArrayList<Table> listTable;
    private final static String fileTable = "src/main/resources/Tables.txt";
    static {
        try {
            TableService.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileTable() {
        return fileTable;
    }

    public static void readFile(){
        try {
            TableService.listTable = new ArrayList<Table>();
            File file = new File(TableService.fileTable);
            Scanner scan = new Scanner(file);
            while (scan.hasNext()){
                String table = scan.nextLine();
                if(table.contains("#") == true) {
                    String[] result = table.split("#");
                    TableService.listTable.add(new Table(Integer.parseInt(result[1])));
                    TableService.listTable.get(TableService.listTable.size() - 1).setPk(result[0]);
                    boolean emp = Integer.parseInt(result[2]) == 1 ? true:false;
                    TableService.listTable.get(TableService.listTable.size() - 1).setEmpty(emp);
                }
            }
            scan.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public static void writeListIntoFile(){
        try {
            File file = new File(TableService.fileTable);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.close();
            TableService.listTable.forEach(tb ->{
                tb.writeIntoFile();
            });
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<Table> getListTable() {
        return TableService.listTable;
    }
    public static void showListTable(ArrayList<Table> list, boolean checkIsEmpty){
        if(list.size() == 0){
            System.out.println(">>> List table is empty <<<");
            return;
        }
        if (checkIsEmpty){
            for(int i = 0; i < list.size();i++){
                if (list.get(i).isEmpty()){
                    System.out.println("------------------");
                    list.get(i).show();
                }
                if(i == list.size() - 1){
                    System.out.println("------------------");
                }
            }
        }
        else {
            for(int i = 0; i < list.size();i++){
                System.out.println("------------------");
                list.get(i).show();
                if(i == list.size() - 1){
                    System.out.println("------------------");
                }
            }
        }
    }
    public static int findTableByPK(String pk){
        int index = -1;
        for (int i = 0; i < TableService.listTable.size(); i++){
            if (TableService.listTable.get(i).getPk().equals(pk)){
                index = i;
                break;
            }
        }
        return index;
    }
    public static Table getTableByID(String tableID){
        for (int i = 0; i < TableService.listTable.size(); i++){
            if (TableService.listTable.get(i).getPk().equals(tableID)){
                return listTable.get(i);
            }
        }
        return null;
    }
    public static void updateTableInfoByID(String pk, int capacity, boolean isEmpty) throws IOException {
        if(TableService.findTableByPK(pk) != -1){
            Table table = TableService.listTable.get(TableService.findTableByPK(pk));
            table.setEmpty(isEmpty);
            table.setCapacity(capacity);
            TableService.writeListIntoFile();
        }
    }
    public static void deleteTableByID(String pk){
        TableService.listTable.remove(TableService.findTableByPK(pk));
        TableService.writeListIntoFile();
    }
    public static ArrayList<Table> searchListTableByCapacity(int capacity, int option){
        ArrayList<Table> kq = new ArrayList<>();
        switch (option){
            case 1:
                TableService.listTable.forEach(tb -> {
                    if(capacity == tb.getCapacity()){
                        kq.add(tb);
                    }
                });
                break;
            case 2:
                TableService.listTable.forEach(tb -> {
                    if(capacity <= tb.getCapacity()){
                        kq.add(tb);
                    }
                });
                break;
            case 3:
                TableService.listTable.forEach(tb -> {
                    if(capacity >= tb.getCapacity()){
                        kq.add(tb);
                    }
                });
                break;
        }
        return kq;
    }
}
