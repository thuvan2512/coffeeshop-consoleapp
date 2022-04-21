package models;


public class Department {
    private int pk = ++count;
    private static int count = 0;
    private String name;

    //constructor
    public Department(String name) {
        this.name = name;
    }
    public Department(){
    }

    //getter and setter
    public static int getCount() {
        return count;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //method
    @Override
    public String toString(){
        return String.format("Department ID: %d\nName of department: %s",this.pk,this.name);
    }
}
