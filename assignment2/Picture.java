package assignment2;
import tester.Tester;

public interface Picture {

  // computes the overall width of this picture
  int getWidth (); 
  // computes the number of single shapes involved in producing the final image
  int countShapes (); 
  // computes how deeply operations are nested in the construction of this picture
  int comboDepth (); 
  // leaves the entire image unchanged, except Beside combos
  // where two sub-images flipped (all names can remain untouched)
  // This mirroring should happen the entire way down the image
  Picture mirror (); 
  // takes an integer depth, and produces a String representing the contents of this picture
  // where the recipe for the picture has been expanded only depth times
  String pictureRecipe (int depth);
  
}

class Shape implements Picture {
  String kind;
  int size;
  
  Shape (String kind, int size) {
    this.kind = kind;
    this.size = size;
   
  }
  
  /* Template:
   * 
   * Fields:
   * kind -- String
   * size -- int
   * 
   * Methods:
   * this.getWidth -- int
   * this.countShapes -- int
   * this.comboDepth -- int
   * this.mirror -- Picture 
   * this.pictureRecipe -- String
   * 
   * 
   */
  
  public int getWidth() {
    return this.size;
  }
  
  public int countShapes () {
    return 1;
  }
  
  public int comboDepth () {
    return 0; 
  }
  
  public Picture mirror () {
    return this; 
  }
 
  public String pictureRecipe (int depth) {
    return this.kind;
  }
  
}

class Combo implements Picture {
  String name;
  Operation op;
  
  Combo (String name, Operation op) {
    this.name = name;
    this.op = op;
    
  } 
  
  /* Template:
   * 
   * Fields:
   * name -- String
   * Op -- operation
   * 
   * Methods:
   * this.getWidth -- int
   * this.countShapes -- int
   * this.comboDepth -- int
   * this.mirror -- Picture
   * this.pictureRecipe -- String
   * 
   * Methods on Fields:
   * this.op.getWidthHelper -- int
   * this.op.getShapesHelper -- int
   * this.op.comboDepthHelper -- int
   * this.op.mirrorHelper -- Picture
   * this.op.pictureRecipeHelper -- String
   */
  
  public int getWidth () {
    return this.op.getWidthHelper(); 
  }
  
  public int countShapes () {
    return this.op.countShapesHelper(); 
  }
  
  public int comboDepth () {
    return this.op.comboDepthHelper(); 
  }
  
  public Picture mirror () {
    return this.op.mirrorHelper(this); 
  }
  
  public String pictureRecipe (int depth) {
    if (depth != 0) {
      return  this.op.pictureRecipeHelper (depth) ; 
      }
    else { 
      return this.name;
      }
  }


}  

interface Operation { 

  //computes the overall width of this picture by operation 
  int getWidthHelper ();
  // computes the number of single shapes involved in producing the final image by operation
  int countShapesHelper ();
  //computes how deeply operations are nested in the construction of this picture by operation
  int comboDepthHelper (); 
  //leaves the entire image unchanged, except Beside combos
  // where two sub-images flipped (all names can remain untouched)
  // This mirroring should happen the entire way down the image by operation
  Picture mirrorHelper (Combo that); 
  //takes an integer depth, and produces a String representing the contents of this picture
  // where the recipe for the picture has been expanded only depth times
  String pictureRecipeHelper (int depth);
}

class Scale implements Operation {
  Picture picture;
  
  Scale (Picture picture) {
    this.picture = picture;
    
  }  
  
  /* Template:
   * 
   * Fields:
   * picture -- Picture
   * 
   * Methods:
   * this.getWidthHelper -- int
   * this.countShapesHelper -- int
   * this.comboDepthHelper -- int
   * this.mirrorHelper -- Picture
   * this.pictureRecipeHelper -- String
   * 
   * Methods on Fields:
   * this.picture.getWidth -- int
   * this.picture.countShapes -- int 
   * this.picture.comboDepth -- int 
   * this.picture.mirror -- Picture 
   * this.picture.pictureRecipe -- String
   * 
   */
  
