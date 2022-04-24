package models;

import enumerate.State;
import enumerate.Time;
import services.ProductService;
import services.StaffService;
import utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;

public class Food extends Product {
    private boolean isVegetarian = true;

    //constructor
    public Food(String name, State state, double price, Time timeOfSale, boolean isVegetarianFood) {
        super(name, state, price, timeOfSale);
        this.isVegetarian = isVegetarianFood;
    }
    public Food(){}
    public boolean getIsVegetarian() {
        return isVegetarian;
    }
    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }
    @Override
    public Food create(String foodString){
        try {
            String[] result = foodString.split("#");
            this.pk = Integer.parseInt(result[0]);
            this.name = result[1];
            this.price = Double.parseDouble(result[2]);
            this.state = State.getValueByInt(Integer.parseInt(result[3]));
            this.timeOfSale = Time.getValueByInt(Integer.parseInt(result[4]));
            this.isVegetarian = Boolean.parseBoolean(result[5]);
            // PK # Name # price # state # timeOfSale # isVegatarian
        }catch (Exception ex2){
            System.out.println(ex2.getMessage());
        }
        return this;
    }
    @Override
    public void create(){
        super.create();
        int numCan;
        try {
            do {
                System.out.print("Is it vegetarian? (1-Yes/ 2-No): ");
                numCan = Integer.parseInt(Utils.getScanner().nextLine());
                if(numCan < 1 || numCan > 2)
                    System.out.println("Invalid value. Try again!!!");
            }while (numCan < 1 || numCan > 2);
        }catch (Exception ex){
            numCan = -1;
            System.out.println(ex.getMessage());
        }
        if (numCan >= 1 && numCan <= 2){
            this.isVegetarian = (numCan==1)?true:false;
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
    public void writeIntoFile(){
        try {
            File file = new File(ProductService.getFileProduct());
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write(String.format("%d#%s#%.0f#%d#%d#%b#%s#\n",this.getPk(),this.getName(),
                    this.getPrice(),this.getState().getInt(),this.getTimeOfSale().getInt(),this.getIsVegetarian(),this.getClass().getName()));
            // PK # Name # price # state # timeOfSale # isVegetarian # Class
            fileWriter.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public String toString(){
        return super.toString() + String.format("Is it vegetarian? %b", this.isVegetarian);
    }
}
