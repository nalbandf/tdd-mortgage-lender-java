package com.cognizant;

public class LoanApplicant {

    private int dti;
    private int credit_score;
    private int savings;

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    private String approvalStatus;

    public LoanApplicant(int dti, int credit_score, int savings){
        this.dti = dti;
        this.credit_score = credit_score;
        this.savings = savings;
    }

    public int getDti() {
        return dti;
    }

    public void setDti(int dti) {
        this.dti = dti;
    }

    public int getCredit_score() {
        return credit_score;
    }

    public void setCredit_score(int credit_score) {
        this.credit_score = credit_score;
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }


    public String approveLoan(Loan loan) {
        return approvalStatus;
    }
}
