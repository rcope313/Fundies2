package lab3;

import tester.Tester;
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import javalib.worldcanvas.WorldCanvas;

import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Red, Green, Yellow, Blue, Black, White)


public interface IMobile {
  
  int totalWeight (); 
  
  int totalHeight (); 
  
  boolean isBalanced (); 
  
  int leftTorque (); 
  
  int rightTorque (); 
  
  IMobile buildMobile (IMobile that, int length); 
  
  int buildSideLength (IMobile that, int length, int startOfStrut, int endOfStrut); 
  
  int curWidth (); 
  
  WorldImage drawMobile (); 
  
  
  

}

class Simple implements IMobile {
  int length;
  int weight;
  String color; 
  
  Simple (int length, int weight, String color) {
    this.length = length;
    this.weight = weight;
    this.color = color;
  }
  
  /* Template
   * 
   * Fields:
   * length -- int
   * weight -- int
   * color -- String
   * 
   * Methods:
   * totalWeight -- int
   * totalHeight -- int
   * isBalanced -- boolean
   * leftTorque -- int
   * rightTorque -- int
   * buildMobile -- IMobile
   * buildSideLength -- int
   * curWidth -- int
   * drawMobile -- WorldImage
   * 
   * Methods on Parameters:
   * that.totalWeight()
   * that.totalHeigh()
   * that.isBalanded()
   * that.leftTorque()
   * that.rightTorque()
   * that.buildMobile()
   * that.buildLength()
   * that.buildLeftSide()
   * that.buildRightSide()
   * that.buildMobile)
   * that.buildSideLength()
   * that.curWidth
   * that.drawMobile()
   */
  
  public int totalWeight() {
    return this.weight; 
  }
  
  public int totalHeight() {
    return this.weight / 10; 
  }
  
  public boolean isBalanced () {
    return true; 
  }
  
  public int leftTorque () {
    return 0; 
  }
   
  
  public int rightTorque () {
    return 0; 
  }
  
  public IMobile buildMobile (IMobile that, int length) {
    return new Complex (
        length, 
        this.buildSideLength (that, length, 0, length),  
        that.buildSideLength (this, length, 0, length),
        this,
        that);
  }
  
  public int buildSideLength (IMobile that, int length, int startOfStrut, int endOfStrut) {
    if (startOfStrut * this.weight == endOfStrut * that.totalWeight()) {
      return startOfStrut; }
    else if (startOfStrut == length) {
      return 0; }
    else {
      return buildSideLength 
          (that, length, (startOfStrut + 1), (endOfStrut - 1)); 
    }
  }
  
  public int curWidth() {
    if (Math.floorMod(this.weight, 10) == 0) {
      return this.weight / 10;
    }
    else {
      return this.weight / 10 + 1; 

    }
  }
  
  public WorldImage drawMobile() {
    return new AboveImage 
            (new LineImage (new Posn(0,10), Color.black),
             new RectangleImage (this.length, this.length, OutlineMode.SOLID, Color.DARK_GRAY))
            .movePinhole(0, -(this.length + 10) / 2); 

  } 
  
    
}





class Complex implements IMobile {
  int length;
  int leftside;
  int rightside; 
  IMobile left;
  IMobile right; 
  
  Complex (int length, int leftside, int rightside, IMobile left, IMobile right) {
    this.length = length;
    this.leftside = leftside;
    this.rightside = rightside;
    this.left = left;
    this.right = right; 
  }

  /* Template
   * 
   * Fields:
   * length -- int
   * leftside -- int
   * rightside -- int
   * left -- IMobile
   * right -- IMobile
   * 
   * Methods:
   * totalWeight -- int
   * totalHeight -- int
   * isBalanced -- boolean
   * leftTorque -- int
   * rightTorque -- int
   * buildMobile -- IMobile
   * buildSideLength -- int
   * curWidth -- int
   * 
   * Methods on Fields:
   * this.left.totalWeight()
   * this.right.totalWeight()
   * this.left.totalHeight()
   * this.right.totalHeight()
   * this.left.isBalanced()
   * this.right.isBalanded()
   * 
   */
 
  
  public int totalWeight() {
    return this.left.totalWeight() + 
           this.right.totalWeight(); 
  }
  
  public int totalHeight () {  
     return Math.max(this.left.totalHeight(), this.right.totalHeight()); 
        

  }
  
