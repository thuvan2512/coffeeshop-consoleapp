package utils;

import enumerate.Gender;
import enumerate.State;
import models.*;
import services.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Presentation {
    public static void showMenu() throws ParseException, IOException {
        System.out.println("\n\t\t======================>-- MENU --<======================");
        System.out.println("\t\t|\t1. Staff Management");
        System.out.println("\t\t|\t2. Table Management ");
        System.out.println("\t\t|\t3. Product Management ");
        System.out.println("\t\t|\t4. View Staff's Birthday In Month  ");
        System.out.println("\t\t|\t5. Book a table");
        System.out.println("\t\t|\t6. Bill Payment ");
        System.out.println("\t\t|\t7. Revenue Statistics ");
        System.out.println("\t\t|\t0. Exit");
        System.out.println("\n\t\t==========================================================\n");
        int choose;
        try {
            do {
                System.out.print("\t\t|\t=> Your Choose: ");
                choose = Integer.parseInt(Utils.getScanner().nextLine());
                if(choose > 7 || choose < 0)
                   System.out.println(">>>>>>>INVALID. PLEASE TRY AGAIN !!! <<<<<<<");
            }while (choose > 7 || choose < 0);
        }catch (Exception ex){
            choose = -1;
            System.out.println(ex.getMessage());
        }
        if (choose <= 7 && choose >= 0){
            switch (choose){
                case 1:
                    showOption01();
                    break;
                case 2:
                    showOption02();
                    break;
                case 3:
                    showOption03();
                    break;
                case 4:
                    showOption04();
                    break;
                case 5:
                    showOption05();
                    break;
                case 6:
                    showOption06();
                    break;
                case 7:
                    showOption07();
                    break;
                default:
                    Presentation.exit();
            }
        }else {
            if(Presentation.continueConfirm("Do you want to continue?"))
                Presentation.showMenu();
            else
                Presentation.exit();
        }
    }
    private static boolean continueConfirm( String content){
        int exit;
        do {
            System.out.printf("%s (1 - Yes/ 2 - No): ",content);
            try {
                exit = Integer.parseInt(Utils.getScanner().nextLine());
            }catch (Exception ex){
                exit = 0;
            }
            if (exit < 1 || exit > 2){
                System.out.println("Please try again !!!");
            }
        }while (exit < 1 || exit > 2);
        if (exit == 1)
            return true;
        return false;

    }
    private static void exit(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss - dd/MM/yyyy");
        System.out.printf("Goodbye!!!\nYou exited at %s", simpleDateFormat.format(new Date()));
        System.exit(0);
    }

    private static void optionSearchStaff() throws ParseException, IOException {
        int choose;
        System.out.println("\n\t\t======================>-- LOOKUP STAFF --<======================");
        System.out.println("\t\t|\t1. By Name");
        System.out.println("\t\t|\t2. By Home Town ");
        System.out.println("\t\t|\t3. By Date Of Birth");
        System.out.println("\t\t|\t4. By Gender");
        System.out.println("\t\t|\t0. Return to Option 1");
        System.out.println("\n\t\t============================================================\n");
        try {
            do {
                System.out.print("\t\t|\t=> Your choose: ");
                choose = Integer.parseInt(Utils.getScanner().nextLine());
                if(choose > 4 || choose < 0)
                    System.out.println(">>>>>>>INVALID. PLEASE TRY AGAIN !!! <<<<<<<");
            }while (choose > 4 || choose < 0);
        }catch (Exception ex){
            choose = -1;
            System.out.println(ex.getMessage());
        }
        if (choose <= 4 && choose >= 0){
            switch (choose){
                case 0:
                    Presentation.showOption01();
                    break;
                case 1:
                    System.out.print("\t\t|\tEnter keyword of name: ");
                    String keyName = Utils.getScanner().nextLine();
                    System.out.println("SEARCH LIST");
                    StaffService.showListStaff(StaffService.searchListStaff(keyName,choose));
                    break;
                case 2:
                    System.out.print("\t\t|\tEnter keyword of home town: ");
                    String keyHomeTown = Utils.getScanner().nextLine();
                    System.out.println("SEARCH LIST");
                    StaffService.showListStaff(StaffService.searchListStaff(keyHomeTown,choose));
                    break;
                case 3:
                    Date dob;
                    System.out.print("\t\t|\tEnter date of birth (dd/MM/yyyy): ");
                    try {
                        dob = Utils.getSimpleDateFormat().parse(Utils.getScanner().nextLine());
                        System.out.println("SEARCH LIST");
                        StaffService.showListStaff(StaffService.searchListStaff(dob));
                    }catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 4:
                    int chooseSex;
                    try {
                        do {
                            System.out.print("Choose gender (1-Male/ 2-Female/ 3-Don't want to reveal): ");
                            chooseSex = Integer.parseInt(Utils.getScanner().nextLine());
                            if (chooseSex < 1 || chooseSex > 3)
                                System.out.println("Invalid value");
                        }while (chooseSex < 1 || chooseSex > 3);
                    }catch (Exception ex){
                        chooseSex = -1;
                        System.out.println(ex.getMessage());
                    }
                    if (chooseSex >= 1 && chooseSex <= 3){
                        System.out.println("SEARCH LIST");
                        StaffService.showListStaff(StaffService.searchListStaff(chooseSex));
                    }
                    break;
                default:
                    Presentation.exit();
            }
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.optionSearchStaff();
        else
            Presentation.exit();
    }
    private static void optionUpdateStaff() throws ParseException, IOException {
        int pk;
        try {
            do {
                System.out.print("Press Staff ID to update (press 0 to cancel): ");
                pk = Integer.parseInt(Utils.getScanner().nextLine());
                if(pk == 0)
                    break;
                if(StaffService.findStaffByID(pk) == -1)
                    System.out.println("Staff not found !!!");
            }while (StaffService.findStaffByID(pk) == -1);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            pk = 0;
        }
        if (pk != 0){
            int chooseSex;
            String name;
            String homeTown;
            Date dob;
            Date joinDate;
            Department department;
            System.out.printf("==================\nEmployee information is being edited\n%s\n==================\n",
                    StaffService.getListStaff().get(StaffService.findStaffByID(pk)));
            try {
                System.out.print("Please enter the new staff's name: ");
                name = Utils.getScanner().nextLine();
                System.out.print("Choose staff gender (1-Male/ 2-Female/ 3-Don't want to reveal): ");
                chooseSex = Integer.parseInt(Utils.getScanner().nextLine());
                Gender gender = Gender.getValueByInt(chooseSex);
                if (gender == null)
                    throw new Exception("Invalid gender");
                System.out.print("Please enter the new staff's home town: ");
                homeTown = Utils.getScanner().nextLine();
                System.out.print("Please enter staff's date of birth(dd/MM/yyyy): ");
                dob = Utils.getSimpleDateFormat().parse(Utils.getScanner().nextLine());
                System.out.print("Please enter staff's joined date(dd/MM/yyyy): ");
                joinDate = Utils.getSimpleDateFormat().parse(Utils.getScanner().nextLine());
                System.out.printf("Choose department (%s): ", DepartmentService.getDepartmentsForOptions());
                department = DepartmentService.getListDepartment().get(Integer.parseInt(Utils.getScanner().nextLine()) - 1);
                StaffService.updateStaffInfoByID(pk,name,homeTown, gender,dob,joinDate, department);
                System.out.printf("\nUpdate successfully!!!\n==================\nStaff information\n%s\n==================\n",
                        StaffService.getListStaff().get(StaffService.findStaffByID(pk)).toString());
            }catch (Exception ex){
                System.out.println(ex.getMessage());
                System.out.println("\nUpdate failed!!!");
            }
        }else {
            System.out.println("The action has been canceled !!!!!!");
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption01();
        else
            Presentation.exit();
    }
    private static void optionDeleteStaff() throws ParseException, IOException  {
        int pk;
        try {
            do {
                System.out.print("Press Staff ID to delete (press 0 to cancel): ");
                pk = Integer.parseInt(Utils.getScanner().nextLine());
                if(pk == 0)
                    break;
                if(StaffService.findStaffByID(pk) == -1)
                    System.out.println("Staff not found. Try again !!!");
            }while (StaffService.findStaffByID(pk) == -1);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            pk = 0;
        }
        boolean del;
        if (pk != 0){
            del = Presentation.continueConfirm("Are you sure you want to delete?");
            if (del){
                StaffService.deleteStaffByID(pk);
                System.out.println("\nDelete successfully !!!");
            }else{
                System.out.println("\nThe action has been canceled !!!!");
            }
        }else{
            System.out.println("\nThe action has been canceled !!!!");
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption01();
        else
            Presentation.exit();
    }

    private static void optionSearchTable() throws ParseException, IOException {
        int capacity;
        try {
            do {
                System.out.print("Please enter the capacity for searching: ");
                capacity = Integer.parseInt(Utils.getScanner().nextLine());
                if (capacity <= 0){
                    System.out.println("Value cannot be less than 0");
                }
            }while (capacity <= 0);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            capacity = -1;
        }
        if (capacity >= 0){
            int choose;
            System.out.println("\n\t\tHow do you want to search for tables with capacity?");
            System.out.printf("\t\t|\t1. Equal to input value (=%d)\n",capacity);
            System.out.printf("\t\t|\t2. Greater than or equal to the input value (>=%d)\n",capacity);
            System.out.printf("\t\t|\t3. Less than or equal to the input value (<=%d)\n",capacity);
            System.out.println("\n\t\t============================================================\n");
            try {
                do {
                    System.out.print("\t\t|\t=>  Your choose: ");
                    choose = Integer.parseInt(Utils.getScanner().nextLine());
                    if(choose > 3 || choose < 1)
                        System.out.println(">>>>>>> INVALID VALUE <<<<<<<");
                }while (choose > 3 || choose < 1);
                System.out.println("LIST TABLE");
                TableService.showListTable(TableService.searchListTableByCapacity(capacity,choose),false);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
                System.out.println("Search failed");
            }
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption02();
        else
            Presentation.exit();
    }
    private static void optionUpdateTable() throws IOException, ParseException {
        String pk = "";
        try {
            do {
                System.out.print("Press table ID to update (press 0 to cancel): ");
                pk = Utils.getScanner().nextLine();
                if(pk.equals("0"))
                    break;
                if(TableService.findTableByPK(pk) == -1)
                    System.out.println("Table not found !!!");
            }while (TableService.findTableByPK(pk) == -1);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        if (!pk.equals("0")){
            int capacity;
            int isEmpty;
            System.out.printf("==================\nTABLE UPDATING\n%s\n==================\n",
                    TableService.getListTable().get(TableService.findTableByPK(pk)));
            try {
                do {
                    System.out.print("Please enter new capacity: ");
                    capacity = Integer.parseInt(Utils.getScanner().nextLine());
                    if (capacity <= 0){
                        System.out.println("value cannot be less than 0");
                    }
                }while (capacity <= 0);
                do {
                    System.out.print("Choose state (1-is empty/ 2-no empty): ");
                    isEmpty = Integer.parseInt(Utils.getScanner().nextLine());
                    if (isEmpty < 1 || isEmpty > 2){
                        System.out.println("invalid value");
                    }
                }while (isEmpty < 1 || isEmpty > 2);
                boolean emp = (isEmpty == 1) ? true:false;
                TableService.updateTableInfoByID(pk,capacity,emp);
                System.out.printf("\nUpdate successfully!!\n==================\nCOMPLETED\n%s\n==================\n",
                        TableService.getListTable().get(TableService.findTableByPK(pk)));
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }else {
            System.out.println("The action has been canceled !!!!!!");
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption02();
        else
            Presentation.exit();
    }
    private static void optionDeleteTable() throws IOException, ParseException {
        String pk = "";
        try {
            do {
                System.out.print("Press table ID to delete (press 0 to cancel): ");
                pk = Utils.getScanner().nextLine();
                if(pk.equals("0"))
                    break;
                if(TableService.findTableByPK(pk) == -1)
                    System.out.println("Table not found !!!");
            }while (TableService.findTableByPK(pk) == -1);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        if (!pk.equals("0")){
            if (Presentation.continueConfirm("Are you sure you want to delete?")){
                TableService.deleteTableByID(pk);
                System.out.println("Delete successfully!!!");
            }else {
                System.out.println("The action has been canceled !!!!!!");
            }
        }
        else {
            System.out.println("The action has been canceled !!!!!!");
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption02();
        else
            Presentation.exit();
    }

    private static void optionAddNewProduct(){
        int chooseCate;
        try {
            do {
                System.out.println("\t\t|\t1. Food");
                System.out.println("\t\t|\t2. Drink ");
                System.out.println("\t\t|\t0. Cancel ");
                System.out.print("\t\t|\t=> Your choose: ");
                chooseCate = Integer.parseInt(Utils.getScanner().nextLine());
                if (chooseCate > 2 || chooseCate < 0)
                    System.out.println(">>>>>>> INVALID VALUE. PLEASE TRY AGAIN !!! <<<<<<<");
            } while (chooseCate > 2 || chooseCate < 0);
        } catch (Exception ex) {
            chooseCate = -1;
            System.out.println(ex.getMessage());
        }
        if (chooseCate == 1){
            Food food = new Food();
            food.create();
            System.out.println("Add product successfully !!!!!!");
            food.show();
        }
        else if (chooseCate == 2){
            Drinks drinks = new Drinks();
            drinks.create();
            System.out.println("Add product successfully !!!!!!");
            drinks.show();
        }
        else {
            System.out.println("The action has been canceled !!!!!!");
        }
    }
    private static void optionDeleteProduct() throws IOException, ParseException {
        int pk;
        try {
            do {
                System.out.print("Enter product ID to delete (press 0 to cancel): ");
                pk = Integer.parseInt(Utils.getScanner().nextLine());
                if(pk == 0)
                    break;
                if(ProductService.findProductByID(pk) == null)
                    System.out.println("Product find not found. Try again !!!");
            }while (ProductService.findProductByID(pk) == null);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            pk = -1;
        }
        boolean del;
        if (pk != 0){
            System.out.println(ProductService.findProductByID(pk));
            del = Presentation.continueConfirm("Are you sure you want to delete?");
            if (del){
                ProductService.deleteProductByID(pk);
                System.out.println("\nDelete successfully !!!");
            }else{
                System.out.println("\nThe action has been canceled !!!!");
            }
        }else{
            System.out.println("\nThe action has been canceled !!!!");
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption03();
        else
            Presentation.exit();

    }
    private static void optionSearchProduct() throws ParseException, IOException {
        int choose;
        System.out.println("\n\t\t======================>-- SEARCHING --<======================");
        System.out.println("\t\t|\t1. By name");
        System.out.println("\t\t|\t2. By price range ");
        System.out.println("\t\t|\t0. Return OPTION 3");
        System.out.println("\n\t\t============================================================\n");
        try {
            do {
                System.out.print("\t\t|\t=> Your choose: ");
                choose = Integer.parseInt(Utils.getScanner().nextLine());
                if(choose > 2 || choose < 0)
                    System.out.print("\t\t >>>>>>>Invalid value. Try again !!! <<<<<<<");
            }while (choose > 2 || choose < 0);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            choose = -1;
        }
        if (choose <= 2 && choose >= 0){
            switch (choose){
                case 0:
//                    ProductService.showListProduct(ProductService.getListProduct());
//                    System.out.println(ProductService.getListProduct().size());
                    Presentation.showOption03();
                    break;
                case 1:
                    System.out.print("\t\t|\tEnter name of product to search: ");
                    String keywords = Utils.getScanner().nextLine();
                    System.out.println("LIST PRODUCTS");
                    ProductService.showListProduct(ProductService.searchProducts(keywords));
                    break;
                case 2:
                    System.out.println("\t\t|\tEnter range of price");
                    double from,to;
                    try {
                        do {
                            System.out.print("\t\t|\tFrom: ");
                            from = Double.parseDouble(Utils.getScanner().nextLine());
                            System.out.print("\t\t|\tTo: ");
                            to = Double.parseDouble(Utils.getScanner().nextLine());
                            if (from >= to){
                                System.out.println("\t\t|\tThe latter value must be greater than the former. Try again!!1!");
                            }
                            if (from < 0 || to < 0){
                                System.out.println("\t\t|\tValue must be greater than 0. Try again !!!");
                            }
                        }while (from >= to || from < 0 || to < 0);
                    }catch (Exception e){
                        from = 0;
                        to = 0;
                        System.out.println(e.getMessage());
                    }
                    System.out.println("LIST PRODUCTS");
                    ProductService.showListProduct(ProductService.searchProducts(from,to));
                    break;
                default:
                    Presentation.exit();
            }
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.optionSearchProduct();
        else
            Presentation.exit();

    }
    private static void optionReverseProduct() throws ParseException, IOException {
        int choose;
        System.out.println("\n\t\t======================>-- SORT --<======================");
        System.out.println("\t\t|\t1. Ascending");
        System.out.println("\t\t|\t2. Descending");
        System.out.println("\t\t|\t0. Return MENU");
        System.out.println("\n\t\t============================================================\n");
        try {
            do {
                System.out.print("\t\t|\t=> Your choose: ");
                choose = Integer.parseInt(Utils.getScanner().nextLine());
                if(choose > 2 || choose < 0)
                    System.out.print("\t\t >>>>>>>Invalid value. Try again !!! <<<<<<<");
            }while (choose > 2 || choose < 0);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            choose = -1;
        }
        if (choose >= 0 && choose <= 2){
            switch (choose){
                case 0:
                    Presentation.showMenu();
                    break;
                case 1:
                    System.out.println("SORT LIST");
                    ProductService.showListProduct(ProductService.reverseListProduct(true));
                    break;
                case 2:
                    System.out.println("SORT LIST");
                    ProductService.showListProduct(ProductService.reverseListProduct(false));
                    break;
                default:
                    Presentation.exit();
            }
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.optionReverseProduct();
        else
            Presentation.exit();
    }
    private static void optionUpdateState() throws ParseException, IOException {
        int pk;
        try {
            do {
                System.out.print("Enter product ID (press 0 to cancel): ");
                pk = Integer.parseInt(Utils.getScanner().nextLine());
                if(pk == 0)
                    break;
                if(ProductService.findProductByID(pk) == null)
                    System.out.println("Product find not found !!!");
            }while (ProductService.findProductByID(pk) == null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            pk = 0;
        }
        if (pk != 0){
            int choose;
            System.out.println(ProductService.findProductByID(pk));
            System.out.println("\n\t\t======================>-- Option --<======================");
            System.out.println("\t\t|\t1. In stock");
            System.out.println("\t\t|\t2. Out of stock ");
            System.out.println("\t\t|\t0. Return OPTION 3");
            System.out.println("\n\t\t============================================================\n");
            try {
                do {
                    System.out.print("\t\t|\t=> Your choose: ");
                    choose = Integer.parseInt(Utils.getScanner().nextLine());
                    if(choose > 2 || choose < 0)
                        System.out.print("\t\t >>>>>>>INVALID VALUE. TRY AGAIN !!! <<<<<<<");
                }while (choose > 2 || choose < 0);
                switch (choose){
                    case 0:
                        Presentation.showOption03();
                        break;
                    case 1:
                        ProductService.updateState(pk, State.IN_STOCK);
                        break;
                    case 2:
                        ProductService.updateState(pk,State.OUT_OF_STOCK);
                        break;
                }
                System.out.println("\nUpdate successfully!!!");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        } else{
            System.out.println("\nThe action has been canceled !!!!");
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption03();
        else
            Presentation.exit();
    }

    private static void showOption01() throws ParseException, IOException {
        int choose;
        System.out.println("\n\t\t======================>-- OPTION 1 --<======================");
        System.out.println("\t\t|\t1. View Staff List ");
        System.out.println("\t\t|\t2. Lookup Staff ");
        System.out.println("\t\t|\t3. Add Staff");
        System.out.println("\t\t|\t4. Update Staff Information");
        System.out.println("\t\t|\t5. Delete Staff ");
        System.out.println("\t\t|\t0. Return To MENU");
        System.out.println("\n\t\t============================================================\n");
        try {
            do {
                System.out.print("\t\t|\t=> Your choose: ");
                choose = Integer.parseInt(Utils.getScanner().nextLine());
                if(choose > 5 || choose < 0)
                    System.out.println(">>>>>>> INVALID VALUE.<<<<<<<");
            }while (choose > 5 || choose < 0);
        }catch (Exception ex){
            choose = -1;
            System.out.println(ex.getMessage());
        }
        if (choose <= 5 && choose >= 0){
            switch (choose){
                case 0:
                    Presentation.showMenu();
                    break;
                case 1:
                    System.out.println("LIST STAFF");
                    StaffService.showListStaff(StaffService.getListStaff());
                    break;
                case 2:
                    Presentation.optionSearchStaff();
                    break;
                case 3:
                    Staff staff = new Staff();
                    staff.create();
                    break;
                case 4:
                    Presentation.optionUpdateStaff();
                    break;
                case 5:
                    Presentation.optionDeleteStaff();
                    break;
                default:
                    Presentation.exit();
            }
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption01();
        else
            Presentation.exit();
    }
    private static void showOption02() throws ParseException, IOException {
        int choose;
        System.out.println("\n\t\t======================>-- OPTION 2 --<======================");
        System.out.println("\t\t|\t1. View all table list");
        System.out.println("\t\t|\t2. View empty table list ");
        System.out.println("\t\t|\t3. Search table information by capacity ");
        System.out.println("\t\t|\t4. Add table info");
        System.out.println("\t\t|\t5. Update table info");
        System.out.println("\t\t|\t6. Delete table info ");
        System.out.println("\t\t|\t0. Return to MENU");
        System.out.println("\n\t\t============================================================\n");
        try {
            do {
                System.out.print("\t\t|\t=> Your choose: ");
                choose = Integer.parseInt(Utils.getScanner().nextLine());
                if(choose > 6 || choose < 0)
                    System.out.println(">>>>>>> INVALID VALUE. PLEASE TRY AGAIN !!! <<<<<<<");
            }while (choose > 6 || choose < 0);
        }catch (Exception ex){
            choose = -1;
            System.out.println(ex.getMessage());
        }
        if (choose <= 6 && choose >= 0){
            switch (choose){
                case 0:
                    Presentation.showMenu();
                    break;
                case 1:
                    System.out.println("ALL TABLE LIST");
                    TableService.showListTable(TableService.getListTable(),false);
                    break;
                case 2:
                    System.out.println("EMPTY TABLE LIST");
                    TableService.showListTable(TableService.getListTable(),true);
                    break;
                case 3:
                    Presentation.optionSearchTable();
                    break;
                case 4:
                    Table table = new Table();
                    table.create();
                    break;
                case 5:
                    Presentation.optionUpdateTable();
                    break;
                case 6:
                    Presentation.optionDeleteTable();
                    break;
                default:
                    Presentation.exit();
            }
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption02();
        else
            Presentation.exit();
    }
    private static void showOption03() throws ParseException, IOException {
        int choose;
        System.out.println("\n\t\t======================>-- OPTION 2 --<======================");
        System.out.println("\t\t|\t1. Add new product to the system");
        System.out.println("\t\t|\t2. Delete product ");
        System.out.println("\t\t|\t3. Search for product (by name or price range) ");
        System.out.println("\t\t|\t4. Sort product list by price");
        System.out.println("\t\t|\t5. Update product inventory status");
        System.out.println("\t\t|\t0. Return to MENU");
        System.out.println("\n\t\t============================================================\n");
        try {
            do {
                System.out.print("\t\t|\t=> Your choose: ");
                choose = Integer.parseInt(Utils.getScanner().nextLine());
                if(choose > 5 || choose < 0)
                    System.out.println(">>>>>>> INVALID VALUE. PLEASE TRY AGAIN !!! <<<<<<<");
            }while (choose > 5 || choose < 0);
        }catch (Exception ex){
            choose = -1;
            System.out.println(ex.getMessage());
        }
        if (choose <= 5 && choose >= 0) {
            switch (choose) {
                case 0:
                    Presentation.showMenu();
                    break;
                case 1:
                    Presentation.optionAddNewProduct();
                    break;
                case 2:
                    Presentation.optionDeleteProduct();
                    break;
                case 3:
                    Presentation.optionSearchProduct();
                    break;
                case 4:
                    Presentation.optionReverseProduct();
                    break;
                case 5:
                    Presentation.optionUpdateState();
                    break;
                default:
                    Presentation.exit();
            }
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption03();
        else
            Presentation.exit();
    }
    private static void showOption04() throws ParseException, IOException {
        System.out.println("\n\t\t======================>-- OPTION 4 --<======================");
        System.out.printf("LIST OF STAFFS WITH BIRTHDAY IN THIS MONTH %d\n",new Date().getMonth() + 1);
        StaffService.showListStaff(StaffService.getListStaffCurrentMonthOfBirth());
        if(Presentation.continueConfirm("Do you want to continue ???"))
            Presentation.showMenu();
        else
            Presentation.exit();
    }
    private static void showOption05() throws ParseException, IOException {
        int choose;
        System.out.println("\n\t\t======================>-- OPTION 5 --<======================");
        System.out.println("\t\t|\t1. Create new bill");
        System.out.println("\t\t|\t2. Add items to the bill ");
        System.out.println("\t\t|\t0. Return to MENU");
        System.out.println("\n\t\t============================================================\n");
        try {
            do {
                System.out.print("\t\t|\t=> Your choose: ");
                choose = Integer.parseInt(Utils.getScanner().nextLine());
                if(choose > 2 || choose < 0)
                    System.out.println(">>>>>>> INVALID VALUE. PLEASE TRY AGAIN !!! <<<<<<<");
            }while (choose > 2 || choose < 0);
        }catch (Exception ex){
            choose = -1;
            System.out.println(ex.getMessage());
        }
        String tableID;
        int check;
        if (choose <= 2 && choose >=0){
            switch (choose){
                case 0:
                    Presentation.showMenu();
                    break;
                case 1:
                    do {
                        System.out.print("Enter tableID (press 0 to cancel): ");
                        tableID = Utils.getScanner().nextLine();
                        if(tableID.equals("0"))
                            break;
                        check = TableService.findTableByPK(tableID);
                        if(check == -1)
                            System.out.println("Table find not found. Try again!!!");
                        if (TableService.getTableByID(tableID) != null){
                            if(TableService.getTableByID(tableID).isEmpty() == false){
                                System.out.println("This table isn't empty!!!");
                                check = -1;
                            }
                        }
                    }while (check == -1);
                    if (!tableID.equals("0")){
                        ArrayList<Order> tempListOrder = new ArrayList<>();
                        do {
                            int productID;
                            boolean state;
                            try {
                                do {
                                    state = true;
                                    System.out.print("Enter productID (press 0 to cancel): ");
                                    productID = Integer.parseInt(Utils.getScanner().nextLine());
                                    if(productID == 0)
                                        break;
                                    if(ProductService.findProductByID(productID) == null)
                                        System.out.println("Product find not found. Try again !!!");
                                    else if (ProductService.findProductByID(productID).getState() == State.OUT_OF_STOCK){
                                        state = false;
                                        System.out.println("Product is out of stock. Please choose another product !!!");
                                    }
                                }while (ProductService.findProductByID(productID) == null || state == false);
                            }catch (Exception e){
                                productID = 0;
                                System.out.println(e.getMessage());
                            }
                            if (productID != 0){
                                System.out.printf("Product name}: %s\nPrice: %,.0f\n", ProductService.findProductByID(productID).getName(),
                                        ProductService.findProductByID(productID).getPrice());
                                int quantity;
                                try {
                                    do {
                                        System.out.print("Enter the quantity: ");
                                        quantity = Integer.parseInt(Utils.getScanner().nextLine());
                                        if (quantity <= 0){
                                            System.out.println("Quantity must be greater than 0 !!!");
                                        }
                                        else {
                                            tempListOrder.add(new Order(productID,tableID,quantity));
                                        }
                                    }while (quantity <= 0);
                                }catch (Exception e){
                                    System.out.println(e.getMessage());
                                }
                            }else {
                                System.out.println("\nThe action has been canceled !!!");
                            }
                        }while (continueConfirm("Would you like to order more?"));
                        if (tempListOrder.size() != 0){
                            BillService.createBill(tableID,tempListOrder);
                            System.out.println("Create bill successfully !!!");
                        }else {
                            System.out.println("Bill generation failed because no product were added !!!");
                        }
                    }else {
                        System.out.println("The action has been canceled !!!");
                    }
                    break;
                case 2:
                    do {
                        System.out.print("Enter tableID (press 0 to cancel): ");
                        tableID = Utils.getScanner().nextLine();
                        if(tableID.equals("0"))
                            break;
                        check = TableService.findTableByPK(tableID);
                        if(check == -1)
                            System.out.println("Table find not found. Try again!!!");
                        if (TableService.getTableByID(tableID) != null){
                            if (BillService.getBillUnpaidByTableID(tableID) == null){
                                System.out.println("The tableID has not recorded any payment bill. Retry!!!");
                                check = -1;
                            }
                        }
                    }while (check == -1);
                    if (!tableID.equals("0")){
                        ArrayList<Order> tempListOrder = new ArrayList<>();
                        System.out.printf("Current bill on this table %s\n",tableID);
                        BillService.showPaymentDetailByTableID(tableID);
                        System.out.println("===============================");
                        do {
                            int productID;
                            boolean state;
                            try {
                                do {
                                    state = true;
                                    System.out.print("Enter productID (press 0 to cancel): ");
                                    productID = Integer.parseInt(Utils.getScanner().nextLine());
                                    if(productID == 0)
                                        break;
                                    if(ProductService.findProductByID(productID) == null)
                                        System.out.println("Product find not found. Try again !!!");
                                    else if (ProductService.findProductByID(productID).getState() == State.OUT_OF_STOCK){
                                        state = false;
                                        System.out.println("Product is out of stock. Please choose another product !!!");
                                    }
                                }while (ProductService.findProductByID(productID) == null || state == false);
                            }catch (Exception e){
                                productID = 0;
                                System.out.println(e.getMessage());
                            }
                            if (productID != 0){
                                System.out.printf("Product name}: %s\nPrice: %,.0f\n", ProductService.findProductByID(productID).getName(),
                                        ProductService.findProductByID(productID).getPrice());
                                int quantity;
                                try {
                                    do {
                                        System.out.print("Enter the quantity: ");
                                        quantity = Integer.parseInt(Utils.getScanner().nextLine());
                                        if (quantity <= 0){
                                            System.out.println("Quantity must be greater than 0 !!!");
                                        }
                                        else {
                                            tempListOrder.add(new Order(productID,tableID,quantity));
                                        }
                                    }while (quantity <= 0);
                                }catch (Exception e){
                                    System.out.println(e.getMessage());
                                }
                            }else {
                                System.out.println("\nThe action has been canceled !!!");
                            }
                        }while (continueConfirm("Would you like to order more?"));
                        if (tempListOrder.size() != 0){
                            BillService.alterListOrderOnBill(tableID,tempListOrder);
                            System.out.println("Successfully added item to bill !!!");
                        }else {
                            System.out.println("No item have been added to the bill !!!");
                        }
                    }else {
                        System.out.println("The action has been canceled !!!");
                    }
                    break;
            }
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption05();
        else
            Presentation.exit();
    }
    private static void showOption06() throws ParseException, IOException {
        System.out.println("\n\t\t======================>-- OPTION 6--<======================");
        String tableID;
        do {
            System.out.print("Enter the table ID you want to pay (press 0 to cancel): ");
            tableID = Utils.getScanner().nextLine();
            if(tableID.equals("0"))
                break;
            if (BillService.getBillUnpaidByTableID(tableID) == null){
                System.out.println("This table ID has not recorded any payment bill!!!");
            }
        }while (BillService.getBillUnpaidByTableID(tableID) == null);
        if (!tableID.equals("0")){
            BillService.showPaymentDetailByTableID(tableID);
            int paymentConfirm;
            try {
                do {
                    System.out.print("Confirm to pay (1-Yes/ 2-No): ");
                    paymentConfirm = Integer.parseInt(Utils.getScanner().nextLine());
                    if (paymentConfirm < 1 || paymentConfirm > 2){
                        System.out.println("INVALID VALUE, RETRY!!!");
                    }
                }while (paymentConfirm < 1 || paymentConfirm > 2);
            }catch (Exception e){
                paymentConfirm = 2;
                System.out.println(e.getMessage());
            }
            if (paymentConfirm == 1){
                BillService.payByTableID(tableID);
                System.out.println("Payment success !!!");
            }
            else {
                System.out.println("The action has been canceled!!!");
            }
        }else {
            System.out.println("The action has been canceled !!!");
        }
        if(Presentation.continueConfirm("Do you want to return MENU?"))
            Presentation.showMenu();
        else
            Presentation.exit();
    }
    private static void showOption07() throws ParseException, IOException {
        int choose;
        System.out.println("\n\t\t======================>-- OPTION 7 --<======================");
        System.out.println("\t\t|\t1. Revenue for the specified month (in the current year)");
        System.out.println("\t\t|\t2. Revenue for the specified period ");
        System.out.println("\t\t|\t0. Return to MENU");
        System.out.println("\n\t\t============================================================\n");
        try {
            do {
                System.out.print("\t\t|\t=> Your choose: ");
                choose = Integer.parseInt(Utils.getScanner().nextLine());
                if(choose > 2 || choose < 0)
                    System.out.print("\t\t >>>>>>>INVALID VALUE. PLEASE TRY AGAIN !!!  <<<<<<<");
            }while (choose > 2 || choose < 0);
        }catch (Exception e){
            choose = -1;
            System.out.println(e.getMessage());
        }
        if (choose <= 2 && choose >=0){
            switch (choose){
                case 0:
                    Presentation.showMenu();
                    break;
                case 1:
                    int month;
                    try {
                        do {
                            System.out.print("=> The month you want the sales statistics is (press 0 to cancel): ");
                            month = Integer.parseInt(Utils.getScanner().nextLine());
                            if (month == 0){
                                break;
                            }
                            if (month < 1 || month > 12){
                                System.out.println("Invalid value. Retry !!!");
                            }
                        }while (month < 1 || month > 12);
                    }catch (Exception e){
                        month = 0;
                        System.out.println(e.getMessage());
                    }
                    if (month != 0){
                        BillService.revenueStatistics(month);
                    }
                    else {
                        System.out.println("The action has been canceled !!!!!");
                    }
                    break;
                case 2:
                    String from;
                    String to;
                    System.out.println("Enter the time period ");
                    do {
                        System.out.print("=>From (dd/MM/yyyy): ");
                        from = Utils.getScanner().nextLine();
                        if (Utils.checkValidDate(from) == false){
                            System.out.println("Invalid import data. Retry !!!");
                        }
                    }while (Utils.checkValidDate(from) == false);
                    boolean check;
                    do {
                        System.out.print("=>To (dd/MM/yyyy): ");
                        to = Utils.getScanner().nextLine();
                        check = Utils.checkValidDate(to);
                        if (check == false){
                            System.out.println("Invalid import data. Retry !!!");
                        }
                        if (check){
                            if (Utils.getSimpleDateFormat().parse(to).before(Utils.getSimpleDateFormat().parse(from))){
                                System.out.printf("You must enter time value after %s. Retry !!!\n",from);
                                check = false;
                            }
                        }
                    }while (check == false);
                    BillService.revenueStatistics(Utils.getSimpleDateFormat().parse(from), Utils.getSimpleDateFormat().parse(to));
                    break;
            }
        }
        if(Presentation.continueConfirm("Do you want to continue?"))
            Presentation.showOption07();
        else
            Presentation.exit();
    }

}
