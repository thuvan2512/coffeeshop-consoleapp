package models;

import base.IModel;
import services.TableService;
import utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Table implements IModel {
    private String pk;
    private static int count = 0;
    {
        try {
            Table.setCount(Utils.getCount(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pk = String.format("B%03d",++count);
    }
    private int capacity = 0;
    private boolean isEmpty = true;

    //constructor
    public Table(int capacity) {
        this.capacity = capacity;
    }
    public Table(){
    }

    //setter and getter

    public String getPk() {
        return pk;
    }
    public void setPk(String pk) {
        this.pk = pk;
    }
    public static void setCount(int cout) {
        Table.count = cout;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public boolean isEmpty() {
        return isEmpty;
    }
    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
    public static String getState(boolean isEmpty){
        if(isEmpty)
            return "is empty";
        return "no empty";
    }
    // method
    @Override
    public void show(){
        System.out.println(this);
    }
    @Override
    public void create() {
        try {
            do {
                System.out.print("Please enter the capacity: ");
                this.capacity = Integer.parseInt(Utils.getScanner().nextLine());
                if (this.capacity <= 0){
                    System.out.println("value cannot be less than 0");
                }
            }while (this.capacity <= 0);
            Utils.setCount(1);
            TableService.getListTable().add(this);
            this.writeIntoFile();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void writeIntoFile(){
        try {
            File file = new File(TableService.getFileTable());
            FileWriter fileWriter = new FileWriter(file,true);
            int emp = this.isEmpty() ? 1:0;
            fileWriter.write(String.format("%s#%d#%d\n", this.getPk(),this.getCapacity(),emp));
            // PK # Capacity # isEmpty
            fileWriter.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public String toString(){
        return String.format("Table ID: %s\nCapacity: %d\nstatus: %s\n",this.pk,this.capacity,Table.getState(this.isEmpty));
    }
}
