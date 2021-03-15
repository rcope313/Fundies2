package lab7;

// Represents a savings account
public class Savings extends Account{

    double interest; // The interest rate

    public Savings(int accountNum, int balance, String name, double interest){
        super(accountNum, balance, name);
        this.interest = interest;
        
    }
    
   public int withdraw(int amount) {
     if (this.balance - amount < 0) {
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
