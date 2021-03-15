package lecture15;

import tester.Tester;

interface IList<T> {
  
  IList<T> filter (IPred<T> pred); 
  <U> IList<U> map (IFunc<T,U> f); 
  <U> U foldr (IFunc2<T,U,U> f, U base); 
  
}

class MtList<T> implements IList<T> {
  
  public IList<T> filter (IPred<T> pred) {
    return this; 
  }
  
  public <U> IList<U> map (IFunc<T,U> f) {
    return new MtList<U>(); 
  }
  
  public <U> U foldr (IFunc2<T,U,U> f, U base) {
    return base; 
  }
  
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest; 
  
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
    
  }
  
  public IList<T> filter (IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred)); 
      }
    else {
      return this.rest.filter(pred); 
    } 
  }
  
  public <U> IList<U> map (IFunc<T,U> f) {
    return new ConsList<U>(f.apply(this.first), this.rest.map(f)); 
  }
  
  public <U> U foldr (IFunc2<T,U,U> f, U base) {
    return f.apply(this.first, this.rest.foldr(f, base)); 
           
  }
  
}

//Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc2<A1, A2, R> {
  
  R apply(A1 arg1, A2 arg2);
  
}

class SumBooks implements IFunc2<Book, Integer, Integer> { 
  
  public Integer apply (Book b1, Integer sum) {
    return b1.price + sum; 
    
  }
}

//Interface for a one-argument function object with signature [A -> R]
interface IFunc<A,R> {
  
  R apply (A arg); 
  
}

class RunnerName implements IFunc<Runner, String> {
  public String apply (Runner r) {
    return r.name;
  }
  
}


interface IPred<T> {
  boolean apply (T t); 
}

class LetterA implements IPred<String> {
  
  public boolean apply (String s) {
    return s == "a"; 
  }
  
}

class Runner {
  String name;
  int time;
  
  Runner (String name, int time) {
    this.name = name;
    this.time = time; 
  }
  
}

class Book {
  String title; 
  int price;
  
  Book (String title, int price) {
    this.title = title;
    this.price = price;
    
  }
}

  

class ExamplesLists {
  
  IList<String> runnernames = new ConsList<String>("Rachel", 
                      new ConsList<String>("Kyle", 
                      new MtList<String>()));
  IList<Runner> runners = new ConsList<Runner>(new Runner ("Rachel", 50),
                          new ConsList<Runner>(new Runner ("Kyle", 45),
                          new MtList<Runner>()));
  IList<Book> books = new ConsList<Book>(new Book ("Twilight", 10),
                      new ConsList<Book>(new Book ("New Moon", 45),
                      new MtList<Book>()));
  
  
  
  boolean testFindMethods(Tester t) {
    return 
        t.checkExpect(this.runners.map(new RunnerName()), this.runnernames) &&
        t.checkExpect(this.books.foldr(new SumBooks(), 0), 55);  
  }
  
}