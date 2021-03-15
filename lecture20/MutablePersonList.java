package lecture20;

class MutablePersonList {
  Sentinel sentinel;
  
  MutablePersonList(Sentinel sentinel) {
    this.sentinel = new Sentinel (new MtLoPerson());
  }
  
  void removePerson (String name) {
    this.sentinel.rest.removePersonHelp(name, this.sentinel);
  }
  
  void addPerson (String name) {}
  
  
}

// represents a sentinel at the start, a node in the middle, or 
// the empty at the end of a list 
abstract class APersonList {
  
  abstract void removePersonHelp(String name, ANode prev); 
  
}

// represents a node in a list that has some list after it
abstract class ANode extends APersonList {
  APersonList rest; 
  
  ANode (APersonList rest) {
    this.rest = rest; 
  }
  
}

class Sentinel extends ANode {
  
  Sentinel (APersonList rest) {
    super(rest); 
  }
  
  void removePersonHelp (String name, ANode prev) {
    throw new RuntimeException ("Can't try to remove on a sentinel");
  }
  
}

class ConsLoPerson extends ANode {
  Person data;
  
  ConsLoPerson (Person data, APersonList rest) {
    super(rest); 
    this.data = data;   
  }
  
  void removePersonHelp (String name, ANode prev) {
    if (this.data.name.equals(name)) {
      prev.rest = this.rest; 
    }
    this.rest.removePersonHelp(name, prev);
  }
  
}

class MtLoPerson extends APersonList {
  
  MtLoPerson() {}
  
  void removePersonHelp(String name, ANode prev) {
    return ; 
  }
  
  
  
    
  
}




