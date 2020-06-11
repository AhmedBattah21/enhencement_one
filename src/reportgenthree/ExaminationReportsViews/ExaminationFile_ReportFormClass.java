/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationReportsViews;


public class ExaminationFile_ReportFormClass {
    
    private int sno;
   private String course;
   private String mid;
   private String end;
   private String average;
   private String comment;
   private String initials;
   
   public  ExaminationFile_ReportFormClass(int sno,String course,String mid,String end,String average,String comment,String initials){
   
       this.sno = sno;
       this.course = course;
       this.mid = mid;
       this.end = end;
       this.average = average;
       this.comment = comment;
       this.initials = initials;
   
   }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }
   
    
}