  public int getWidthHelper() {
    return this.picture.getWidth() * 2;
  }
  
  public int countShapesHelper () {
    return this.picture.countShapes();
  }
  
  public int comboDepthHelper () {
    return 1 + this.picture.comboDepth(); 
  } 
  
  public Picture mirrorHelper (Combo that) {
    return that; 
  }
  
  public String pictureRecipeHelper (int depth) {
    if (depth != 0) {
      return 
          "scale(" 
          + this.picture.pictureRecipe(depth - 1) 
          + ")"; 
      }
    else {
      return this.picture.pictureRecipe(depth);
    }
  }
}


class Beside implements Operation {
  Picture picture1;
  Picture picture2;
  
  Beside (Picture picture1, Picture picture2) {
    this.picture1 = picture1;
    this.picture2 = picture2;
    
  } 
  
  /* Template:
   * 
   * Fields:
   * picture1 -- Picture
   * picture2 -- Picture
   * 
   * Methods:
   * this.getWidthHelper -- int
   * this.countShapesHelper -- int
   * this.comboDepthHelper -- int
   * this.mirrorHelper -- Picture
   * this.pictureRecipeHelper -- String
   * 
   * Methods on Fields:
   * this.picture1.getWidth -- int
   * this.picture2.getWidth -- int
   * this.picture1.countShapes -- int
   * this.picture2.countShapes -- int
   * this.picture1.comboDepth -- int
   * this.picture2.comboDepth -- int
   * this.picture1.mirror - Picture
   * this.picture2.mirror - Picture
   * this.picture1.pictureRecipe -- String
   * this.picture2.pictureRecipe -- String
   * 
   */
  
  public int getWidthHelper () {
    return this.picture1.getWidth() + this.picture2.getWidth();
  }
  
  public int countShapesHelper () {
    return this.picture1.countShapes() + this.picture2.countShapes(); 
  }
  
  public int comboDepthHelper () {
    return 1 + Math.max(this.picture1.comboDepth(), this.picture2.comboDepth()); 
  }
  
  public Picture mirrorHelper (Combo that) {
    return new Combo (that.name, new Beside (this.picture2, this.picture1)) ; 
    
  }
  
  public String pictureRecipeHelper (int depth) {
    if (depth != 0) {
      return 
          "beside(" 
          + this.picture1.pictureRecipe(depth - 1) 
          +", "
          + this.picture2.pictureRecipe(depth - 1) 
          + ")" ; 
      }  
      else {return 
            this.picture1.pictureRecipe(depth)
          + this.picture2.pictureRecipe(depth); 
      }
  }
} 


class Overlay implements Operation {
  Picture topPicture;
  Picture bottomPicture;
  
  Overlay (Picture topPicture, Picture bottomPicture) {
    this.topPicture = topPicture;
    this.bottomPicture = bottomPicture;
    
  }
  
  /* Template:
   * 
   * Fields:
   * topPicture -- Picture
   * bottomPicture -- Picture
   * 
   * Methods:
   * this.getWidthHelper -- int
   * this.countShapesHelper -- int
   * this.comboDepthHelper -- int
   * this.mirrorHelper -- Picture
   * this.pictureDepthHelper -- String
   * 
   * Methods on Fields:
   * this.topPicture.getWidth -- int
   * this.bottomPicture.getWidth -- int
   * this.topPicture.countShapes -- int
   * this.bottomPicture.countShapes -- int
   * this.topPicture.comboDepth -- int
   * this.bottomPicture.comboDepth -- int
   * this.topPicture.mirror - Picture
   * this.bottomPicture.mirror - Picture
   * this.topPicture.pictureDepth -- String
   * this.bottomPicture.pictureDepth -- String
   * 
   * Fields of Parameters:
   * that.name
   * that.op
   * 
   * Methods on Parameters:
   * that.op.getWidthHelper -- int
   * that.op.countShapesHelper -- int
   * that.op.comboDepthHelper -- int
   * that.op.mirrorHelper -- Picture
   *
   */
  
  
  public int getWidthHelper() {
    return this.bottomPicture.getWidth() ; 
  }
  
