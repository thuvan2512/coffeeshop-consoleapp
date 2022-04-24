package models;

import base.IModel;
import enumerate.State;
import enumerate.Time;
import utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Product implements IModel {
    protected int pk;
    protected static int count = 0;
    {
        try {
            Product.setCount(Utils.getCount(2));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        pk = ++count; // primary key auto increase
    }
    protected String name;
    protected State state;
    protected double price;
    protected Time timeOfSale;
    // Constructor
    public Product() {

    }
    public Product(String name, State state, double price, Time timeOfSale) {
        this.name = name;
        this.state = state;
        this.price = price;
        this.timeOfSale = timeOfSale;
    }

    // Setter and getter


    public int getPk() {
        return pk;
    }
    public void setPk(int pk) {
        this.pk = pk;
    }
    public static void setCount(int count) {
        Product.count = count;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Time getTimeOfSale() {
        return timeOfSale;
    }
    public void setTimeOfSale(Time timeOfSale) {
        this.timeOfSale = timeOfSale;
    }


    // method
    @Override
    public void show(){
        System.out.println(this);
    }
    @Override
    public void create(){
        int numTime;
        int numState;
        System.out.print("Enter name of product: ");
        this.name = Utils.getScanner().nextLine();
        try {
            do {
                System.out.print("Enter price: ");
                this.price = Double.parseDouble(Utils.getScanner().nextLine());
                if (this.price < 0){
                    System.out.println("Invalid value. Try again!!!");
                }
            }while (this.price < 0);
            do {
                System.out.print("Choose numTime of sale this product (1-MORNING/ 2-NOON/ 3-EVENING/ 4-ALL DAY): ");
                numTime = Integer.parseInt(Utils.getScanner().nextLine());
                if(numTime < 1 || numTime > 4)
                    System.out.println("Invalid value. Try again!!!");
                else {
                    this.timeOfSale = Time.getValueByInt(numTime);
                }
            }while (numTime < 1 || numTime > 4);
            do {
                System.out.print("Choose state of this product (1-In stock/ 2-Out of stock): ");
                numState = Integer.parseInt(Utils.getScanner().nextLine());
                if(numState < 1 || numState > 2)
                    System.out.println("Invalid value. Try again!!!");
                else {
                    this.state = State.getValueByInt(numState);
                }
            }while (numState < 1 || numState > 2);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    @Override
    public void writeIntoFile(){
        return;
    }
    @Override
    public String toString(){
        return String.format("Product ID: %d\nName: %s\nState: %s\nPrice: %,.0f VND\nTime of sale: %s\n",
                this.pk, this.name, this.state.getValue(), this.price, this.timeOfSale.getValue());
    }

    public Product create(String productString){
        return this;
    }


}
