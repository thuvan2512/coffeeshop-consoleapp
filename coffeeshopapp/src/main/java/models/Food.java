package models;

import enumerate.State;
import enumerate.Time;
import utils.Utils;

public class Food extends Product {
    private boolean isVegetarian;

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
        }
    }
    @Override
    public String toString(){
        return super.toString() + String.format("Is it vegetarian? %b", this.isVegetarian);
    }
}
