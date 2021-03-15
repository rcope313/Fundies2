package assignment1;

public interface Taco {

}

class EmptyShell implements Taco {
  boolean softshell;
  
  EmptyShell (boolean softshell) {
    this.softshell = softshell; 
    
  } 
}

class Filled implements Taco {
  Taco taco;
  String filling;
  
  Filled (Taco taco, String filling) {
    this.taco = taco;
    this.filling = filling;
    
  }  
}

class ExamplesTacos {
  ExamplesTacos () {} 
  
  Taco order1 = new Filled (new EmptyShell (true), "Carnitas, Salsa, Lettuce, Cheddar Cheese");
  Taco order2 = new Filled (new EmptyShell (false), "Veggies, Guacamole, Sour Cream");
  
}
  
  
