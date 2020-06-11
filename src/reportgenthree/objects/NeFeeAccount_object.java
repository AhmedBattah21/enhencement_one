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
public class NeFeeAccount_object {
    
    
    private String id = "";
    private String accname = "";
    private String t1fee = "";
    private String t2fee = "";
    private String t3fee = "";
    private String totals = "";
    
    public NeFeeAccount_object(String id_1,String accname_1,String t1fee_1,String t2fee_1,String t3fee_1,String totals_1){
   
   
        id = id_1;
        accname= accname_1;
        t1fee = t1fee_1;
        t2fee = t2fee_1;
        t3fee = t3fee_1;
        totals = totals_1;
        
   }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccname() {
        return accname;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }

    public String getT1fee() {
        return t1fee;
    }

    public void setT1fee(String t1fee) {
        this.t1fee = t1fee;
    }

    public String getT2fee() {
        return t2fee;
    }

    public void setT2fee(String t2fee) {
        this.t2fee = t2fee;
    }

    public String getT3fee() {
        return t3fee;
    }

    public void setT3fee(String t3fee) {
        this.t3fee = t3fee;
    }

    public String getTotals() {
        return totals;
    }

    public void setTotals(String totals) {
        this.totals = totals;
    }
    
    
    
}
