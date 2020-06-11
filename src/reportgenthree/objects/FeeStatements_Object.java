/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.objects;

/**
 *
 * @author Computer
 *
 *
 */
public class FeeStatements_Object {

    private String State_date;
    private String State_paymode;
    private String State_receiptno;
    private String State_amount;
    private String State_balance;
    private String Term;
    private String State_comment;

    public FeeStatements_Object(String date, String paymode, String receiptno, String amount, String balance, String term, String comment) {

        State_date = date;
        State_paymode = paymode;
        State_receiptno = receiptno;
        State_amount = amount;
        State_balance = balance;
        Term = term;
        State_comment = comment;

    }

    public String getState_comment() {
        return State_comment;
    }

    public void setState_comment(String State_comment) {
        this.State_comment = State_comment;
    }
    
    

    public String getState_date() {
        return State_date;
    }

    public void setState_date(String State_date) {
        this.State_date = State_date;
    }

    public String getState_paymode() {
        return State_paymode;
    }

    public void setState_paymode(String State_paymode) {
        this.State_paymode = State_paymode;
    }

    public String getState_receiptno() {
        return State_receiptno;
    }

    public void setState_receiptno(String State_receiptno) {
        this.State_receiptno = State_receiptno;
    }

    public String getState_amount() {
        return State_amount;
    }

    public void setState_amount(String State_amount) {
        this.State_amount = State_amount;
    }

    public String getState_balance() {
        return State_balance;
    }

    public void setState_balance(String State_balance) {
        this.State_balance = State_balance;
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String Term) {
        this.Term = Term;
    }

}
