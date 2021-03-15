package lecture2;
// to represent an ancestor tree
public interface IAT {}

// to represent an unknown member of an ancestor tree
class Unknown implements IAT{
  Unknown () {}
}

// to represent a person with the person's ancestor tree
class Person implements IAT{
  String name;
  IAT mom;
  IAT dad;
  
  Person (String name, IAT mom, IAT dad) {
    this.name = name;
    this.mom = mom;
    this.dad = dad;
    
  }
  
  
  
}




