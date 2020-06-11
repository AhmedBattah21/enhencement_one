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



public class StprofileExamination_details {
    
    String recordid;
    String unitname;
    String unitscore;
    String remark;
   int count;
    public StprofileExamination_details(int count,String recordid,String unitname,String unitscore,String remark){
    
    this.recordid = recordid;
    this.unitname = unitname;
    this.unitscore = unitscore;
    this.remark = remark;
    this.count = count;
    
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getUnitscore() {
        return unitscore;
    }

    public void setUnitscore(String unitscore) {
        this.unitscore = unitscore;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    
    
}
