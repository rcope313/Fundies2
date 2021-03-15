package lecture16;

interface ILoFoo {
 
  
}

class MTLoFoo implements ILoFoo {
  
  
}

class ConsLoFoo implements ILoFoo {
  IFoo first;
  ILoFoo rest;
  
  ConsLoFoo (IFoo first, ILoFoo rest) {
    this.first = first;
    this.rest = rest;
  }
  
}


public interface IFoo {
  
  // to return the result of applying the given visitor to this Foo
  <R> R accept (IFooVisitor<R> visitor);
  
 
}

class X implements IFoo {
  
  // to return the result of applying the given visitor to this x
  public <R> R accept (IFooVisitor<R> visitor) {
    return visitor.visitX(this);
    }
}

class Y implements IFoo {
  
  // to return the result of applying the given visitor to this x
  public <R> R accept (IFooVisitor<R> visitor) {
    return visitor.visitY(this);
  }
  
}

class Z implements IFoo {
  
//to return the result of applying the given visitor to this x
 public <R> R accept (IFooVisitor<R> visitor) {
   return visitor.visitZ(this);
 }  
}

//Interface for a one-argument function object with signature [A -> R]
interface IFunc<A,R> {
  R apply (A arg); 
}

// implement a function over Foo objects, return a value of R
interface IFooVisitor<R> {
  R visitX (X x);
  R visitY (Y y);
  R visitZ (Z z); 
}

class FooToInt implements IFooVisitor<Integer>, IFunc<IFoo, Integer> {
  
  public Integer visitX (X x) {
    return 1; 
  }
  
  public Integer visitY (Y y) {
    return 2;
  }
  
  public Integer visitZ (Z z) {
    return 3; 
  }
 
  /// this function is fucking crazy 
  public Integer apply (IFoo foo) {
    return foo.accept(this); 
  }
  
}
