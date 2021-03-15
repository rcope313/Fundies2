package lecture19;
import tester.Tester;

interface ILoPerson {
  
  boolean contains (String name); 
  // finds the person on this list with the given names and
  // returns their phone number,
  // or -1 if no such person is found
  int findPhoneNumber (String name); 
  void changePhone (String name, int newNum); 
  
  
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
   
    this.friends =
      new ConsLoPerson(anne, new ConsLoPerson(clyde,
        new ConsLoPerson(gail, new ConsLoPerson(frank,
          new ConsLoPerson(jenny, new MtLoPerson())))));
    this.family =
      new ConsLoPerson(anne, new ConsLoPerson(dana,
        new ConsLoPerson(frank, new MtLoPerson())));
    this.work =
      new ConsLoPerson(bob, new ConsLoPerson(clyde,
        new ConsLoPerson(dana, new ConsLoPerson(eric,
          new ConsLoPerson(henry, new ConsLoPerson(irene,
            new MtLoPerson()))))));
  }

  //In ExamplePhoneLists
  void testFindPhoneNum(Tester t) {
    this.initData();
    t.checkExpect(this.friends.contains("Frank"), true);
    t.checkExpect(this.work.contains("Zelda"), false);
  }
  
  void testFindPhoneNumber(Tester t) {
    this.initData();
    t.checkExpect(this.friends.findPhoneNumber("Frank"), 7294);
    t.checkExpect(this.work.findPhoneNumber("Zelda"), -1);
    t.checkExpect(this.friends.findPhoneNumber("Anne"),
                  this.family.findPhoneNumber("Anne")); 
  }
  
  void testChangeNum(Tester t) {
    this.initData();
    t.checkExpect(this.frank.phone, 7294);
    this.frank.changeNum(9021);
    t.checkExpect(this.frank.phone, 9021);
  }
  
  void testChangePhone (Tester t) {
    this.initData();
    t.checkExpect(this.friends.findPhoneNumber("Frank"), 7294);
    t.checkExpect(this.family.findPhoneNumber("Frank"),   
                  this.friends.findPhoneNumber("Frank"));
    t.checkExpect(this.frank.phone, 7294);
    this.family.changePhone("Frank", 9021);
    t.checkExpect(this.friends.findPhoneNumber("Frank"), 9021);
    t.checkExpect(this.family.findPhoneNumber("Frank"),   
                  this.friends.findPhoneNumber("Frank"));
    t.checkExpect(this.frank.phone, 9021);
                 
  }
  
  void testAliasing (Tester t) {
    // create two person objects which are the same
    Person johndoe1 = new Person ("John Doe", 12345); 
    Person johndoe2 = new Person ("John Doe", 12345); 
    // Alias johndoe3 to johndoe1
    Person johndoe3 = johndoe1;
    
    // check that all 3 john does are the same according to samePerson
    t.checkExpect(johndoe1.samePerson(johndoe2), true); 
    t.checkExpect(johndoe1.samePerson(johndoe3), true);
    
    // modify johndoe1
    johndoe1.name = "John Deere";
    
    // now lets try the same tests. Which one will pass?
    t.checkExpect(johndoe1.samePerson(johndoe2), true); 
    t.checkExpect(johndoe1.samePerson(johndoe3), true);
   
        
    
  }
  
  
}