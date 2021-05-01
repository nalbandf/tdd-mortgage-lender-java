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
        int savingsWorth = (requestedAmount*100)/la.getSavings();
        System.out.println("savingsWorth is "+savingsWorth);
        if(la.getDti()<36 && la.getCredit_score()>620 && savingsWorth>=25){
            loan.setLoan_amount(requestedAmount);
            loan.setLoanApplicant(la);
            loan.setQualification("qualified");
            loan.setStatus("qualified");
        }
        return loan;

    }







}
