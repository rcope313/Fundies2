package lecture19;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;

interface IList<T> {

  T find (Predicate<T> whichOne);
  
  //In IList<T>
  //EFFECT: Finds and modifies the person in this list matching the
  //given predicate, by using the given operation
  Void find(Predicate<T> whichOne, Function<T, Void> whatToDo);
  
}

class MtList<T> implements IList<T> {

  MtList() {
  }

  public T find(Predicate<T> whichOne) {
    return null;
  }
  
  public Void find(Predicate<T> whichOne, Function<T, Void> whatToDo) { return null; }


}
  

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public T find(Predicate<T> whichOne) {
    if (whichOne.test(this.first)) {
      return this.first;
    }
    else {
      return this.rest.find(whichOne);
    }
  }
  
  public Void find(Predicate<T> whichOne, Function<T, Void> whatToDo) {
    if (whichOne.test(this.first)) {
      whatToDo.apply(this.first);
    }
    else {
      this.rest.find(whichOne, whatToDo);
    }
    return null;
  }

}

class ChangePhone9021 implements Function<Person, Void> {
  
  public Void apply (Person p) {
    p.phone = 9021;
    return null;

        
  }  
}

class FindPhoneNum9021 implements Predicate<Person> {

  public boolean test(Person t) {
    return t.phone == 9021;
  }
}

class FindPhoneNum3456 implements Predicate<Person> {

  public boolean test(Person t) {
    return t.phone == 3456;
  }
}

class ExamplePhoneLists2 {
 
  IList<Person> friends, family, work;
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

    this.friends = new ConsList<Person>(anne, new ConsList<Person>(clyde, new ConsList<Person>(gail,
        new ConsList<Person>(frank, new ConsList<Person>(jenny, new MtList<Person>())))));
    this.family = new ConsList<Person>(anne,
        new ConsList<Person>(dana, new ConsList<Person>(frank, new MtList<Person>())));
    this.work = new ConsList<Person>(bob,
        new ConsList<Person>(clyde, new ConsList<Person>(dana, new ConsList<Person>(eric,
            new ConsList<Person>(henry, new ConsList<Person>(irene, new MtList<Person>()))))));
  }

  void testFindPhoneNumber(Tester t) {
    this.initData();
    t.checkExpect(this.friends.find(new FindPhoneNum9021()), null);
    t.checkExpect(this.work.find(new FindPhoneNum3456()), bob);

  }
  
  void testChangePhone (Tester t) {
    this.initData();
    t.checkExpect(this.work.find(new FindPhoneNum3456()), bob);
    t.checkExpect(this.bob.phone, 3456);
    this.work.find(new FindPhoneNum3456(), new ChangePhone9021()); 
    t.checkExpect(this.work.find(new FindPhoneNum9021()), bob);
    t.checkExpect(this.bob.phone, 9021);
                 
  }
  
  

}