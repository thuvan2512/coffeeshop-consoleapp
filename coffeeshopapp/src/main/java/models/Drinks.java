package models;

import enumerate.State;
import enumerate.Time;
import services.ProductService;
import utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Drinks extends Product {
    private boolean isAddIce = true;

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
            try {
                this.writeIntoFile();
                ProductService.getListProduct().add(this);
                Utils.setCount(2);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    @Override
    public Drinks create(String foodString){
        try {
            String[] result = foodString.split("#");
            this.pk = Integer.parseInt(result[0]);
            this.name = result[1];
            this.price = Double.parseDouble(result[2]);
            this.state = State.getValueByInt(Integer.parseInt(result[3]));
            this.timeOfSale = Time.getValueByInt(Integer.parseInt(result[4]));
            this.isAddIce = Boolean.parseBoolean(result[5]);
            // PK # Name # price # state # timeOfSale # isAddIce
        }catch (Exception ex2){
            System.out.println(ex2.getMessage());
        }
        return this;
    }
    @Override
    public void writeIntoFile(){
        try {
            File file = new File(ProductService.getFileProduct());
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write(String.format("%d#%s#%.0f#%d#%d#%b#%s#\n",this.getPk(),this.getName(),
                    this.getPrice(),this.getState().getInt(),this.getTimeOfSale().getInt(),this.getIsAddIce(),this.getClass().getName()));
            // PK # Name # price # state # timeOfSale # isAddIce # Class
            fileWriter.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public String toString(){
        return super.toString() + String.format("Can add ice? %b", this.isAddIce);
    }
}
