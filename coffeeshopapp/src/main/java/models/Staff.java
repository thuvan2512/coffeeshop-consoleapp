package models;

import base.IModel;
import enumerate.Gender;
import services.DepartmentService;
import services.StaffService;
import utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Staff implements IModel {
    private int pk;
    private static int count = 0;
    {
        try {
            Staff.setCount(Utils.getCount(0));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        pk = ++count; // primary key auto increase
    }
    private String name;
    private Gender gender = Gender.UNDEFINED;
    private String homeTown;
    private Date dob;
    private Date joinDate;
    private Department department;

    //constructor
    public Staff(String name, Gender gender, String homeTown, Date dob, Date joinDate, Department department) {
        this.name = name;
        this.gender = gender;
        this.homeTown = homeTown;
        this.dob = dob;
        this.joinDate = joinDate;
        this.department = department;
    }
    public Staff() {
    }

    //Setter and getter
    public int getPk() {
        return pk;
    }
    public String getName() {
        return name;
    }
    public Gender getSex() {
        return gender;
    }
    public String getHomeTown() {
        return homeTown;
    }
    public Date getDob() {
        return dob;
    }
    public Date getJoinDate() {
        return joinDate;
    }
    public Department getDepartment() {
        return department;
    }
    public static void setCount(int count){
        Staff.count = count;
    }
    public void setPk(int pk){
        this.pk = pk;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSex(Gender gender) {
        this.gender = gender;
    }
    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    // method
    @Override
    public void create(){
        try {
            System.out.print("Please enter the new staff's name: ");
            this.name = Utils.getScanner().nextLine();
            System.out.print("Choose staff gender (1-Male/ 2-Female/ 3-Don't want to reveal): ");
            this.gender = Gender.getValueByInt(Integer.parseInt(Utils.getScanner().nextLine()));
            if (this.gender == null)
                throw new Exception("Invalid gender");
            System.out.print("Please enter the new staff's home town: ");
            this.homeTown = Utils.getScanner().nextLine();
            System.out.print("Please enter staff's date of birth(dd/MM/yyyy): ");
            this.dob = Utils.getSimpleDateFormat().parse(Utils.getScanner().nextLine());
            System.out.print("Please enter staff's joined date(dd/MM/yyyy): ");
            this.joinDate = Utils.getSimpleDateFormat().parse(Utils.getScanner().nextLine());
            System.out.printf("Choose department (%s): ",DepartmentService.getDepartmentsForOptions());
            this.department = DepartmentService.getListDepartment().get(Integer.parseInt(Utils.getScanner().nextLine()) - 1);
            Utils.setCount(0);
            StaffService.getListStaff().add(this);
            this.writeIntoFile();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void show(){
        System.out.println(this);
    }
    @Override
    public void writeIntoFile(){
        try {
            File file = new File(StaffService.getFileStaffs());
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write(String.format("%d#%s#%d#%s#%s#%s#%s\n",
                    this.getPk(),this.getName(),this.getSex().getInt(),
                    this.getHomeTown(),
                    Utils.getSimpleDateFormat().format(this.getDob()),Utils.getSimpleDateFormat().format(this.getJoinDate()),
                    this.getDepartment().getName()));
            // PK # Name # Sex # HomeTown # DOB # JD # Department
            fileWriter.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public String toString(){
        return String.format("Staff ID: %d\nFull name: %s\nSex: %s\nHome town: %s\nDate of birth: %s\nJoined date: %s\nDepartment: %s",
                this.pk, this.name, this.gender.getValue(), this.homeTown, Utils.getSimpleDateFormat().format(this.dob),
                Utils.getSimpleDateFormat().format(this.joinDate), this.department.getName());
    }
}
