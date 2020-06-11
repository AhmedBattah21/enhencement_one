/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.objects;

/**
 *
 * @author Computer
 */
public class Teachers_AssignedUnitsViewerClass {
    
    private String name;
    private String unitname;
    private String std_year;
    private String class_name;
    private int number;
    private String Teacher_idnumber;
    
    public Teachers_AssignedUnitsViewerClass(String name,String unitname,String std_year,String class_name,int number,String Teacher_idnumber){
    
        this.name = name;
        this.unitname = unitname;
        this.std_year = std_year;
        this.class_name = class_name;
        this.number = number;
        this.Teacher_idnumber = Teacher_idnumber;
    
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTeacher_idnumber() {
        return Teacher_idnumber;
    }

    public void setTeacher_idnumber(String Teacher_idnumber) {
        this.Teacher_idnumber = Teacher_idnumber;
    }
    
    
}
