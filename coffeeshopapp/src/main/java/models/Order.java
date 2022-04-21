package models;

import services.ProductService;
import utils.Utils;


public class Order {
    private String OrderID;
    private int productID;
    private String tableID;
    private int quantity = 0;
    {
        OrderID = Utils.getUUID();
    }

    //getter and setter
    public String getOrderID() {
        return OrderID;
    }
    public void setOrderID(String orderID) {
        OrderID = orderID;
    }
    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
        this.productID = productID;
    }
    public String getTableID() {
        return tableID;
    }
    public void setTableID(String tableID) {
        this.tableID = tableID;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    //constructor
    public Order(){

    }
    public Order(int productID, String tableID, int quantity){
        this.productID = productID;
        this.tableID = tableID;
        this.quantity = quantity;
    }
    //method
    public double getTotalPrice(){
        return ProductService.findProductByID(this.productID).getPrice() * this.quantity;
    }

}
