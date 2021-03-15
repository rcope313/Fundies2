package lab2;

import tester.Tester;

public interface GamePiece {
  
  //returns the value of a game piece
  int getValue (); 
  //combines this game piece with the given game piece to form a merged piece
  GamePiece merge (GamePiece that); 
  //checks whether this game piece was created according to the rules of 2048
  //only equal-valued pieces can merge
  boolean isValid (); 

}

class BaseTile implements GamePiece {
  int value;
  
  BaseTile (int value) {
    this.value = value; 
  }  
  
  /* Template:
   * 
   * Fields:
   * value -- int
   * 
   * Methods:
   * this.getValue -- int
   * this.merge -- GamePiece
   * this.isValid -- GamePiece
   * 
   * Methods on Fields:
   * that.getValue -- int
   * that.merge -- GamePiece
   * that.isValid -- boolean
   * 
   */
  
  public int getValue () {
    return this.value; 
  }
  
  public GamePiece merge (GamePiece that) {
    return new MergeTile (this, that); 
  }
  
  public boolean isValid () {
    return true;
  }
  
}

class MergeTile implements GamePiece {
  GamePiece piece1;
  GamePiece piece2;
  
  MergeTile (GamePiece piece1, GamePiece piece2) {
    this.piece1 = piece1;
    this.piece2 = piece2;
  }
  
  /* Template:
   * 
   * Fields:
   * piece1 - GamePiece
   * piece2 - GamePiece
   * 
   * Methods:
   * this.getValue -- int
   * this.merge -- int
   * this.isValid -- boolean 
   * 
   * Methods on Fields:
   * this.piece1.getValue -- int
   * this.piece2.getValue -- int
   * this.piece1.merge -- GamePiece
   * this.piece2.merge -- GamePiece
   * this.piece1.isValid -- boolean
   * this.piece2.isValid -- boolean
   * 
   * Methods on Paramters -- int
   * that.getValue -- int
   * that.merge -- GamePiece
   * that.isValid -- boolean
   * 
   */
  
  public int getValue () {
    return  this.piece1.getValue() 
          + this.piece2.getValue();
  }
  
  public GamePiece merge (GamePiece that) {
    return new MergeTile (this, that); 
  }
  
  public boolean isValid () {
    if (this.piece1.getValue() == this.piece2.getValue()) {
      return true; 
    }
    else {
      return false;
    }
  }
  
}

class ExamplesGamePieces {
  ExamplesGamePieces() {} 
  
  GamePiece b1 = new BaseTile (2);
  GamePiece m1 = new MergeTile (this.b1, this.b1); // 4
  GamePiece m2 = new MergeTile (this.m1, this.b1); // 6
  GamePiece m3 = new MergeTile (this.m2, this.b1); // 8
  GamePiece m4 = new MergeTile (this.m2, this.m3); // 14
  GamePiece m5 = new MergeTile (this.m3, this.m3); // 16
  
  
  boolean testGetValue (Tester t) {
    return
        t.checkExpect(this.b1.getValue(), 2) &&
        t.checkExpect(this.m1.getValue(), 4) &&
        t.checkExpect(this.m4.getValue(), 14);  
    }
  
  boolean testMerge (Tester t) {
    return
        t.checkExpect(this.m1.merge(this.b1), this.m2) &&
        t.checkExpect(this.m2.merge(this.b1), this.m3) &&
        t.checkExpect(this.m2.merge(this.m3),  this.m4);
  }
  
  boolean testIsValid (Tester t) {
    return
        t.checkExpect(this.b1.isValid(), true) &&
        t.checkExpect(this.m2.isValid(), false) &&
        t.checkExpect(this.m5.isValid(), true);
 
  }
        
    
       
}
  
