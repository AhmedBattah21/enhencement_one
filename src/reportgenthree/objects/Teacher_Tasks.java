/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.objects;

public class Teacher_Tasks {

    private String task_code;
    private String task_name;

    public Teacher_Tasks(String task_code,String task_name) {

        this.task_code = task_code;
        this.task_name = task_name;
    }

    public String getTask_code() {
        return task_code;
    }

    public void setTask_code(String task_code) {
        this.task_code = task_code;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }
    
    

}
