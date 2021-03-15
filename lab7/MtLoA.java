package lab7;

// Represents the empty List of Accounts
public class MtLoA implements ILoA{
    
    MtLoA(){}
    
    public void depositHelper (int acctNum, int amt) {
      throw new RuntimeException (Integer.toString(acctNum) + " does not exist"); 
    }
    
    public void withdrawHelper (int acctNum, int amt) {
      throw new RuntimeException (Integer.toString(acctNum) + " does not exist"); 
    }
}

