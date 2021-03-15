package lab7;
import tester.*;

// Bank Account Examples and Tests
public class Examples {

    public Examples(){ reset(); }
    
    Account check1;
    Account check2;
    Account savings1;
    Account savings2;
    Account credit1;
    Account credit2;
    Bank ally; 
    ILoA allyAccounts, allyAccounts2;

    
    // Initializes accounts to use for testing with effects.
    // We place inside reset() so we can "reuse" the accounts
    public void reset(){
        
        // Initialize the account examples
        check1 = new Checking(01, 100, "First Checking Account", 20);
        check2 = new Checking(02, 1000, "Second Checking Account", 25);
        savings1 = new Savings(03, 200, "First Savings Account", 2.5);
        savings2 = new Savings(04, 2000, "Second Savings Account", 1.5);
        credit1 = new Credit(05, 300, "First Credit Line", 5000, 1.5);
        credit2 = new Credit(06, 3000, "Second Credit Line", 10000, 2);
        ally = new Bank ("Ally"); 
        allyAccounts = new ConsLoA (check1, new MtLoA()); 
        allyAccounts2 = new ConsLoA (check2, new ConsLoA (check1, new MtLoA())); 
   
           
    }
    
    // Tests the exceptions we expect to be thrown when
    //   performing an "illegal" action.
    public void testExceptions(Tester t){
        reset();
        t.checkException("Test for invalid Checking withdraw",
                         new RuntimeException("1000 is not available"),
                         this.check1,
                         "withdraw",
                         1000);
    }
    
    // Test the deposit method(s)
    public void testDeposit(Tester t){
        reset();
        t.checkExpect(check1.withdraw(25), 75);
        t.checkExpect(check1, new Checking(1, 75, "First Checking Account", 20));
        reset();
    }
    
    public void testAdd (Tester t) {
      reset();
      t.checkExpect(ally.accounts, new MtLoA()); 
      ally.add(check1);
      t.checkExpect(ally.accounts, this.allyAccounts); 
    }
    
    
    public void testBankDW (Tester t) {
      reset();
      t.checkExpect(check1.balance, 100);
      ally.add(check1);
      ally.add(check2);
      t.checkExpect(ally.accounts, this.allyAccounts2); 
      ally.deposit(01, 25);
      t.checkExpect(check1.balance, 125);
      t.checkException("Test for absent acct num",
          new RuntimeException("3 does not exist"),
          this.ally, 
          "deposit",
          03,
          25); 
      ally.withdraw(01, 25);
      t.checkExpect(check1.balance, 100);
      
      
    }
    
    
}
