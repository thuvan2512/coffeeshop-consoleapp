package models;

import services.TableService;
import utils.Utils;

import java.io.IOException;

public class Table{
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
    public void show(){
        System.out.println(this);
    }
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
            TableService.writeTableIntoFile(this);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public String toString(){
        return String.format("Table ID: %s\nCapacity: %d\nstatus: %s\n",this.pk,this.capacity,Table.getState(this.isEmpty));
    }
}
