package models;

import enumerate.State;
import enumerate.Time;
import utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Drinks extends Product {
    private boolean isAddIce;

    //constructor
    public Drinks(String name, State state, double price, Time timeOfSale, boolean isAddIce) {
        super(name, state, price, timeOfSale);
        this.isAddIce = isAddIce;
    }
    public Drinks(){}
    //getter and setter
    public boolean getIsAddIce() {
        return isAddIce;
    }
    public void setIsAddIce(boolean addIce) {
        isAddIce = addIce;
    }

    //method
    @Override
    public void create(){
        super.create();
        int numCan;
        try {
            do {
                System.out.print("Can add ice? (1-Yes/ 2-No): ");
                numCan = Integer.parseInt(Utils.getScanner().nextLine());
                if(numCan < 1 || numCan > 2)
                    System.out.println("Invalid value. Try again!!!");
            }while (numCan < 1 || numCan > 2);
        }catch (Exception ex){
            numCan = -1;
            System.out.println(ex.getMessage());
        }
        if (numCan >= 1 && numCan <= 2){
            this.isAddIce = (numCan==1)?true:false;
        }
    }
    @Override
    public String toString(){
        return super.toString() + String.format("Can add ice? %b", this.isAddIce);
    }
}
