package assignment7;
import java.util.function.BiFunction;
import java.util.function.Function;

interface IFormulas {
  
  String print (); 
  
}

class Neg implements Function<Double,Double>, IFormulas {
  
  public Double apply (Double x) { return x * -1; }
  public String print () { return "neg"; }
  
}

class Sqr implements Function <Double,Double>, IFormulas {
  
  public Double apply (Double x) { return Math.sqrt(x); }
  public String print () { return "sqr"; }  

}

class Plus implements BiFunction <Double,Double,Double>, IFormulas {
  
  public Double apply (Double x, Double y) { return x + y; }
  public String print () { return "plus"; }
 
}

class Minus implements BiFunction <Double,Double,Double>, IFormulas {
  
  public Double apply (Double x, Double y) { return x - y; }
  public String print () { return "min"; }
  
}

class Mul implements BiFunction <Double,Double,Double>, IFormulas {
  
  public Double apply (Double x, Double y ) { return x * y; }
  public String print () { return "mul"; }
  
}

class Div implements BiFunction <Double,Double,Double>, IFormulas {
  
  public Double apply (Double x, Double y ) { return x / y; }
  public String print () { return "div"; } 
 
}

