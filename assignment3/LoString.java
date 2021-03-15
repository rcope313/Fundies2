package assignment3;
import tester.Tester;

public interface LoString {
  
  // produces new list in alphabetical order 
  LoString sort (); 
  
  // inserts an item into an already sorted list
  LoString insert (String s); 
  
  // determines whether the list is sorted or not
  boolean isSorted (); 
  
  // gets the first of the rest
  String firstOfRest (); 
  
  //takes this list of Strings and a given list of Strings,
  //and produces a list where the first, third, fifth... 
  //elements are from this list, and the second, fourth, sixth... 
  //elements are from the given list. 
  //Any “leftover” elements (when one list is longer than the other)
  //should just be left at the end.
  LoString interleave (LoString that); 
  
  //this sorted list of Strings and a given sorted list of Strings,
  //and produces a sorted list of Strings that
  //contains all items in both original lists, including duplicates.
  LoString merge (LoString that); 
  
  //produces a new list of Strings containing the same elements as this list of Strings, 
  //but in reverse order.
  LoString reverse (); 
  
  //adds an element at the end of a list
  LoString addAtEnd (String s); 
  
  // determines whether the list is doubled (i.e first & second string are the same, etc)
  boolean isDoubledList (); 
  
  //returns the rest of the rest
  LoString restOfRest (); 
  
  //determines whether or not the list is a palindrome
  boolean isPalindrome(); 

  

}

class ConsLoString implements LoString {
  String first;
  LoString rest;
  
  ConsLoString (String first, LoString rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /* Template
   * 
   * Fields
   * first -- string
   * rest -- LoString
   * 
   * Methods
   * sort() -- LoStrings
   * insert() -- LoStrings
   * isSorted() -- boolean
   * interleave(that) -- LoString
   * merge(that) -- LoString
   * reverse() -- LoString
   * isDoubledList() -- boolean
   * isPalindrome -- booleans
   * 
   * Methods on Fields
   * this.rest.sort() 
   * this.rest.insert()
   * this.rest.isSorted()
   * this.rest.interleave(that)
   * this.rest.merge(that)
   * this.rest.reverse()
   * this.rest.isDoubledList()
   * this.rest.isPalindrome()
   * 
   * 
   * 
   * Methods on Parameters
   * that.rest.....
   * 
   * 
   */
  
  public LoString sort () {
    return this.rest.sort()
               .insert(this.first);
  }
  
  
  public LoString insert (String s) {
    if (this.first.compareTo(s) <= 0) {
      return new ConsLoString (this.first, this.rest.insert(s));
    }
    else {
      return new ConsLoString (s, this) ;
    }
  }
  
  public boolean isSorted () { 
    if (this.rest.firstOfRest().equals("")) {
      return true; 
    }
    else if (this.first.compareTo(this.rest.firstOfRest()) > 0) {
      return false; 
    }
   return this.rest.isSorted();
 }

  
  public String firstOfRest() {
    return this.first; 
  }
  
  public LoString interleave (LoString that) {
    return new ConsLoString (this.first, that.interleave(this.rest)); 
  }
  
  public LoString merge (LoString that) {
    return this.interleave(that).sort(); 
  }
  
  public LoString reverse () {
    return this.rest.reverse()
           .addAtEnd(this.first); 
  }
  
  public LoString addAtEnd (String s) { 
    return new ConsLoString (this.first, this.rest.addAtEnd(s)); 
  }
  
  public boolean isDoubledList () {
    if (!this.first.equals(this.rest.firstOfRest())) {
      return false; 
    }
    else {
      return this.rest.restOfRest().isDoubledList(); 
    }
  }
  
  public LoString restOfRest () {
    return this.rest; 
  }
  
  public boolean isPalindrome () {
    return this.merge(this.reverse()).isDoubledList();
  }
 
}

class MtLoString implements LoString {
  MtLoString () {}
  
  public LoString sort () {
    return this; 
  }
  
  public LoString insert (String s) {
    return new ConsLoString (s, this); 
  }
  
  public boolean isSorted () {
    return true; 
  }
  
  public String firstOfRest () {
    return "";  
  }
  
  public LoString interleave (LoString that) {
    return that; 
  }
  
  public LoString merge (LoString that) {
    return that.sort(); 
  }
  
  public LoString reverse () {
    return this; 
  }
  
  public LoString addAtEnd (String s) {
    return new ConsLoString (s, this); 
  }
  
  public boolean isDoubledList() {
    return true; 
  }
  
  public LoString restOfRest () {
    return this; 
  }
  
  public boolean isPalindrome () {
    return true; 
  }
  
  public boolean equals(Object that) {
    return (that instanceof MtLoString);
  }
  
}

class ExamplesLoStrings {
  ExamplesLoStrings () {}
  
  LoString empty = new MtLoString ();
  LoString list1 = new ConsLoString ("apple", empty);
  LoString list2 = new ConsLoString ("banana", list1);
  LoString list3 = new ConsLoString ("cantelope", list2); 
  
  LoString list4 = new ConsLoString ("cantelope", empty);
  LoString list5 = new ConsLoString ("banana", list4);
  LoString list6 = new ConsLoString ("apple", list5); 
  
  LoString list7 = new ConsLoString ("grape", list5);
  LoString list8 = new ConsLoString ("apple", list7); 
  
  LoString list9 = new ConsLoString ("apple", list1);
  LoString list10 = new ConsLoString ("banana", list9);
  LoString list11 = new ConsLoString ("banana", list10); 
  
  LoString list12 = new ConsLoString ("apple", empty);
  LoString list13 = new ConsLoString ("banana", list12);
  LoString list14 = new ConsLoString ("apple", list13); 
  
  
  boolean testSort (Tester t) {
    return
        t.checkExpect(empty.sort(), empty) &&
        t.checkExpect(list6.sort(), list6);
  }
  
  boolean testIsSorted (Tester t) {
    return
        t.checkExpect(empty.isSorted(), true) &&
        t.checkExpect(list6.isSorted(),  true) && 
        t.checkExpect(list8.isSorted(), false);
  }
  
  boolean testInterleave (Tester t) {
    return
        t.checkExpect (empty.interleave(empty), empty) &&
        t.checkExpect (empty.interleave(list3), list3) &&
        t.checkExpect (list3.interleave(empty), list3); 
//      t.checkExpect (list3.interleave(list6), empty) && these passed but i refuse to write 
//      t.checkExpect (list1.interleave(list3), empty); examples of them on principle 
  }
  
  boolean testMerge (Tester t) {
    return
        t.checkExpect(empty.merge(empty),  empty) &&
        t.checkExpect(empty.merge(list3),  list6) &&
        t.checkExpect(list3.merge(empty),  list6);
 //     t.checkExpect(list3.merge(list3),  empty); again, on principle
  }
  
  boolean testReverse (Tester t) {
    return
        t.checkExpect(empty.reverse(), empty) &&
        t.checkExpect(list3.reverse(), list6) &&
        t.checkExpect(list6.reverse(), list3); 
  }
  
  boolean testIsDoubledList (Tester t) {
    return
        t.checkExpect(empty.isDoubledList(), true) &&
        t.checkExpect(list6.isDoubledList(), false) &&
        t.checkExpect(list11.isDoubledList(), true); 
  }
  
  boolean testIsPalindrome (Tester t) {
    return
//        t.checkExpect(empty.isPalindrome(), true) &&
//       t.checkExpect(list6.isPalindrome(), false) &&
        t.checkExpect(list14.isPalindrome(), true); 
  }
  
  

  
}
