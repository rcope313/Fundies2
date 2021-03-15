package lecture20;
import tester.Tester;

interface ILoPerson {
  
  boolean contains (String name); 
  // finds the person on this list with the given names and
  // returns their phone number,
  // or -1 if no such person is found
  int findPhoneNumber (String name); 
  void changePhone (String name, int newNum); 
  void removePerson (String name);
  void removePersonHelper (String name, ConsLoPerson prev); 
  
  
}

class MtLoPerson implements ILoPerson {
  MtLoPerson() {}
  
  public boolean contains (String name) {
    return false; 
  }
  
  public int findPhoneNumber (String name) {
    return -1; 
  }
  
  public void changePhone (String name, int newNum) {
    return ; 
  }
  
  public void removePerson (String name) {
    return ;
  }
  
  public void removePersonHelper (String name, ConsLoPerson prev) {
    return ;
  }
}

class ConsLoPerson implements ILoPerson {
  Person first;
  ILoPerson rest;
  
  ConsLoPerson (Person first, ILoPerson rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public boolean contains (String name) {
    return 
        this.first.name.equals(name) 
        || this.rest.contains(name);  
  }
  
  public int findPhoneNumber (String name) {
    if (this.first.sameName(name)) {
      return this.first.phone; 
    }
    return this.rest.findPhoneNumber(name); 
  }
  
  public void changePhone (String name, int newNum) {
    if (this.first.sameName(name)) {
      this.first.changeNum(newNum);
    }
    this.rest.changePhone(name, newNum);
  }
  
  public void removePerson (String name) {
    this.rest.removePersonHelper(name, this); 
  }
    
    
  public void removePersonHelper (String name, ConsLoPerson prev) { 
    if (this.first.sameName(name)) {
      prev.rest = this.rest; // modify the previous node to bypass this node 
    }
    this.rest.removePersonHelper(name, this); 
  }

}

class Person {
  String name;
  int phone;
  Person(String name, int phone) {
    this.name = name;
    this.phone = phone;
  }
  // Returns true when the given person has the same name and phone number as this person
  boolean samePerson(Person that) {
    return this.name.equals(that.name) && this.phone == that.phone;
  }
 
  // Returns true when the given person has the same name as a given String
  boolean sameName(String name) {
    return this.name.equals(name);
  }
  
  // EFFECT: modifies this person's phone number to the given one
  void changeNum(int newNum) {
    this.phone = newNum; 
  }
}

class ExamplePhoneLists {
  
 
  ILoPerson friends, family, work;
  Person anne, bob, clyde, dana, eric, frank, gail, henry, irene, jenny; 
  
  void initData() {
    
    this.anne = new Person("Anne", 1234);
    this.bob = new Person("Bob", 3456);
    this.clyde = new Person("Clyde", 6789);
    this.dana = new Person("Dana", 1357);
    this.eric = new Person("Eric", 12469);
    this.frank = new Person("Frank", 7294);
    this.gail = new Person("Gail", 9345);
    this.henry = new Person("Henry", 8602);
    this.irene = new Person("Irene", 91302);
    this.jenny = new Person("Jenny", 8675309);
   
    this.work =
        new ConsLoPerson(this.bob, new ConsLoPerson(this.clyde,
          new ConsLoPerson(this.dana, new ConsLoPerson(this.eric,
            new ConsLoPerson(this.frank, new MtLoPerson())))));
      // We're friends with everyone at work, and also with other people
      this.friends =
        new ConsLoPerson(this.anne, new ConsLoPerson(this.gail,
          new ConsLoPerson(this.henry, this.work)));
  }
 
  
  // Tests removing the first person in a list
  void testRemoveFirstPerson (Tester t) {
    this.initData();
    ILoPerson list1 =
        new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
          new ConsLoPerson(this.henry, new MtLoPerson())));
      ILoPerson list2 =
        new ConsLoPerson(this.anne, new ConsLoPerson(this.dana,
          new ConsLoPerson(this.gail, new MtLoPerson())));
    // check initial test conditions
    t.checkExpect(list1.contains("Anne"), true);
    t.checkExpect(list2.contains("Anne"), true); 
    // modify list1
    list1.removePerson("Anne");
    // check that list1 has been modified
    t.checkExpect(list1.contains("Anne"), false);
    // but list2 hasn't been modified
    t.checkExpect(list2.contains("Anne"), true);
    
  }
  
  void testRemoveMiddlePerson (Tester t) {
    this.initData();
    this.initData();
    ILoPerson list1 =
      new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
        new ConsLoPerson(this.henry, new MtLoPerson())));
    ILoPerson list2 =
      new ConsLoPerson(this.dana, new ConsLoPerson(this.clyde,
        new ConsLoPerson(this.gail, new MtLoPerson())));
    // Check initial conditions
    t.checkExpect(list1.contains("Clyde"), true);
    t.checkExpect(list2.contains("Clyde"), true);
    // Modify list1
    list1.removePerson("Clyde");
    // Check that list1 has been modified...
    t.checkExpect(list1.contains("Clyde"), false);
    // ...but that list2 has not
    t.checkExpect(list2.contains("Clyde"), true);
    
  }
  
  void testRemoveLastPerson(Tester t) {
    this.initData();
    ILoPerson list1 =
      new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
        new ConsLoPerson(this.henry, new MtLoPerson())));
    ILoPerson list2 =
      new ConsLoPerson(this.dana, new ConsLoPerson(this.gail,
        new ConsLoPerson(this.henry, new MtLoPerson())));
    // Check initial conditions
    t.checkExpect(list1.contains("Henry"), true);
    t.checkExpect(list2.contains("Henry"), true);
    // Modify list1
    list1.removePerson("Henry");
    // Check that list1 has been modified...
    t.checkExpect(list1.contains("Henry"), false);
    // ...but that list2 has not
    t.checkExpect(list2.contains("Henry"), true);
  
  }
  
  void removeCoworker (Tester t) {
    this.initData();
    // test that eric is a coworker and a friend
    t.checkExpect(this.work.findPhoneNumber("Eric"), this.eric.phone);
    t.checkExpect(this.friends.findPhoneNumber("Eric"), this.eric.phone);
    // Remove eric from coworkers
    this.work.removePerson("Eric");
    // check that Eric is no longer a coworker
    t.checkExpect(this.work.findPhoneNumber("Eric"), -1);
    // Check that Eric is still a friend
    t.checkExpect(this.friends.findPhoneNumber("Eric"), this.eric.phone);
  

    
    
    
    
    
  }
    
  
}
  
  
