/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

/**
 *
 * @author Computer
 */
public class AvailableProgramsClass {
    
    private String pname;
    private String t1;
    private String t2;
    private String t3;
    private String pid;
    private int total;
    private String pplan;
    
    public AvailableProgramsClass(String pname,String t1,String t2,String t3,int total,String pid,String pplan){
    
    this.pname = pname;
    this.t1 = t1;
    this.t2 = t2;
    this.t3 = t3;
    this.total = total;
    this.pid = pid;
    this.pplan = pplan;
    
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT3() {
        return t3;
    }

    public void setT3(String t3) {
        this.t3 = t3;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPplan() {
        return pplan;
    }

    public void setPplan(String pplan) {
        this.pplan = pplan;
    }
    
    
    
}