  public int countShapesHelper () {
    return this.bottomPicture.countShapes() + this.topPicture.countShapes(); 
  }
  
  public int comboDepthHelper () {
    return 1 + Math.max(this.topPicture.comboDepth(), this.bottomPicture.comboDepth());
  }
  
  public Picture mirrorHelper (Combo that) {
    return that; 
  }
  
  public String pictureRecipeHelper (int depth) {
   if (depth != 0) { 
     return 
        "overlay(" 
        + this.topPicture.pictureRecipe(depth - 1) 
        + ", "
        + this.bottomPicture.pictureRecipe(depth - 1) 
        + ")" ;
     }
   else {return 
       this.topPicture.pictureRecipe(depth)
     + this.bottomPicture.pictureRecipe(depth);
   }
  }

}

class ExamplesPictures {
  ExamplesPictures () {}
  
  Picture circle = new Shape ("circle", 20);
  Picture square = new Shape ("square", 30);
  
  Picture bigSquare = new Combo ("big square", new Scale (this.square)); 
  Picture bigCircle = new Combo ("big circle", new Scale (this.circle));
  
  Picture squareOnCircle = new Combo ("square on circle", new Overlay (this.square, this.bigCircle));
  Picture circleOnSquare = new Combo ("circle on square", new Overlay (this.circle, this.bigSquare));

  Picture circleSquare = new Combo ("for mirror test", 
      new Beside (this.square, this.circle));
  Picture squareCircle = new Combo ("for mirror test", 
      new Beside (this.circle, this.square)); 
  
  Picture doubledSquareOnCircle = new Combo ("for mirror test", 
      new Beside (this.squareOnCircle, this.squareOnCircle));
  Picture doubledCircleOnSquare = new Combo ("for mirror test", 
      new Beside (this.circleOnSquare, this.circleOnSquare));
 
  boolean testGetWidth(Tester t) {
    return
        t.checkExpect(this.circle.getWidth(), 20) &&
        t.checkExpect(this.bigCircle.getWidth(), 40) &&
        t.checkExpect(this.squareOnCircle.getWidth(), 40) &&
        t.checkExpect(this.doubledSquareOnCircle.getWidth(), 80);       
  }
  
  boolean testCountShapes(Tester t) {
    return
        t.checkExpect(this.circle.countShapes(), 1) &&
        t.checkExpect(this.bigCircle.countShapes(), 1) &&
        t.checkExpect(this.squareOnCircle.countShapes(), 2) &&
        t.checkExpect(this.doubledSquareOnCircle.countShapes(), 4);       
  }
  
  boolean testComboDepth(Tester t) {
    return
        t.checkExpect(this.circle.comboDepth(), 0) &&
        t.checkExpect(this.bigCircle.comboDepth(), 1) &&
        t.checkExpect(this.squareOnCircle.comboDepth(), 2) &&
        t.checkExpect(this.doubledSquareOnCircle.comboDepth(), 3);       
  }
  
  boolean testMirror(Tester t) {
    return
        t.checkExpect(this.circle.mirror(), this.circle) &&
        t.checkExpect(this.bigCircle.mirror(), this.bigCircle) &&
        t.checkExpect(this.squareOnCircle.mirror(), this.squareOnCircle) &&
        t.checkExpect(this.doubledSquareOnCircle.mirror(), this.doubledSquareOnCircle) &&
        t.checkExpect(this.squareCircle.mirror(), this.circleSquare); 
     
  }
  
  boolean testPictureRecipe(Tester t) {
    return
        t.checkExpect(this.circle.pictureRecipe(0), "circle") &&
        t.checkExpect(this.bigCircle.pictureRecipe(1), "scale(circle)") &&
        t.checkExpect(this.squareOnCircle.pictureRecipe(2), "overlay(square, scale(circle))") &&
        t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(4), 
            "beside(overlay(square, scale(circle)), overlay(square, scale(circle)))") &&
        t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(2), 
            "beside(overlay(square, big circle), overlay(square, big circle))");
  }
}