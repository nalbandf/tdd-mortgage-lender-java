package com.cognizant;

import java.time.LocalDate;

public class Loan {
    private String qualification;
    private int loan_amount;
    private String status;
    private LocalDate approvedDate;

    public LoanApplicant getLoanApplicant() {
        return loanApplicant;
    }

    public void setLoanApplicant(LoanApplicant loanApplicant) {
        this.loanApplicant = loanApplicant;
    }

    private LoanApplicant loanApplicant;

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }


    public int getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(int loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }
}
