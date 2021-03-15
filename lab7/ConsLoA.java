package lab7;

// Represents a non-empty List of Accounts...
public class ConsLoA implements ILoA{

    Account first;
    ILoA rest;

    public ConsLoA(Account first, ILoA rest){
        this.first = first;
        this.rest = rest;
    }
    
    public void depositHelper (int acctNum, int amt) {
      if (this.first.accountNum == acctNum) {
        this.first.deposit(amt); 
      } else
      this.rest.depositHelper(acctNum, amt);
    }
    
    public void withdrawHelper (int acctNum, int amt) {
      if (this.first.accountNum == acctNum) {
        this.first.withdraw(amt); 
      } else
      this.rest.withdrawHelper(acctNum, amt);
    }
}