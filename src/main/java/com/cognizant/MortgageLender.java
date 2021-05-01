package com.cognizant;

public class MortgageLender {

     int availableFundsAmount;

    public int getAvailableFunds(){
        return availableFundsAmount;
    }


    public void depositFunds(int amount) {
        availableFundsAmount+=amount;
    }

    public Loan processLoan(LoanApplicant la, int requestedAmount) {
        Loan loan = new Loan();
        String qualificationstatus = this.checkQualificationStatus(la);
        return loan;

    }

     public string checkQualificationStatus(LoanApplicant la){

     }





}
