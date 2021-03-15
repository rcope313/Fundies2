package lecture4;

import tester.Tester;

// to represent a geometric shape
public interface IShape {
  // to compute area of this shape
  double area ();
//to compute the distance from this shape to the origin
  double distanceToOrigin();
//to increase the size of this shape by the given increment
  IShape grow (int inc);
//is the area of this shape bigger than
//the area of the given shape?
  boolean isBiggerThan(IShape that); 
 

}

//To represent a 2-d point by Cartesian coordinates
class CartPt {
  int x;
  int y;
  
   CartPt(int x, int y) {
     this.x = x;
     this.y = y;
   }
  
   double distanceToOrigin() {
     return Math.sqrt(this.x * this.x + this.y * this.y);
    }
}

// to represent a circle
class Circle implements IShape {
  CartPt center;
  int radius;
  String color;
  
  Circle (CartPt center, int radius, String color) {
    this.center = center;
    this.radius = radius;
    this.color = color;
}
  
  /* TEMPLATE
   * 
   * FIELDS
   * this.x - int
   * this.y - int
   * this.radus - int
   * this.color - String
   * 
   * METHODS
   * this.area() - double
   * 
   * METHODS ON FIELDS
   * this.center.distanceToOrigin - double
   */
  
  // to compute the area of this shape
  public double area () {
    return Math.PI * this.radius * this.radius;
  }
  // to compute the distance of this shape to the origin
  public double distanceToOrigin () {
    return this.center.distanceToOrigin() - this.radius;
  }
  
  // to increase the size of this shape by the given increment
  public IShape grow (int inc) {
    return new Circle (this.center, (this.radius * inc), this.color); 
  }
   
  // is the area of this shape bigger than
  //the area of the given shape?
  public boolean isBiggerThan (IShape that) {
    return this.area() > that.area();
    
  }
  
  
}

// to represent a square
class Square implements IShape {
  CartPt topLeft;
  int size;
  String color;
  
  Square (CartPt topLeft, int size, String color) {
    this.topLeft = topLeft;
    this.size = size;
    this.color = color;
  }
  
  /* TEMPLATE
   * 
   * FIELDS
   * this.x - int
   * this.y - int
   * this.size - int
   * this.color - String
   * 
   * METHODS
   * this.area() - double
   */
  
  // to compute the area of this shape
  public double area () {
    return this.size * this.size;
  }
  
 // to compute the distance of this shape to the origin
 public double distanceToOrigin () {
   return this.topLeft.distanceToOrigin();
 }
 
 // to increase the size of this shape by the given increment
 public IShape grow (int inc) {
   return new Square (this.topLeft, this.size * 2, this.color); 
 }
  
 // is the area of this shape bigger than
 //the area of the given shape?
 public boolean isBiggerThan (IShape that) {
   return this.area() > that.area();
   
 }
  
}

class ExamplesShapes {
  ExamplesShapes () {}
  
  IShape c1 = new Circle (new CartPt (50,50), 10, "red");
  IShape s1 = new Square (new CartPt (50,50), 30, "red");
  
  // to test the method area that implements IShape
  boolean testIShapeArea (Tester t) {
    return 
        t.checkInexact(this.c1.area(), 314.15, 0.01) &&
        t.checkInexact(this.s1.area(), 900.0, 0.01); 
  }
  
  // to test the method distanceToOrigin that implements IShape
  boolean testIShapeDistanceToOrigin (Tester t) {
    return
        t.checkInexact(this.c1.distanceToOrigin(), 60.71, 0.01) &&
        t.checkInexact(this.s1.distanceToOrigin(), 70.71, 0.01);
  }
  
  //to test the method grow that implements IShape
  boolean testIShapegrow (Tester t) {
    return
        t.checkExpect(this.c1.grow(2), new Circle (new CartPt (50,50), 20, "red")) &&
        t.checkExpect(this.s1.grow(2), new Square (new CartPt (50, 50), 60, "red"));          
  }
  
  //to test the method isBiggerThan that implements IShape
  boolean testIShapeIsBiggerThan (Tester t) {
    return
        t.checkExpect (this.c1.isBiggerThan(s1), true) &&
        t.checkExpect (this.s1.isBiggerThan(c1), true);
    
   }
}


  




