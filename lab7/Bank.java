package lab7;

// Represents a Bank with list of accounts
public class Bank {
    
    String name;
    ILoA accounts;
    
    public Bank(String name){
        this.name = name; 
        // Each bank starts with no accounts
        this.accounts = new MtLoA();
    }
    
    // EFFECT: add a new acct to this bank
    void add (Account a) {
      this.accounts = new ConsLoA (a, this.accounts); 
    }
    
    void deposit (int acctNum, int amt) {
      this.accounts.depositHelper(acctNum, amt); 
    }
    
    void withdraw (int acctNum, int amt) {
      this.accounts.withdrawHelper(acctNum, amt); 
    } 

    // EFFECT: Remove the given account from this Bank
    void removeAccount(int acctNo)  {
      
      
    }
    

}
