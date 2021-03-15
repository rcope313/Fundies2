package lab7;

// Represents a checking account
public class Checking extends Account{

    int minimum; // The minimum account balance allowed

    public Checking(int accountNum, int balance, String name, int minimum){
        super(accountNum, balance, name);
        this.minimum = minimum;
    }
    
    public int withdraw(int amount) {
      if (this.balance - amount < minimum) {
        throw new RuntimeException (Integer.toString(amount) + " is not available"); 
      }
      else {
        this.balance = this.balance - amount;
        return this.balance; 
        
      }
    }
    
    public int deposit(int amt) {
      this.balance = this.balance + amt; 
      return this.balance; 
      
    }
      
    
}
