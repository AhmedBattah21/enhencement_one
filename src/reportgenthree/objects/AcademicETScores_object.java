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
public class AcademicETScores_object {
    
    private String regno;
    private int number;
    private String mid;
    private String end;
    private String total;
    private String Remark;
    private String name;
   

    
    
    public AcademicETScores_object(int number1,String regno1,String score1,String score2,String score3,String remark,String name1){
    
    
    this.regno = regno1;
    this.number = number1;
    this.mid = score1;
    this.end = score2;
    this.total = score3;
    this.Remark= remark;
    this.name= name1;
   
    
    
    
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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
    
    

}