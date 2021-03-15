package assignment1;

public class Person {
  String name;
  int yob;
  String state;
  boolean citizen;
  
  Person(String name, int yob, String state, boolean citizen) {
    this.name = name;
    this.yob = yob;
    this.state = state;
    this.citizen = citizen ;
    
  }
}

class ExamplesPersons {
  ExamplesPersons () {} 
  
  Person rachel = new Person ("Rachel", 1994, "MA", true);
  Person jackie = new Person ("Jackie", 1920, "NY", true);
  Person ella = new Person ("Ella", 1996, "CA", false);
  
}
