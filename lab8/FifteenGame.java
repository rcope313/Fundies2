package lab8;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

class FifteenGame extends World {

ArrayList<ArrayList<Tile>> tiles;

  FifteenGame () {
  this.tiles = new TileUtils().generateGridOfTiles(); 
  }

  FifteenGame (ArrayList<ArrayList<Tile>> tiles) {
  this.tiles = tiles;  
  }
  

  public WorldScene makeScene() {
    
    WorldScene s = new WorldScene(480, 480);
    WorldImage grid = new TileUtils().drawGrid(this, this.background()); 
    
    s.placeImageXY(grid, 0, 0); 
    
    return s; 
    
  }

  public WorldImage background() {
    return new RectangleImage(480,480, OutlineMode.SOLID, Color.white).movePinhole(-240,-240);  
       
  }

  public void onKeyEvent(String ke) {
    
    int blackKeyRow = this.findBlackKey().get(0);
    int blackKeyCol = this.findBlackKey().get(1); 
    
    if (ke.equals("up") && blackKeyRow != 0) {
      new ArrayUtils().swap(this.tiles, blackKeyRow,blackKeyCol, (blackKeyRow-1),blackKeyCol); 
    }
    else if (ke.equals("down") && blackKeyRow !=3) {
      new ArrayUtils().swap(this.tiles, blackKeyRow,blackKeyCol, (blackKeyRow+1),blackKeyCol); 
    }
    else if (ke.equals("left") && blackKeyCol != 0) {
      new ArrayUtils().swap(this.tiles, blackKeyRow,blackKeyCol, blackKeyRow,(blackKeyCol-1));  
    }
    else if (ke.equals("right") && blackKeyCol != 3) {
      new ArrayUtils().swap(this.tiles, blackKeyRow,blackKeyCol, blackKeyRow,(blackKeyCol+1));  
    }
    else {
      return ; 
    }
      
  }
  
  ArrayList<Integer> findBlackKey () {
    
    ArrayList<Integer> result = new ArrayList<Integer>(); 
        
    for (int idxGrid = 0;
        idxGrid < 4; 
        idxGrid = idxGrid + 1) {
      
      for (int idxCol = 0;
          idxCol < 4;
          idxCol = idxCol + 1) {

        
        if (this.tiles.get(idxGrid).get(idxCol).value == 0) { 
        
          result.add(idxGrid); 
          result.add(idxCol);  
          
          return result; 
        
        }
      }
    }

    throw new RuntimeException ("zero tile not in this grid"); 
           
  }


}

class Tile {
  
  int value;
  
  Tile (int value) {
    this.value = value; 
    
  }
  
  WorldImage tileImage() {
    if (this.value == 0) {
      return new RectangleImage(120, 120, OutlineMode.SOLID, Color.black).movePinhole(-60, -60);
    }
    return new OverlayImage(
        new TextImage(Integer.toString(this.value), 40, Color.black), 
        new RectangleImage(120, 120, OutlineMode.OUTLINE, Color.black))
        .movePinhole(-60, -60); 
  }

  WorldImage drawAt(int col, int row, WorldImage background) {
    return new OverlayImage
        (this.tileImage().movePinhole(0 - (row*120), 0 - (col*120)), 
        background); 
  }
  
}

class TileUtils { 
  
  WorldImage drawGrid(FifteenGame game, WorldImage background) {
    
    WorldImage result = background; 
      
    for (int idxGrid = 0; 
        idxGrid < 4;
        idxGrid = idxGrid + 1) {
      
      for (int idxCol = 0; 
          idxCol < 4;
          idxCol = idxCol + 1) {
      
        result = game.tiles.get(idxGrid).get(idxCol)
            .drawAt(idxGrid, idxCol, result);  
        
      }
    } 
    
    return result; 
    
  }
  
  ArrayList<ArrayList<Tile>> generateGridOfTiles () {
  
   ArrayList<Integer> comparator = 
       new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
   
   ArrayList<Tile> resultRow = new ArrayList<Tile>(); 
   ArrayList<ArrayList<Tile>> resultGrid = new ArrayList<ArrayList<Tile>>(); 
   
   for (int idxg = 0; 
       idxg < 4;
       idxg = idxg + 1) {
   
     for (int idx = 0;                                   
         idx < 4;                              
         idx = idx + 1) {
       
       // i can't add one because it's out of bounds for the comparator,
       // but i can't end on 0 because that doesn't fufill nextInt function
       int randomInt = comparator.get
           (new Random().nextInt(comparator.size())); 
       
         resultRow.add(new Tile(randomInt)); 
         new ArrayUtils().removeExcept(comparator, randomInt);
     }
     
     resultGrid.add(resultRow);
     resultRow = new ArrayList<Tile>(); 
      
   }
   
   return resultGrid; 
    
 }

}

