package lecture8;

import tester.Tester;

public interface LoIntegers {
  
  // determine if list contains:
  // - a number that is even
  // - number that is pos & odd
  // - number between 5 and 10, inclusive
  boolean contains (); 
  
  // determine if list contains a number that is even
  boolean even (); 
  
  // determine if list contains a number both positive and odd
  boolean posAndOdd (); 
  
  // determine if list contain a number between 5 and 10, inclusive
  boolean fiveThruTen (); 
  
  
}

class ConsLoIntegers implements LoIntegers {
  int first;
  LoIntegers rest;
  
 
  
  ConsLoIntegers (int first, LoIntegers rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /* Template
   * 
   * Fields:
   * first -- int
   * rest -- int
   * 
   * Methods:
   * even -- boolean
   * evenDup -- int
   * posAndOdd -- boolean
   * fiveThruTen -- boolean
   * contains -- bolean
   * containsNoDup - boolean
   * 
   * Methods on Fields: 
   * this.rest.even()
   * this.rest.evenDup(); 
   * this.rest.posAndOdd(); 
   * this.rest.fiveThurTen(); 
   * this.rest.contains(); 
   * this.rest.containsNoDup(); 
   */
  
  //////////////////////
  
  public boolean contains() {
    if (this.even() &&
        this.posAndOdd() &&
        this.fiveThruTen()) {
      return true;
    }
    else {
      return false;
    }
  }
 
 
  
  //////////////////////////
  
  public boolean even() {
    if (this.first % 2 == 0) {
      return true;
    }
    else {      
      return this.rest.even(); 
    }
  }
  
 
  
  
  //////////////////////
  
  
  public boolean posAndOdd() {
    if (this.first > 0 &&
        this.first % 2 != 0) {
      return true;
    }
    else {      
      return this.rest.posAndOdd(); 
    }
  }
  
  
  
  ///////////////////////
  
  public boolean fiveThruTen() {
    if (this.first >= 5 &&
        this.first <= 10) {
      return true;
    }
    else {      
      return this.rest.fiveThruTen(); 
    }
  }
  
  
  
  ///////////////////////
  
  
  
}

class MtLoIntegers implements LoIntegers {
  MtLoIntegers () {
    
  }
  
  /* Template
   * 
   * Fields:
   * 
   * Methods:
   * even -- boolean
   * posAndOdd -- boolean
   * fiveThruTen -- bolean
   * contains -- bolean
   */
  
  ///////
 
 
  public boolean contains() {
    return false;
  }
  
  //////////
  
  public boolean even() {
    return false; 
  }
  
  
  
  /////////////
  
  public boolean posAndOdd() {
    return false; 
  }
  
  
  
  ////////////////
  
  public boolean fiveThruTen() {
    return false; 
  }
  
  
  
}

class ExamplesLoIntegers {
  ExamplesLoIntegers () {}
    
  LoIntegers empty = new MtLoIntegers (); 
  LoIntegers list1 = new ConsLoIntegers (1, this.empty); // (1, empty)
  LoIntegers list2 = new ConsLoIntegers (5, this.list1); // (5, 1, empty)
  LoIntegers list3 = new ConsLoIntegers (8, this.list2); // (8, 5, 1, empty)
  LoIntegers list4 = new ConsLoIntegers (10, this.list3); // (10, 8, 5, 1, empty)
  
  boolean testEven (Tester t) {
    return
        t.checkExpect(this.list1.even(), false) &&
        t.checkExpect(this.list3.even(), true);
  }
  
  boolean testPosAndOdd (Tester t) {
    return
        t.checkExpect(this.list4.posAndOdd(), true) &&
        t.checkExpect(this.empty.posAndOdd(), false); 
  }
  
  boolean testFiveThruTen (Tester t) {
    return
        t.checkExpect(this.list1.fiveThruTen(), false) &&
        t.checkExpect(this.list2.fiveThruTen(), true);
    
  }
  
  boolean testContains (Tester t) {
    return 
        t.checkExpect(this.list1.contains(), false) &&
        t.checkExpect(this.list3.contains(), true); 
   
  }
}
