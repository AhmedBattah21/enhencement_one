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
public class FindStudentAcademicScores_object {
    
    private String examname;
    private String term;
    private String score;
    private String Remark;
    private String unitname;
    private String exyear;

    
    
    public FindStudentAcademicScores_object(String examname1,String term1,String score1,String remark,String unitname1,String exyear1){
    
    
    this.term = term1;
    this.examname = examname1;
    this.score = score1;
    this.Remark= remark;
    this.unitname= unitname1;
    this.exyear= exyear1;
    
    
    }

    public String getExyear() {
        return exyear;
    }

    public void setExyear(String exyear) {
        this.exyear = exyear;
    }

    
    public String getExamname() {
        return examname;
    }

    public void setExamname(String examname) {
        this.examname = examname;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    
}
