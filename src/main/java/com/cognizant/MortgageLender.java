package com.cognizant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MortgageLender {

    int availableFundsAmount;
    int pendingFundsToProcess;
    LocalDate currentDate = LocalDate.now();
    LocalDate currentDateMinusThree = currentDate.minusDays(3);
    List<Loan> listofLoans = new ArrayList<>();

    public void setLoanObject(Loan loanObject) {
        this.loanObject = loanObject;
    }

    private Loan loanObject;
    public int getAvailableFunds() {
        return availableFundsAmount;
    }

    public List<Loan> getListOfLoans() {
        return listofLoans;
    }

    public void depositFunds(int amount) {
        availableFundsAmount += amount;
    }

    public Loan processLoan(LoanApplicant la, int requestedAmount) {


        try {

            if(availableFundsAmount<requestedAmount) {
                    loanObject = new Loan();
                    loanObject.setStatus("On Hold");
                    loanObject.setLoan_amount(requestedAmount);

            }
            else {
                loanObject = checkLoanQualification(la, requestedAmount);
                if(loanObject.getQualification().equalsIgnoreCase("not qualified")){
                    loanObject.setStatus("DO NOT PROCEED");

                }
                else {
                    loanObject.setStatus("Approved");
                   // System.out.println("date -3 ==>"+currentDateMinusThree);
                    pendingFundsToProcess=requestedAmount;
                    availableFundsAmount-=pendingFundsToProcess;

                }
            }
            listofLoans.add(loanObject);
            return loanObject;


        }catch(NullPointerException ne){
           throw new NullPointerException("null pointer exception");
        }


    }

    public Loan checkLoanQualification(LoanApplicant la, int requestedAmount) {
        Loan loan;
        if (la != null) {

            loan = new Loan();

        int savingsWorth = (la.getSavings() * 100) / requestedAmount;

            if (la.getDti() < 36 && la.getCredit_score() > 620 && savingsWorth >= 25) {
                loan.setLoan_amount(requestedAmount);
                loan.setLoanApplicant(la);
                loan.setQualification("qualified");
                loan.setStatus("qualified");
                loan.setApprovedDate(currentDate.minusDays(4));
            } else if (la.getDti() < 36 && la.getCredit_score() > 620 && savingsWorth < 25) {

                loan.setLoan_amount(la.getSavings() * 4);
                loan.setLoanApplicant(la);
                loan.setQualification("partially qualified");
                loan.setStatus("qualified");
                loan.setApprovedDate(currentDateMinusThree);
            } else if (la.getDti() < 36 && la.getCredit_score() < 620 && savingsWorth >= 25) {

                loan.setLoan_amount(0);
                loan.setLoanApplicant(la);
                loan.setQualification("not qualified");
                loan.setStatus("denied");

            } else if (la.getDti() > 36 && la.getCredit_score() > 620 && savingsWorth >= 25) {
                loan.setLoan_amount(0);
                loan.setLoanApplicant(la);
                loan.setQualification("not qualified");
                loan.setStatus("denied");
            }

            return loan;
        }
        else
            throw new NullPointerException(" Loan Object is Null");
    }


    public int getPendingFundsToProcess() {

        return pendingFundsToProcess;
    }

    public Loan getLoanObject(){
        return loanObject;
    }

    public void checkExpiredLoans(){
        for(Loan loan : listofLoans){
            LocalDate loanDate = loan.getApprovedDate();
            if(loanDate.isBefore(currentDateMinusThree)){
                loan.setStatus("Expired");
            }

        }
    }


    public void postProcessingOfLoanApplication(Loan loan, String s) {

        if(s.equalsIgnoreCase("accepted")){
            pendingFundsToProcess-=loan.getLoan_amount();
            this.loanObject.setStatus("ACCEPTED");
        }
        else
        {
            pendingFundsToProcess-=loan.getLoan_amount();
            availableFundsAmount+=loan.getLoan_amount();
            this.loanObject.setStatus("REJECTED");
        }

    }

    public List<Loan> filterLoansByStatus(String status) {
        List<Loan> filteredList = new ArrayList<>();
            for(Loan loan : listofLoans){
                if(loan.getStatus().equalsIgnoreCase(status)){
                    filteredList.add(loan);
                }
            }

        return filteredList;
    }
}