  public boolean isBalanced () {
   if (this.leftTorque() != 
       this.rightTorque()) {
     return false;
   }
   else {
     return this.left.isBalanced() &&
            this.right.isBalanced(); 
   }
 
  }
  
  public int leftTorque() {
    return this.leftside * this.left.totalWeight(); 
  }
  
  public int rightTorque () {
    return this.rightside * this.right.totalWeight(); 
  }
  
  public IMobile buildMobile (IMobile that, int length) {
    return new Complex (
        length, 
        this.buildSideLength (that, length, 0, length), 
        that.buildSideLength (this, length, 0, length),
        this,
        that);
  }
  
  public int buildSideLength (IMobile that, int length, int startOfStrut, int endOfStrut) {
    if (startOfStrut * this.totalWeight() == endOfStrut * that.totalWeight()) {
      return startOfStrut; 
      }
    else if (startOfStrut == length) {
      return 0; }
    else {
      return buildSideLength 
          (that, length, (startOfStrut + 1), (endOfStrut - 1)); 
    }
  }
  
  public int curWidth() {
    return  this.left.curWidth() 
          + this.right.curWidth()
          + this.length; 
     
  }
  
  public WorldImage drawMobile () {
    return new OverlayImage (this.drawOneSide().movePinhole(this.length*10, 0),
                             this.right.drawMobile());  
                              
                            
  }
  
  public WorldImage drawOneSide () {
    return new OverlayImage (this.left.drawMobile(),
                             this.drawLeftStrut()); 
                            
  }
  
  WorldImage drawLeftStrut () {
    return new LineImage (new Posn (this.length * 10, 0), Color.BLACK)
               .movePinhole(this.length*10/-2,0); 

  }
  
 
}
  


class ExamplesMobiles {
  ExamplesMobiles () {} 
  
  IMobile SimpleBlue20 = new Simple (20, 20, "blue");
  IMobile SimpleBlue36 = new Simple (36, 36, "blue");
  IMobile SimpleRed12 = new Simple (12,12,"red");
  IMobile SimpleRed36 = new Simple (36,36,"red");
  IMobile SimpleGreen60 = new Simple (60,60,"green");

  IMobile ComplexString3 = new Complex (8,5,3,SimpleRed36,SimpleGreen60); 
  IMobile ComplexString2 = new Complex (9,8,1,SimpleRed12,ComplexString3);
  IMobile ComplexString1 = new Complex (12,9,3,SimpleBlue36,ComplexString2);
  IMobile ComplexString0 = new Complex (11,8,3, SimpleRed12, SimpleRed36); 
  
  boolean testTotalWeight (Tester t) {
    return
        t.checkExpect(SimpleBlue20.totalWeight(), 20) &&
        t.checkExpect(ComplexString3.totalWeight(), 96) &&
        t.checkExpect(ComplexString1.totalWeight(), 144);
        
  }
  
//  boolean testTotalHeight (Tester t) 
//    return
//        t.checkExpect(SimpleBlue20.totalHeight(), 2) &&
//        t.checkExpect(ComplexString3.totalHeight(), 6) &&
//        t.checkExpect(ComplexString1.totalHeight(), 10);
  
//  }
  
  boolean testIsBalanced (Tester t) {
    return
        t.checkExpect(SimpleBlue20.isBalanced(), true) &&
        t.checkExpect(ComplexString1.isBalanced(), true) &&
        t.checkExpect(ComplexString0.isBalanced(), false); 
  }
  
  boolean testBuildMobile (Tester t) {
    return 
        t.checkExpect(SimpleRed12.buildMobile(SimpleBlue20, 8), 
                        new Complex (8,5,3,SimpleRed12,SimpleBlue20)) &&
        t.checkExpect(SimpleBlue20.buildMobile(ComplexString3, 29),
                         new Complex (29, 24, 5, SimpleBlue20, ComplexString3)); 
            
  }
  
  boolean testcurWidth (Tester t) {
    return
        t.checkExpect(SimpleBlue36.curWidth(), 4) &&
        t.checkExpect(SimpleBlue20.curWidth(), 2) &&
        t.checkExpect(ComplexString0.curWidth(), 17) &&
        t.checkExpect(ComplexString2.curWidth(), 29); 
    
  }
  
  boolean testDrawMobile(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(ComplexString1.drawMobile(), 250, 250))
        && c.show();
  }
}


  