class ArrayUtils {
  
  <T> void removeExcept(ArrayList<T> arr, T t) {
     
     ArrayList<T> duplicate = arr; 
     
     for (int idx = 0;                                   
         idx < arr.size();                              
         idx = idx + 1) {
       
       if (t == (arr.get(idx))) {
         duplicate.remove(idx); 
         
       } 
     }
     
     duplicate = arr; 
     
  }
  
  boolean isInList (ArrayList<Integer> arr, int x) { 
    
    for (int idx = 0;                                   
        idx < arr.size();                              
        idx = idx + 1) {
      
      if (arr.get(idx) == x) {
        return true; 
      }
    }
    return false; 
  }

  <T> void swap(ArrayList<ArrayList<T>> arr, int idxRow1, 
                int idxCol1, int idxRow2, int idxCol2) { 
    
    T oldValueAtIdxCol1 = arr.get(idxRow1).get(idxCol1); 
    T oldValueAtIdxCol2 = arr.get(idxRow2).get(idxCol2); 

    arr.get(idxRow2).set(idxCol2, oldValueAtIdxCol1);
    arr.get(idxRow1).set(idxCol1, oldValueAtIdxCol2);
    

  }
  
}

class ExampleFifteenGame {
  
  Tile tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7, 
  tile8, tile9, tile10, tile11, tile12, tile13, tile14, tile15; 
  ArrayList<Tile> row0, row1, row2, row3, row4, row5, row6, row7;  
  ArrayList<ArrayList<Tile>> grid1, grid2; 
  FifteenGame game; 
  
  void voidInitData() {
    
    tile0 = new Tile(0);
    tile1 = new Tile(1);
    tile2 = new Tile(2);
    tile3 = new Tile(3);
    tile4 = new Tile(4);
    tile5 = new Tile(5);
    tile6 = new Tile(6);
    tile7 = new Tile(7);
    tile8 = new Tile(8);
    tile9 = new Tile(9);
    tile10 = new Tile(10);
    tile11 = new Tile(11);
    tile12 = new Tile(12);
    tile13 = new Tile(13);
    tile14 = new Tile(14);
    tile15 = new Tile(15);
    
    row0 = new ArrayList<Tile>(Arrays.asList(tile15, tile1, tile2, tile3)); 
    row1 = new ArrayList<Tile>(Arrays.asList(tile4, tile5, tile6, tile7)); 
    row2 = new ArrayList<Tile>(Arrays.asList(tile8, tile9, tile10, tile11)); 
    row3 = new ArrayList<Tile>(Arrays.asList(tile12, tile13, tile14, tile0)); 
    
    row4 = new ArrayList<Tile>(Arrays.asList(tile0, tile1, tile2, tile3)); 
    row5 = new ArrayList<Tile>(Arrays.asList(tile4, tile5, tile6, tile7)); 
    row6 = new ArrayList<Tile>(Arrays.asList(tile8, tile9, tile10, tile11)); 
    row7 = new ArrayList<Tile>(Arrays.asList(tile12, tile13, tile14, tile15));
    
    grid1 = new ArrayList<ArrayList<Tile>>(Arrays.asList(row0, row1, row2, row3)); 
    grid2 = new ArrayList<ArrayList<Tile>>(Arrays.asList(row4, row5, row6, row7)); 
    
    game = new FifteenGame(grid1); 
    

  }
   
  void testGame(Tester t) {
    
    FifteenGame g = new FifteenGame();
    g.bigBang(480, 480); 
      
  }
  
  void testGetZero(Tester t) {
    this.voidInitData();
    t.checkExpect(game.tiles.get(0).get(0).value, 15); 
    t.checkExpect(game.findBlackKey(), new ArrayList<Integer>(Arrays.asList(3,3))); 
  }
  
  void testSwap (Tester t) {
    this.voidInitData();
    new ArrayUtils().swap(grid1, 0,0, 3,3); 
    t.checkExpect(grid1, grid2); 
  }

}
  
  
  
  
    
     
   
  
  
  
    

  


