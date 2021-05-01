package com.cognizant;

public class MortgageLender {

    int availableFundsAmount;
    int pendingFundsToProcess;

    public void setLoanObject(Loan loanObject) {
        this.loanObject = loanObject;
    }

    private Loan loanObject;
    public int getAvailableFunds() {
        return availableFundsAmount;
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
                    pendingFundsToProcess=requestedAmount;
                    availableFundsAmount-=pendingFundsToProcess;

                }
            }
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
            } else if (la.getDti() < 36 && la.getCredit_score() > 620 && savingsWorth < 25) {

                loan.setLoan_amount(la.getSavings() * 4);
                loan.setLoanApplicant(la);
                loan.setQualification("partially qualified");
                loan.setStatus("qualified");

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
}
