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
public class ExaminationScoreSheetViewerClass {
    
    private int count;
    private String score;
    private String adm_number;
    private String remark;
    private String recordid;
    private String std_name;
    
    public  ExaminationScoreSheetViewerClass(int count,String adm_number,String score,String remark,String recordid,String std_name){
    
    this.count = count;
    this.score = score;
    this.adm_number = adm_number;
    this.remark = remark;
    this.recordid = recordid;
    this.std_name = std_name;
    
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAdm_number() {
        return adm_number;
    }

    public void setAdm_number(String adm_number) {
        this.adm_number = adm_number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }
    
    
    
    
}
