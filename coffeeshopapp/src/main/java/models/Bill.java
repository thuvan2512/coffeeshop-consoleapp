package models;

import utils.Utils;

import java.util.ArrayList;
import java.util.Date;

public class Bill {
    private String billID;
    private String tableID;
    private ArrayList<Order> listOrder = new ArrayList<>();
    private boolean paymentStatus = false;
    private Date initDate;
    {
        initDate = new Date();
        billID = Utils.getUUID();
    }
    //getter and setter

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }
    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }
    public Date getInitDate() {
        return initDate;
    }
    public String getTableID() {
        return tableID;
    }
    public String getBillID() {
        return billID;
    }
    public void setBillID(String billID) {
        this.billID = billID;
    }
    public ArrayList<Order> getListOrder() {
        return listOrder;
    }
    public void setListOrder(ArrayList<Order> listOrder) {
        this.listOrder = listOrder;
    }
    public boolean getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    //constructor
    public Bill(){

    }
    public Bill(String tableID){
        this.tableID = tableID;
    }
    //method
    public double getTotalPriceOfBill(){
        double totalPrice = 0;
        for (int i = 0;i < this.listOrder.size();i++){
            totalPrice += this.listOrder.get(i).getTotalPrice();
        }
        return totalPrice;
    }
}
