package services;


import enumerate.Gender;
import models.Department;
import models.Staff;
import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StaffService {
    private static ArrayList<Staff> listStaff;
    private final static String fileStaffs = "src/main/resources/Staffs.txt";
    static {
        try {
            StaffService.readFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getFileStaffs() {
        return fileStaffs;
    }
    public static ArrayList<Staff> getListStaff() {
        return StaffService.listStaff;
    }

    public static void readFile(){
        try {
            StaffService.listStaff = new ArrayList<Staff>();
            File file = new File(fileStaffs);
            Scanner scan = new Scanner(file);
            while (scan.hasNext()){
                String staff = scan.nextLine();
                if(staff.contains("#") == true) {
                    String[] result = staff.split("#");
                    Department depart = DepartmentService.getListDepartment().stream().filter(department -> department.getName().equals(result[6]) == true).collect(Collectors.toList()).get(0);
                    StaffService.listStaff.add(new Staff(result[1],
                            Gender.getValueByInt(Integer.parseInt(result[2])), result[3],
                            Utils.getSimpleDateFormat().parse(result[4]), Utils.getSimpleDateFormat().parse(result[5]),
                            depart));
                    StaffService.listStaff.get(StaffService.listStaff.size() - 1).setPk(Integer.parseInt(result[0]));
                }
            }
            scan.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public static void writeListIntoFile() {
        try {
            File file = new File(fileStaffs);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.close();
            StaffService.listStaff.forEach(st ->{
                st.writeIntoFile();
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void showListStaff(ArrayList<Staff> list){
        if(list.size() == 0){
            System.out.println(">>> List Staff Is Empty<<<");
        }
        for(int i = 0; i < list.size();i++){
            System.out.println("------------------");
            list.get(i).show();
            if(i == list.size() - 1){
                System.out.println("------------------");
            }
        }
    }
    public static int findStaffByID(int pk){
        int index = -1;
        for (int i = 0; i < StaffService.listStaff.size(); i++){
            if (StaffService.listStaff.get(i).getPk() == pk){
                index = i;
                break;
            }
        }
        return index;
    }
    public static void updateStaffInfoByID(int pk, String name, String homeTown, Gender gender, Date dob, Date joinDate, Department department) throws IOException {
        if(StaffService.findStaffByID(pk) != -1){
            Staff newStaff = StaffService.listStaff.get(StaffService.findStaffByID(pk));
            newStaff.setName(name);
            newStaff.setHomeTown(homeTown);
            newStaff.setSex(gender);
            newStaff.setDob(dob);
            newStaff.setJoinDate(joinDate);
            newStaff.setDepartment(department);
            StaffService.writeListIntoFile();
        }
    }
    public static void deleteStaffByID(int pk)  {
        StaffService.listStaff.remove(StaffService.findStaffByID(pk));
        StaffService.writeListIntoFile();
    }
    public static ArrayList<Staff> searchListStaff(Date dob){
        ArrayList<Staff> kq = new ArrayList<>();
        StaffService.listStaff.forEach(st ->{
            if(st.getDob().equals(dob)){
                kq.add(st);
            }
        });
        return kq;
    }
    public static ArrayList<Staff> searchListStaff(int sex){
        ArrayList<Staff> kq = new ArrayList<>();
        StaffService.listStaff.forEach(st ->{
            if(st.getSex().getInt() == sex){
                kq.add(st);
            }
        });
        return kq;
    }
    public static ArrayList<Staff> searchListStaff(String key,int option){
        ArrayList<Staff> kq = new ArrayList<>();
        if (option == 1){
            StaffService.listStaff.forEach(st ->{
                if(st.getName().toLowerCase().contains(key.toLowerCase().strip())){
                    kq.add(st);
                }
            });
        }else if (option == 2){
            StaffService.listStaff.forEach(st ->{
                if(st.getHomeTown().toLowerCase().contains(key.toLowerCase().strip())){
                    kq.add(st);
                }
            });
        }
        return kq;
    }
    public static ArrayList<Staff> getListStaffCurrentMonthOfBirth(){
        ArrayList<Staff> kq = new ArrayList<>();
        Date currentDay = new Date();
        StaffService.listStaff.forEach(st ->{
            if(st.getDob().getMonth() == currentDay.getMonth()){
                kq.add(st);
            }
        });
        return kq;
    }
}
