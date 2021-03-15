package assignment7;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;


interface IArith {
  
//to return the result of applying the given visitor to this Foo
 <R> R accept (IArithVisitor<R> visitor);

}

class Const implements IArith {
  double num;
  
  Const (double num) {
    this.num = num; 
  }
  
  public <R> R accept (IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }
  
}

class UnaryFormula implements IArith {
  Function<Double,Double> func;
  String name;
  IArith child;
  
  UnaryFormula(Function<Double,Double> func, String name, IArith child) {
    this.func = func;
    this.name = name;
    this.child = child;
  }
  
  public <R> R accept (IArithVisitor<R> visitor) {
    return visitor.visitUnary(this);
  
  }
  
}

class BinaryFormula implements IArith {
  BiFunction <Double,Double,Double> func;
  String name;
  IArith left;
  IArith right;
  
  BinaryFormula (BiFunction<Double,Double,Double> func, String name, IArith left, IArith right) {
    this.func = func;
    this.name = name;
    this.left = left;
    this.right = right; 
  }
  
  public <R> R accept (IArithVisitor<R> visitor) {
    return visitor.visitBinary(this);
    
  }
  
}

//implement a function over and IArith and return R

interface IArithVisitor<R> extends Function<IArith, R>{
  
  R visitConst (Const c);
  
  R visitUnary (UnaryFormula uf);
  
  R visitBinary (BinaryFormula bf); 
  
}

class EvalVisitor implements IArithVisitor<Double> {
  
  public Double apply (IArith arth) {
    return arth.accept(this);
  }
  
  public Double visitConst (Const c) {
    return c.num;  
  }
  
  public Double visitUnary (UnaryFormula uf) {
    return uf.func.apply(this.apply(uf.child)); 
      
  }
  
  public Double visitBinary (BinaryFormula bf) {
    return bf.func.apply(this.apply(bf.left), this.apply(bf.right)); 
  } 
 
}

class PrintVisitor implements IArithVisitor<String> {
  
  public String apply (IArith arth) {
    return arth.accept(this); 
  }
  
  public String visitConst (Const c) {
    return Double.toString(c.num); 
  }

  public String visitUnary(UnaryFormula uf) {
    return "(" + uf.name + " " + this.apply(uf.child) + ")";
  }

  public String visitBinary(BinaryFormula bf) {
    return "(" + bf.name + " " + this.apply(bf.left) + " " + this.apply(bf.right) + ")"; 
  }
 
}  

class DoubleVisitor implements IArithVisitor<IArith> {
  
  public IArith apply (IArith arth) {
    return arth.accept(this); 
  }
  
  public IArith visitConst (Const c) {
    return new Const(c.num * 2);   
  }
  
  public IArith visitUnary (UnaryFormula uf) {
    return new UnaryFormula (uf.func, uf.name, this.apply(uf.child)); 
  }
  
  public IArith visitBinary (BinaryFormula uf) {
    return new BinaryFormula (uf.func, uf.name, this.apply(uf.left), this.apply(uf.right));
  
  }

}

class NoNegativeResults implements IArithVisitor<Boolean> {
  
  public Boolean apply (IArith arth) {
    return arth.accept(this);
  }
  
  public Boolean visitConst (Const c) {
    return c.num > 0; 
  }
  
  public Boolean visitUnary (UnaryFormula uf) {
    if (uf.accept(new EvalVisitor()) > 0) {
      return this.apply(uf.child);  
    }
    return false; 
      
  }
  
  public Boolean visitBinary (BinaryFormula bf) {
    if (bf.accept(new EvalVisitor()) > 0) {
      return this.apply(bf.left) &&
             this.apply(bf.right);  
    }
    return false; 
  } 
  
}

class ExamplesFormulas {
  
  IArith u1 = new UnaryFormula (new Neg(), "neg", new Const(1)); 
  IArith u2 = new UnaryFormula (new Sqr(), "sqr", new Const(16)); 
  IArith u3 = new UnaryFormula (new Neg(), "neg", this.u2); 
  IArith b1 = new BinaryFormula (new Plus(), "plus", new Const(4), new Const(6));
  IArith b2 = new BinaryFormula (new Minus(), "min", this.u2, this.b1); 
  IArith b3 = new BinaryFormula (new Plus(), "min", this.u1, this.b1); 


  boolean testEvalVisitor (Tester t) {
    return
        t.checkExpect (u1.accept(new EvalVisitor()), -1.0) &&
        t.checkExpect (u2.accept(new EvalVisitor()), 4.0) &&
        t.checkExpect (u3.accept(new EvalVisitor()), -4.0) &&
        t.checkExpect (b1.accept(new EvalVisitor()), 10.0) &&
        t.checkExpect (b2.accept(new EvalVisitor()), -6.0); 
  }
  
  boolean testPrintVisitor (Tester t) {
    return
        t.checkExpect (new Const(1).accept(new PrintVisitor()), "1.0") &&
        t.checkExpect (u1.accept(new PrintVisitor()), "(neg 1.0)") &&
        t.checkExpect (u3.accept(new PrintVisitor()), "(neg (sqr 16.0))") &&
        t.checkExpect (b2.accept(new PrintVisitor()), "(min (sqr 16.0) (plus 4.0 6.0))"); 
  }
  
  boolean testDoubleVisitor (Tester t) {
    return
        t.checkExpect(new Const(1).accept(new DoubleVisitor()), new Const(2)) &&
        t.checkExpect(u1.accept(new DoubleVisitor()),
            new UnaryFormula (new Neg(), "neg", new Const(2))) &&
        t.checkExpect(b2.accept(new DoubleVisitor()),
            new BinaryFormula (new Minus(), "min", 
                new UnaryFormula (new Sqr(), "sqr", new Const(32)),
                new BinaryFormula (new Plus(), "plus", new Const(8), new Const(12))));
  }
  
  boolean testNoNegativeResults (Tester t) {
    return
        t.checkExpect (u1.accept(new NoNegativeResults()), false) &&
        t.checkExpect (u2.accept(new NoNegativeResults()), true) &&
        t.checkExpect (u3.accept(new NoNegativeResults()), false) &&
        t.checkExpect (b1.accept(new NoNegativeResults()), true) &&
        t.checkExpect (b3.accept(new NoNegativeResults()), false); 
  }
  
  
}
  
  
  








