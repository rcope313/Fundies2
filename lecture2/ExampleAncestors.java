package lecture2;

// examples and tests for the class hierarchy that represents 
// ancestor trees

public class ExampleAncestors {
  ExampleAncestors () {}
  
  IAT unknown = new Unknown();
  IAT mary = new Person ("Mary", this.unknown, this.unknown);
  IAT robert = new Person ("Robert", this.unknown, this.unknown);
  IAT john = new Person ("John", this.unknown, this.unknown);
  IAT jane = new Person ("Jane", this.mary, this.robert);
  IAT dan = new Person ("Dan", this.jane, this.john);
  
  
  
  
  
      
  

}
