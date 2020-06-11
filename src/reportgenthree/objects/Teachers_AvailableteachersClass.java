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
public class Teachers_AvailableteachersClass {
    
    private String stname;
    private String IdNumber;
    private String initials;
    private String dept;
    private int number;
    private String task;
    
    public  Teachers_AvailableteachersClass(String stname,String IdNumber,String initials,String dept,int number,String task){
    
    this.number = number;
    this.IdNumber = IdNumber;
    this.dept = dept;
    this.initials = initials;
    this.stname = stname;
    this.task = task;
    
    
    }

    public String getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(String IdNumber) {
        this.IdNumber = IdNumber;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
    

    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    
    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
    
}
