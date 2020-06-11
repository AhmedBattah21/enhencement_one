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
public class StudentAcademicScores_object {

    private String regno;
    private int number;
    private String score;

    private String Remark;
    private String name;

    public StudentAcademicScores_object(int number1, String regno1, String score1, String remark, String name1) {

        this.regno = regno1;
        this.number = number1;
        this.score = score1;

        this.Remark = remark;
        this.name = name1;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

}
