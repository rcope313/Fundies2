package lab6; 

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;


interface IList<T> {
  IList<T> filter(Predicate<T> pred);
  <U> IList<U> map(Function<T,U> converter);
  <U> U fold(BiFunction<T,U,U> converter,U initial);
  <U, V> IList<V> convolve(BiFunction<T,U,V> converter, IList<U> that); 
  T getFirst(); 
  IList<T> getRest(); 
}

class MtList<T> implements IList<T> {
  
  MtList() {}

  @Override
  public IList<T> filter(Predicate<T> pred) {
    // TODO Auto-generated method stub
    return new MtList<T>();
  }

  @Override
  public <U> IList<U> map(Function<T, U> converter) {
    // TODO Auto-generated method stub
    return new MtList<U>();
  }

  @Override
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    // TODO Auto-generated method stub
    return initial;
  }
  
  public <U, V> IList<V> convolve(BiFunction<T,U,V> converter, IList<U> that) {
    return new MtList<V>();  

  }
  
  public T getFirst() {
    return null; 
  }
  
  public IList<T> getRest() {
    return null; 
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public IList<T> filter(Predicate<T> pred) {
    // TODO Auto-generated method stub
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first,this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  @Override
  public <U> IList<U> map(Function<T, U> converter) {
    // TODO Auto-generated method stub
    return new ConsList<U>(converter.apply(this.first),this.rest.map(converter));
  }

  @Override
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    // TODO Auto-generated method stub
    return converter.apply(this.first, this.rest.fold(converter,initial));
  }
  
  public <U, V> IList<V> convolve(BiFunction<T,U,V> converter, IList<U> that) {
    return new ConsList<V> (converter.apply(this.first, that.getFirst()), 
                            this.rest.convolve(converter, that.getRest())); 
  }
  public T getFirst() {
    return this.first; 
  }
  
  public IList<T> getRest () {
    return this.rest; 
  }
}

class ConvolveProduct implements BiFunction<Integer, Integer, Integer> {
  
  public Integer apply (Integer x, Integer y) {
    return x * y; 
  }
}

class ConvolveStringLength implements BiFunction<String, Integer, Boolean> {
  
  public Boolean apply (String string, Integer x) {
    return string.length() == x; 
  }
}


class BeginsWithT implements Predicate<String> {
  
  public boolean test (String s) {
    return "T".equals(s.charAt(0)); 
  }
}

class SumEndingER implements BiFunction<String, Integer, Integer> {
  
  public Integer apply (String s, Integer sum) {
    if (s.endsWith("er")) {
      return 1 + sum;
    }
    else {
      return sum; 
    }
  }
}

class MonthAbbr implements Function<String, String> {
  
  public String apply (String s) {
    return s.substring(0,3); 
  }
}


class ExamplesLists{
  ExamplesLists() {}
  
  IList<String> winter = new ConsList<String> ("December", 
                         new ConsList<String> ("January",
                         new ConsList<String> ("Febuary",
                         new MtList<String>())));
  IList<String> spring = new ConsList<String> ("March", 
                         new ConsList<String> ("April",
                         new ConsList<String> ("May",
                         new MtList<String>())));
  IList<String> summer = new ConsList<String> ("June", 
                         new ConsList<String> ("July",
                         new ConsList<String> ("August",
                         new MtList<String>())));
  IList<Integer> int1 = new ConsList<Integer> (1, 
                        new ConsList<Integer> (2,
                        new ConsList<Integer> (3,
                        new MtList<Integer>())));
  IList<Integer> int2 = new ConsList<Integer> (3, 
                        new ConsList<Integer> (4,
                        new ConsList<Integer> (5,
                        new MtList<Integer>())));
  IList<Integer> int3 = new ConsList<Integer> (3, 
                        new ConsList<Integer> (8,
                        new ConsList<Integer> (15,
                        new MtList<Integer>())));
  IList<String> string1 = new ConsList<String> ("a",
                          new ConsList<String> ("ab",
                          new ConsList<String> ("c",
                          new MtList<String>()))); 
  IList<Boolean> bol1 = new ConsList<Boolean> (true,
                        new ConsList<Boolean> (true,
                        new ConsList<Boolean> (false,
                        new MtList<Boolean>()))); 
      
  
  
  boolean testFindMethods(Tester t) {
    return
      t.checkExpect(winter.filter(new BeginsWithT()), new MtList<String>()) &&
      t.checkExpect(winter.fold(new SumEndingER(), 0), 1) &&
      t.checkExpect(summer.fold(new SumEndingER(), 0), 0) &&
      t.checkExpect(summer.map(new MonthAbbr()), new ConsList<String> ("Jun", 
                                                 new ConsList<String> ("Jul",
                                                 new ConsList<String> ("Aug",
                                                 new MtList<String>())))) &&
      t.checkExpect(int1.convolve(new ConvolveProduct(), int2), int3) &&
      t.checkExpect(string1.convolve(new ConvolveStringLength(), int1), bol1);
  }
}
  
    