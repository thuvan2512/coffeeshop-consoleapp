package services;

import models.Department;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DepartmentService {
    private static List<Department> listDepartment;
    private final static String fileDepartment = "src/main/resources/Departments.txt";

    public static List<String> readFile(){
        ArrayList<String> results = new ArrayList<>();
        try {
            File file = new File(fileDepartment);
            Scanner scanner = new Scanner(file);
            results.addAll(List.of(scanner.nextLine().split("#")));
            scanner.close();
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return results;
    }
    public static void initListDepartment(){
        listDepartment = new ArrayList<Department>();
        List<String> results = DepartmentService.readFile();
        try {
            for (int i = 0;i < results.size();i++){
                DepartmentService.listDepartment.add(new Department(results.get(i)));
            }
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }

    }
    public static List<Department> getListDepartment() {
        return DepartmentService.listDepartment;
    }
    public static String getDepartmentsForOptions() {
        String results = "";
        List<String> department = DepartmentService.readFile();
        for (int i = 0;i < department.size();i++){
            if (department.size() == i+1)
                results += String.format(" %d-%s",i+1,department.get(i));
            else
                results += String.format(" %d-%s/",i+1,department.get(i));
        }
        return results;
    }
}
