package assignment4;
import tester.*; 

public class BagelRecipe {
  double flour; 
  double water;
  double yeast;
  double malt;
  double salt;
  
  BagelRecipe (double flour, double water, double yeast, double malt, double salt) {
    this.flour = new Utils().checkEqualRatio(flour, water); 
    this.water = new Utils().checkEqualRatio(water, flour); 
    this.yeast = new Utils().checkEqualRatio(yeast, malt); 
    this.malt = new Utils().checkEqualRatio(malt, yeast); 
    this.salt = new Utils().checkSaltRatio(salt, yeast, flour);  
  }
  
  BagelRecipe (double flour, double yeast) {
    this(flour, flour, yeast, yeast, new Utils().produceSalt(yeast, flour)); 
    
  }
  
  BagelRecipe (double flour, double yeast, double salt) {
    this(new Utils().volumeToWeight(flour, 4.25, 1),
         new Utils().volumeToWeight(flour, 4.25, 1),
         new Utils().volumeToWeight(yeast, 5, 48),
         new Utils().volumeToWeight(yeast, 5, 48),
         new Utils().volumeToWeight(salt, 10, 48)); 

  }

  boolean sameRecipe (BagelRecipe that) {
   return this.flour - that.flour <= .001 
       && this.water - that.water <= .001
       && this.yeast - that.yeast <= .001
       && this.malt - that.malt <= .001
       && this.salt - that.salt <= .001
       && this.flour - that.flour > -.001
       && this.water - that.water > -.001
       && this.yeast - that.yeast > -.001
       && this.malt - that.malt > -.001
       && this.salt - that.salt > -.001;
        
  }
}
 

class ExamplesBagels {
  
  BagelRecipe recipe1 = new BagelRecipe (17.00,17.00,.45,.45,.40);
  BagelRecipe recipe2 = new BagelRecipe (17,.45); 
  BagelRecipe recipe3 = new BagelRecipe (4, 4.32,1.92); 
  BagelRecipe recipe0 = new BagelRecipe (34, 34, .9, .9, .8); 

  
  boolean checkConstructorException (Tester t) {
    return 
        t.checkConstructorException
        (new IllegalArgumentException("Invalid Ratio"), "Bagel Recipe", 17,17,.45,.45,.4); 
  }
  
  boolean testSameRecipe (Tester t) {
    return
        t.checkExpect(recipe1.sameRecipe(recipe2), true) &&
        t.checkExpect(recipe1.sameRecipe(recipe3), true) &&
        t.checkExpect(recipe2.sameRecipe(recipe3), true) &&
        t.checkExpect(recipe1.sameRecipe(recipe0), false); 
        
  }
}

class Utils {
  
  double checkEqualRatio(double x, double y) {
    if (x == y) {
      return x;
    }
    else {
      throw new IllegalArgumentException("Invalid Ratio");
    }
  }
    
  double checkSaltRatio (double salt, double yeast, double flour) { 
    if (salt + yeast - flour / 20 <= .001) {
      return salt;  
    }
    else {
      throw new IllegalArgumentException("Invalid Ratio");
    }
  }
    
  double produceSalt (double yeast, double flour) {
    return flour / 20 - yeast; 
  }
    
  double volumeToWeight (double val, double conv, double tsp) {
    return val * conv / tsp; 
  }
}


