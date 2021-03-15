package lab1;


////////// 1 & 2

public class Person {
  String name;
  int age;
  String gender;
  Address address; 
  
  Person(String name, int age, String gender, Address address) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.address = address;
    
  }
}

class Address {
  String city;
  String state;
  
  Address (String city, String state) {
    this.city = city;
    this.state = state;
  }
}

class ExamplesPersons {
  
  Person tim = new Person ("Tim", 23, "Male", (new Address("Boston", "Massachusetts")));
  Person kate = new Person ("Kate", 22, "Female", (new Address("Warwick", "Rhode Island")));
  Person rebecca = new Person ("Rebecca", 31, "non-binary", (new Address("Nashua", "New Hampshire")));
  Person rachel = new Person ("Rachel", 25, "Female", (new Address("Boulder", "Colorado")));
  Person kyle = new Person ("Kyle", 26, "Male", (new Address("Boulder", "Colorado")));
  Person alex = new Person ("Alex", 28, "Lame0", (new Address ("Lame City", "LamESTATE")));
  
}

//////////3

interface IDeli {}

class Soup implements IDeli {
  String name;
  int price;
  boolean vegetarian;
  
  Soup(String name, int price, boolean vegetarian) {
    this.name = name;
    this.price = price;
    this.vegetarian = vegetarian;
    
  }
}

class Salad implements IDeli {
  String name;
  int price;
  String dressing;
  boolean vegetarian;
  
  Salad(String name, int price, String dressing, boolean vegetarian) {
    this.name = name;
    this.price = price;
    this.dressing = dressing;
    this.vegetarian = vegetarian;
    
  }
}

class Sandwich implements IDeli {
  String name;
  int price;
  String bread;
  String twofillings;
  
  Sandwich(String name, int price, String bread, String twofillings) {
    this.name = name;
    this.price = price;
    this.bread = bread;
    this.twofillings = twofillings;
    
  }
}

class ExamplesIDelis {
  IDeli tomatoSoup = new Soup("Tomato Soup", 300, true);
  IDeli clamChowder = new Soup("Clam Chowder", 500, false);
  IDeli cobbSalad = new Salad("Cobb Salad", 600, "Ranch Dressing", false);
  IDeli greekSalad = new Salad("Greek Salad", 500, "Greek Dressing", true);
  IDeli capreseSandwich = new Sandwich("Caprese Sandwich", 400, "Focaccia", "Mozzarella and Tomatoes");
  IDeli pbAndJ = new Sandwich("PB & J", 300, "White Bread", "Peanut Butter and Jelly");
  
  
}

////////// 4

interface IAncestorTree {}

class Unknown implements IAncestorTree {
  Unknown () {}
}

class TreeNode implements IAncestorTree {
  Person person;
  IAncestorTree mom;
  IAncestorTree dad;
  
  TreeNode (Person person, IAncestorTree mom, IAncestorTree dad) {
    this.person = person;
    this.mom = mom;
    this.dad = dad;
  }
}

class ExamplesIAncestorTrees {
  IAncestorTree unknown = new Unknown();
  IAncestorTree ann = new TreeNode (
      new Person("Ann", 59, "Female", new Address("Boston", "Massachusetts")), 
      this.unknown, this.unknown);
  IAncestorTree rich = new TreeNode (
      new Person ("Richard", 62, "Male", new Address("Portsmouth", "Rhode Island")),
      this.unknown, this.unknown);
  IAncestorTree rachel = new TreeNode (
      new Person("Rachel", 25, "Female", new Address("Boulder", "Colorado")),
      this.ann, this.rich);

}










