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
public class FeeBalances_object {

    private String stname;
    private String classname;
    private String feebalance;
    private String admnumber;
    private int Count;
    private String coursename;
    private String totalfee;
    private String amount_paid;

    public FeeBalances_object(String stname1, String classname1, String admnumber1, int count, String feebalance1,String coursename,String totalfee,
            String amount_paid) {

        this.classname = classname1;
        this.admnumber = admnumber1;
        this.stname = stname1;
        this.Count = count;
        this.feebalance = feebalance1;
        this.coursename = coursename;
        this.totalfee = totalfee;
        this.amount_paid = amount_paid;

    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }
    
    

    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getFeebalance() {
        return feebalance;
    }

    public void setFeebalance(String feebalance) {
        this.feebalance = feebalance;
    }

    public String getAdmnumber() {
        return admnumber;
    }

    public void setAdmnumber(String admnumber) {
        this.admnumber = admnumber;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(String totalfee) {
        this.totalfee = totalfee;
    }
    
    

}
