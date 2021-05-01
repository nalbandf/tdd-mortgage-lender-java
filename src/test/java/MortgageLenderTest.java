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
    public void setUp(){
       ml = new MortgageLender();
    }

    @Test
    public void testCheckZeroAvailableFunds(){
        int availableFunds = ml.getAvailableFunds();
        assertEquals(0,availableFunds);
    }

    @Test
    public void testCheckAvailableFundsIsNotZero(){
         ml.depositFunds(100000);
        assertNotEquals(0,ml.getAvailableFunds());
    }

    @Test
    public void testGetAvailableFunds(){
        ml.depositFunds(100000);
        assertEquals(100000,ml.getAvailableFunds());
    }

    @Test
    public void testAddAvailableFunds(){
        ml.depositFunds(100000);
        int current_amount = ml.getAvailableFunds();
        assertTrue(current_amount>0);
        int deposit_amount = 200000;
        assertNotEquals(deposit_amount,ml.getAvailableFunds());
        ml.depositFunds(deposit_amount);
        int total= current_amount+deposit_amount;
        assertTrue(total==ml.getAvailableFunds());

    }

    @Test
    public void testQualifyFullLoanAmount(){
        LoanApplicant la = new LoanApplicant(21,700,100000);
        int requestedAmount = 300000;
        Loan loan = ml.processLoan(la,requestedAmount);
        assertEquals("qualified",loan.getQualification());
        assertEquals(250000, loan.getLoan_amount());
        assertEquals("qualified",loan.getStatus());
    }



}
