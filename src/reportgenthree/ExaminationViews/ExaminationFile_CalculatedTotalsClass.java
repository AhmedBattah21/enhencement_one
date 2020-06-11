/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationViews;

/**
 *
 * @author Computer
 */
public class ExaminationFile_CalculatedTotalsClass {

    String mid_score;
    String term_totals;
    String std_name;
    String end_score;
    String Admnumber;
    String remark;
   
    int count;

    public ExaminationFile_CalculatedTotalsClass(int count, String mid_score, String std_name, String end_score, String term_totals, String remark,String adm) {

        this.mid_score = mid_score;
        this.std_name = std_name;
        this.end_score = end_score;
        this.term_totals = term_totals;
        this.remark = remark;
        this.count = count;
        this.Admnumber = adm;

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

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
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

    public String getAdmnumber() {
        return Admnumber;
    }

    public void setAdmnumber(String Admnumber) {
        this.Admnumber = Admnumber;
    }
    
    

}
