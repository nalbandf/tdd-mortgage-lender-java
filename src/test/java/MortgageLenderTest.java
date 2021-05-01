import com.cognizant.Loan;
import com.cognizant.LoanApplicant;
import com.cognizant.MortgageLender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MortgageLenderTest {

    MortgageLender ml;

    @BeforeEach
    public void setUp() {
        ml = new MortgageLender();
    }

    @Test
    public void testCheckZeroAvailableFunds() {
        int availableFunds = ml.getAvailableFunds();
        assertEquals(0, availableFunds);
    }

    @Test
    public void testCheckAvailableFundsIsNotZero() {
        ml.depositFunds(100000);
        assertNotEquals(0, ml.getAvailableFunds());
    }

    @Test
    public void testGetAvailableFunds() {
        ml.depositFunds(100000);
        assertEquals(100000, ml.getAvailableFunds());
    }

    @Test
    public void testAddAvailableFunds() {
        ml.depositFunds(100000);
        int current_amount = ml.getAvailableFunds();
        assertTrue(current_amount > 0);
        int deposit_amount = 200000;
        assertNotEquals(deposit_amount, ml.getAvailableFunds());
        ml.depositFunds(deposit_amount);
        int total = current_amount + deposit_amount;
        assertTrue(total == ml.getAvailableFunds());

    }

    @Test
    public void testQualifyFullLoanAmountSuccess() {
        ml.depositFunds(1000000);
        LoanApplicant la = new LoanApplicant(21, 700, 100000);
        int requestedAmount = 250000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertEquals("qualified", loan.getQualification());
        assertEquals(250000, loan.getLoan_amount());
        assertEquals("Approved", loan.getStatus());
    }

    @Test
    public void testNotQualifiedForGreaterDTI() {
        ml.depositFunds(1000000);
        LoanApplicant la = new LoanApplicant(37, 700, 100000);
        int requestedAmount = 250000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertEquals("not qualified", loan.getQualification());
        assertEquals(0, loan.getLoan_amount());
        assertEquals("DO NOT PROCEED", loan.getStatus());
    }

    @Test
    public void testNotQualifiedWithLessCreditScore() {
        ml.depositFunds(1000000);
        LoanApplicant la = new LoanApplicant(30, 600, 100000);
        int requestedAmount = 250000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertEquals("not qualified", loan.getQualification());
        assertEquals(0, loan.getLoan_amount());
        assertEquals("DO NOT PROCEED", loan.getStatus());
    }

    @Test
    public void testPartiallyQualified() {
        ml.depositFunds(1000000);
        LoanApplicant la = new LoanApplicant(30, 700, 50000);
        int requestedAmount = 250000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertEquals("partially qualified", loan.getQualification());
        assertEquals(200000, loan.getLoan_amount());
        assertEquals("Approved", loan.getStatus());
    }

    @Test
    public void testNotNullLoanObject() {
        LoanApplicant la = new LoanApplicant(30, 700, 50000);
        int requestedAmount = 250000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertNotNull(loan);
    }

    @Test
    public void testNullLoanObject() {
        ml.depositFunds(1000000);
        LoanApplicant la = null;
        int requestedAmount = 250000;
        //Loan loan = ml.processLoan(la,requestedAmount);
        assertThrows(NullPointerException.class, () -> {
            ml.processLoan(la, requestedAmount);
        });

    }

    @Test
    public void testCheckLoanStatusOnHold(){
        ml.depositFunds(100000);
        LoanApplicant la = new LoanApplicant(30, 700, 50000);
        int requestedAmount = 125000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertEquals("On Hold", loan.getStatus());


    }

    @Test
    public void testCheckLoanStatusApprovedWithQualified(){
        ml.depositFunds(200000);
        LoanApplicant la = new LoanApplicant(30, 700, 100000);
        int requestedAmount = 125000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertEquals("Approved", loan.getStatus());


    }
    @Test
    public void testCheckLoanStatusApprovedWithoutQualified(){
        ml.depositFunds(200000);
        LoanApplicant la = new LoanApplicant(37, 700, 100000);
        int requestedAmount = 125000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertEquals("DO NOT PROCEED", loan.getStatus());


    }

    @Test
    public void checkPendingFundsToProcess(){
        ml.depositFunds(100000);
        LoanApplicant la = new LoanApplicant(30, 700, 100000);
        int requestedAmount = 25000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertEquals(25000, ml.getPendingFundsToProcess());
        assertEquals(75000,ml.getAvailableFunds());

    }

    @Test
    public void testProcessResponseForApprovedLoans(){
        ml.depositFunds(100000);
        LoanApplicant la = new LoanApplicant(30, 700, 100000);
        int requestedAmount = 25000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertEquals(25000, ml.getPendingFundsToProcess());
        assertEquals(75000,ml.getAvailableFunds());
        la.setApprovalStatus("accepted");
        String applicantApprovalStatus = la.approveLoan(loan);
        assertEquals("accepted",applicantApprovalStatus);
        ml.postProcessingOfLoanApplication(loan,applicantApprovalStatus);
        assertEquals(0, ml.getPendingFundsToProcess());
        assertEquals("ACCEPTED", ml.getLoanObject().getStatus());


    }
    @Test
    public void testProcessResponseForRejectedLoans(){
        ml.depositFunds(100000);
        LoanApplicant la = new LoanApplicant(30, 700, 100000);
        int requestedAmount = 25000;
        Loan loan = ml.processLoan(la, requestedAmount);
        assertEquals(25000, ml.getPendingFundsToProcess());
        assertEquals(75000,ml.getAvailableFunds());
        la.setApprovalStatus("Rejected");
        assertEquals("Rejected",la.getApprovalStatus());
        ml.postProcessingOfLoanApplication(loan,la.getApprovalStatus());
        assertEquals(0, ml.getPendingFundsToProcess());
        assertTrue(ml.getAvailableFunds()>75000);
        assertEquals("REJECTED", ml.getLoanObject().getStatus());


    }


}
