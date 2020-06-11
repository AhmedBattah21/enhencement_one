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
public class StprofileExamination_finaldetails {

    String mid_score;
    String term_totals;
    String unitname;
    String end_score;
    String remark;
    int count;

    public StprofileExamination_finaldetails(int count, String mid_score, String unitname, String end_score, String term_totals, String remark) {

        this.mid_score = mid_score;
        this.unitname = unitname;
        this.end_score = end_score;
        this.term_totals = term_totals;
        this.remark = remark;
        this.count = count;

    }

    public String getMid_score() {
        return mid_score;
    }

    public void setMid_score(String mid_score) {
        this.mid_score = mid_score;
    }

    public String getTerm_totals() {
        return term_totals;
    }

    public void setTerm_totals(String term_totals) {
        this.term_totals = term_totals;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getEnd_score() {
        return end_score;
    }

    public void setEnd_score(String end_score) {
        this.end_score = end_score;
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
