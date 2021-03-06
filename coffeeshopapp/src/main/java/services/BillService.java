package services;

import models.Bill;
import models.Order;
import models.Table;
import utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class BillService{
    private static ArrayList<Bill> listBillUnpaid = new ArrayList<>();
    private static ArrayList<Bill> listBillPaid = new ArrayList<>();
    private final static String fileBill = "src/main/resources/Bills.txt";
    static {
        try {
            BillService.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileBill() {
        return fileBill;
    }

    public static void createBill(String tableId, ArrayList<Order> listOrder) {
        if (listOrder != null){
            Table table = TableService.getListTable().get(TableService.findTableByPK(tableId));
            table.setEmpty(false);
            TableService.getListTable().set(TableService.findTableByPK(tableId),table);
            TableService.writeListIntoFile();
            Bill bill = new Bill(tableId);
            bill.setListOrder(listOrder);
            BillService.listBillUnpaid.add(bill);
            bill.writeBillIntoFile();
        }
    }
    public static void readFile(){
        BillService.listBillUnpaid = new ArrayList<>();
        BillService.listBillPaid = new ArrayList<>();
        try {
            File file = new File(fileBill);
            Scanner scan = new Scanner(file);
            while (scan.hasNext()){
                String bill = scan.nextLine();
                if(bill.contains("#") == true) {
                    ArrayList<Order> listOrder = new ArrayList<>();
                    String[] result = bill.split("#");
                    String[] resultListOrder = result[4].split("@");
                    for (int i = 0;i < resultListOrder.length;i++){
                        String[] resultOder = resultListOrder[i].split("-");
                        listOrder.add(new Order(Integer.parseInt(resultOder[0]),result[1],Integer.parseInt(resultOder[1])));
                    }
                    Bill tempBill = new Bill();
                    tempBill.setBillID(result[0]);
                    tempBill.setTableID(result[1]);
                    tempBill.setInitDate(Utils.getSimpleDateFormat().parse(result[2]));
                    tempBill.setPaymentStatus(Boolean.parseBoolean(result[3]));
                    tempBill.setListOrder(listOrder);
                    if (Boolean.parseBoolean(result[3]) == true){
                        BillService.listBillPaid.add(tempBill);
                    }
                    else {
                        BillService.listBillUnpaid.add(tempBill);
                    }
                }
            }
            scan.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void writeListIntoFile(){
        try {
            File file = new File(fileBill);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.close();
            BillService.listBillUnpaid.forEach(bill ->{
                bill.writeBillIntoFile();
            });
            BillService.listBillPaid.forEach(bill ->{
                bill.writeBillIntoFile();
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Bill getBillUnpaidByTableID(String tableID){
        for (int i = 0; i < BillService.listBillUnpaid.size(); i++){
            if (BillService.listBillUnpaid.get(i).getTableID().equals(tableID)){
                return BillService.listBillUnpaid.get(i);
            }
        }
        return null;
    }
    public static void removeBillUnpaidByTableID(String tableID){
        for (int i = 0; i < BillService.listBillUnpaid.size(); i++){
            if (BillService.listBillUnpaid.get(i).getTableID().equals(tableID)){
                BillService.listBillUnpaid.remove(i);
                return;
            }
        }
    }
    public static void payByTableID(String tableID) throws IOException {
        Bill bill = getBillUnpaidByTableID(tableID);
        if (bill != null){
            bill.setPaymentStatus(true);
            BillService.listBillPaid.add(bill);
            BillService.removeBillUnpaidByTableID(tableID);
            BillService.writeListIntoFile();

            Table table = TableService.getListTable().get(TableService.findTableByPK(tableID));
            table.setEmpty(true);
            TableService.getListTable().set(TableService.findTableByPK(tableID),table);
            TableService.writeListIntoFile();
        }
    }
    public static void showPaymentDetailByTableID(String tableID){
        Bill bill = getBillUnpaidByTableID(tableID);
        if (bill != null){
            System.out.printf("Table ID: %s\n",bill.getTableID());
            bill.getListOrder().forEach(order -> {
                System.out.printf("============\nProduct name: %s\nUnit price: %,.0f VND\nQuantity: %d\n", ProductService.findProductByID(order.getProductID()).getName(),
                        ProductService.findProductByID(order.getProductID()).getPrice(), order.getQuantity());
            });
            System.out.printf("\nTotal price: %,.0f VND\n",bill.getTotalPriceOfBill());
        }else {
            System.out.println("Can't found the information!!!");
        }
    }
    public static void alterListOrderOnBill(String tableID, ArrayList<Order> listOrder) throws IOException {
        Bill bill = getBillUnpaidByTableID(tableID);
        if (bill != null){
            ArrayList<Order> orders = bill.getListOrder();
            orders.addAll(listOrder);
            bill.setListOrder(orders);
            BillService.writeListIntoFile();

        }
    }
    public static void revenueStatistics(int month){
        double totalRevenue = 0;
        for (int i = 0; i < BillService.listBillPaid.size(); i++){
            if (BillService.listBillPaid.get(i).getInitDate().getMonth() + 1 == month && BillService.listBillPaid.get(i).getInitDate().getYear() == new Date().getYear()){
                totalRevenue += BillService.listBillPaid.get(i).getTotalPriceOfBill();
            }
        }
        System.out.printf("REVENUE STATISTICS IN %d / %d\nTOTAL STATISTICS: %,.0f VND\n",month,new Date().getYear() + 1900, totalRevenue);
    }
    public static void revenueStatistics(Date from, Date to){
        double totalRevenue = 0;
        for (int i = 0; i < BillService.listBillPaid.size(); i++){
            Date initDate = BillService.listBillPaid.get(i).getInitDate();
            if ( (initDate.equals(from) || initDate.after(from)) && (initDate.before(to) || initDate.equals(to))){
                totalRevenue += BillService.listBillPaid.get(i).getTotalPriceOfBill();
            }
        }
        System.out.printf("REVENUE STATISTICS FROM %s TO %s\nTOTAL STATISTICS: %,.0f VND\n",
                Utils.getSimpleDateFormat().format(from),Utils.getSimpleDateFormat().format(to),totalRevenue);
    }
}
