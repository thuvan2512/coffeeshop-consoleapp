package services;


import enumerate.State;
import enumerate.Time;
import models.Drinks;
import models.Food;
import models.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductService{
    private static ArrayList<Product> listProduct = new ArrayList<>();
    private final static String fileProduct = "src/main/resources/Products.txt";
    static {
        try {
            ProductService.readFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static ArrayList<Product> getListProduct() {
        return listProduct;
    }
    public static String getFileProduct() {
        return fileProduct;
    }

    public static Product findProductByID(int pk){
        for (int i = 0; i < ProductService.listProduct.size(); i++){
            if (ProductService.listProduct.get(i).getPk() == pk){
                Product product = ProductService.listProduct.get(i);
                return product;
            }
        }
        return null;
    }
    public static void deleteProductByID(int pk) throws IOException {
        if(ProductService.findProductByID(pk) != null){
            ProductService.getListProduct().remove(ProductService.findProductByID(pk));
            System.out.println("Delete product successfully!!!!");
            ProductService.writeListIntoFile();
        }
        else {
            System.out.println("Delete product failed!!!!");
        }
    }
    public static void showListProduct(ArrayList<Product> list){
        if (list != null){
            if (list.size() !=0){
                list.forEach(product -> {
                    product.show();
                    System.out.println("===========");
                });
            }else {
                System.out.println("Empty product list!!!");
            }
        }else{
            System.out.println("Empty product list!!!");
        }
    }
    public static ArrayList<Product> searchProducts(String keys){
        ArrayList<Product> kq = new ArrayList<>();
        ProductService.listProduct.forEach(f ->{
            if(f.getName().toLowerCase().contains(keys.toLowerCase().strip())){
                kq.add(f);
            }
        });
        return kq;
    }
    public static ArrayList<Product> searchProducts(double from, double to){
        ArrayList<Product> kq = new ArrayList<>();
        ProductService.listProduct.forEach(f ->{
            if(f.getPrice() >= from && f.getPrice() <= to){
                kq.add(f);
            }
        });
        return kq;
    }
    public static ArrayList<Product> reverseListProduct(boolean increase){
        ArrayList<Product> kq;
        kq = (ArrayList<Product>) ProductService.listProduct.clone();
        int k = (increase)? 1:-1;
        kq.sort((f1,f2) -> {
            if (f1.getPrice() > f2.getPrice()){
                return 1*k;
            }else if (f1.getPrice() < f2.getPrice()){
                return -1*k;
            }
            return 0;
        });
        return kq;
    }
    public static void updateState(int pk,State state){
        if(ProductService.findProductByID(pk) != null){
            Product product = ProductService.findProductByID(pk);
            product.setState(state);
            ProductService.writeListIntoFile();
        }
    }

    public static void readFile() {
        try {
            ProductService.listProduct = new ArrayList<Product>();
            File file = new File(fileProduct);
            Scanner scan = new Scanner(file);
            while (scan.hasNext()){
                String product = scan.nextLine();
                if(product.contains("#") == true) {
                    String[] result = product.split("#");
                    Class c = Class.forName(result[6]);
                    Product p = (Product) c.getConstructor().newInstance();
                    ProductService.listProduct.add(p.create(product));
                }
            }
            scan.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public static void writeListIntoFile() {
        try {
            File file = new File(fileProduct);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.close();
            ProductService.listProduct.forEach(pd ->{
                pd.writeIntoFile();
            });
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
