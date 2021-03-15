package assignment9;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import tester.*;
import javalib.impworld.*; 
import java.awt.Color;
import javalib.worldimages.*;

class FloodItWorld extends World {
  
  ArrayList<ArrayList<Cell>> board;
  int numberOfRows;
  int numberOfCols;  
  int numberOfColors; 
  int mouseClicks; 
  
  FloodItWorld(int numberOfRows, int numberOfCols, int numberOfColors) {
    
    this.numberOfRows = new Utils().checkRange(numberOfRows, 1, 20,
        "Can only accomodate 2 to 20 rows"); 
    
    this.numberOfCols = new Utils().checkRange(numberOfCols, 1, 20,
        "Can only accomodate 2 to 20 rows");
    
    this.numberOfColors = new Utils().checkRange(numberOfColors, 2, 5, 
        "Can only accomodate 3 to 6 colors"); 
      
    this.board = new Utils().generateBoard
        (numberOfRows, numberOfCols, numberOfColors);  
    
    this.floodCellsInit(); 
  }
  
  FloodItWorld
    (ArrayList<ArrayList<Cell>> board, 
     int numberOfRows, 
     int numberOfCols, 
     int numberOfColors) {
    super();
    this.board = board;
    this.numberOfRows = numberOfRows;
    this.numberOfCols = numberOfCols;
    this.numberOfColors = numberOfColors; 
  }
  
  
  @Override
 public WorldScene makeScene() {
    int widthOfBoard = this.numberOfCols * new Cell().CELL_SIZE; 
    int lengthOfBoard = this.numberOfRows * new Cell().CELL_SIZE; 
    int lengthOfMouseClickCounter =  new Cell().CELL_SIZE * 6; 
    
    WorldScene s = new WorldScene(widthOfBoard, lengthOfBoard + lengthOfMouseClickCounter); 
    WorldImage grid = new Utils().drawGrid(this); 
    WorldImage mouseClickCounter = this.mouseClickCounterImage(); 
    
    s.placeImageXY(grid, 
                   widthOfBoard/2,
                   lengthOfBoard/2);  
    s.placeImageXY(mouseClickCounter, 
                   widthOfBoard/2, 
                  (lengthOfBoard + lengthOfMouseClickCounter - 3 * new Cell().CELL_SIZE));    
    
    return s;
  }
  
 WorldImage background() {
    return new RectangleImage
        (new Cell().CELL_SIZE * numberOfCols, 
         new Cell().CELL_SIZE * numberOfRows,
         OutlineMode.SOLID, Color.white); 

  }
  
 WorldImage mouseClickCounterImage() {
    return new OverlayImage
        (new TextImage (this.scoreboard(), 36, FontStyle.BOLD, Color.black), 
        (new RectangleImage 
            (new Cell().CELL_SIZE * numberOfCols, 
                new Cell().CELL_SIZE * 6,
                OutlineMode.SOLID, Color.white))); 
               
  }
  
 String scoreboard () {
    return Integer.toString(this.mouseClicks) + "/" + Integer.toString(this.maxClicks()); 
  }
  
 int maxClicks () { 
    return (int)(((this.numberOfColors * this.numberOfCols * this.numberOfRows) * .0149) + 5.96); 
  }
 
 
 @Override 
 public WorldScene lastScene(String msg) { 
   
   int widthOfGrid = this.numberOfCols * new Cell().CELL_SIZE; 
   int lengthOfGrid = this.numberOfRows * new Cell().CELL_SIZE; 
   
   WorldScene s = this.makeScene(); 
   s.placeImageXY(new TextImage(msg, 20, Color.black), widthOfGrid/2, lengthOfGrid + new Cell().CELL_SIZE * 5); 
   return s; 
   
  
 }
 
 boolean allCellsFlooded () {
    
    for (ArrayList<Cell> row : this.board) {
      for (Cell cell : row) {
        
        if (!cell.flooded) {
          return false; 
        }
      }
    }
    
    return true; 
 
  }
   

 @Override
 public void onMouseClicked(Posn mouseCoor) {
     
   this.floodCellsWithMouseClick(mouseCoor);
   this.mouseClicks++; 
   
   if (this.allCellsFlooded() && this.mouseClicks <= this.maxClicks()) {
     this.endOfWorld("You Win!");
   } 
   else if (!this.allCellsFlooded() && this.mouseClicks == this.maxClicks()) {
     this.endOfWorld("You Lose");
   } 
 
 }
 
 void floodCellsWithMouseClick (Posn mouseCoor) {

   
   Posn index = new Posn (mouseCoor.x / new Cell().CELL_SIZE, 
                          mouseCoor.y / new Cell().CELL_SIZE);
   
   int newColor = this.board.get(index.y).get(index.x).color; 
   
   for (int idxRow = 0; 
       idxRow < this.numberOfRows;
       idxRow = idxRow + 1) {
       
     for (int idxCol = 0; 
         idxCol < this.numberOfCols;
         idxCol = idxCol + 1) {
       
       Cell thisCell = this.board.get(idxRow).get(idxCol);
       
       if (thisCell.flooded) {
         
         thisCell.color = newColor;
         this.floodCellBelow(thisCell, idxRow, idxCol); 
         this.floodCellAbove(thisCell, idxRow, idxCol);
         this.floodCellRight(thisCell, idxRow, idxCol);
         this.floodCellLeft(thisCell, idxRow, idxCol);
         
         
       }
     }
   }
 }
 
 void floodCellsInit () {
   
   int floodedColor = this.board.get(0).get(0).color; 
   
   for (int idxRow = 0; 
       idxRow < this.numberOfRows;
       idxRow = idxRow + 1) {
       
     for (int idxCol = 0; 
         idxCol < this.numberOfCols;
         idxCol = idxCol + 1) {
       
       Cell thisCell = this.board.get(idxRow).get(idxCol);
       
       if (thisCell.flooded) {
         
         thisCell.color = floodedColor; 
         this.floodCellBelow(thisCell, idxRow, idxCol); 
         this.floodCellAbove(thisCell, idxRow, idxCol);
         this.floodCellRight(thisCell, idxRow, idxCol);
         this.floodCellLeft(thisCell, idxRow, idxCol);
   
       }        
     }
   }
 }
 
 void floodCellBelow(Cell thisCell, int row, int col) {
   
   if (row + 1 >= this.numberOfRows || 
       this.board.get(row + 1).get(col).color != thisCell.color) {
     return ; 
   
   } else {
     this.board.get(row + 1).get(col).flooded = true;  
   }
 }
 
 void floodCellAbove(Cell thisCell, int row, int col) {
   
   if (row - 1 < 0  ||
      this.board.get(row - 1).get(col).color != thisCell.color) {
   
     return ; 
  
   } else {
     this.board.get(row - 1).get(col).flooded = true;  
   }
 }
 
 void floodCellRight(Cell thisCell, int row, int col) {
 
   if (col + 1 >= this.numberOfCols ||
       this.board.get(row).get(col + 1).color != thisCell.color) {
     return ; 
   } else {
     this.board.get(row).get(col + 1).flooded = true;  
   }
 }
 
 void floodCellLeft(Cell thisCell, int row, int col) {
 
   if (col - 1 < 0 || 
       this.board.get(row).get(col - 1).color != thisCell.color) {
     return ; 
   } else {
     this.board.get(row).get(col - 1).flooded = true;  
   }
 }
 
 

}

class Cell {
  int x;
  int y;
  int color;
  boolean flooded; 
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;
  
  Cell (int x, int y, int color, boolean flooded) {
    this.x = x;
    this.y = y; 
    this.color = color;
    this.flooded = flooded;
 
  }
  
  Cell () {}; 
   
  int CELL_SIZE = 25; 
  
  WorldImage cellImage() {
    if (this.color == 0) {
      return new RectangleImage (this.CELL_SIZE, this.CELL_SIZE, OutlineMode.SOLID, Color.ORANGE); 
    }
    else if (this.color == 1) {
      return new RectangleImage (this.CELL_SIZE, this.CELL_SIZE, OutlineMode.SOLID, Color.CYAN); 
    }
    else if (this.color == 2) {
      return new RectangleImage (this.CELL_SIZE, this.CELL_SIZE, OutlineMode.SOLID, Color.MAGENTA); 
    }
    else if (this.color == 3) {
      return new RectangleImage (this.CELL_SIZE, this.CELL_SIZE, OutlineMode.SOLID, Color.YELLOW); 
    }
    else if (this.color == 4) {
      return new RectangleImage (this.CELL_SIZE, this.CELL_SIZE, OutlineMode.SOLID, Color.PINK);
    }
    else if (this.color == 5) {
      return new RectangleImage (this.CELL_SIZE, this.CELL_SIZE, OutlineMode.SOLID, Color.GREEN);
    }
    else {
      throw new RuntimeException ("Cannot creat tile with color value over 5"); 
    }
      
  }

}  
  
class Utils {
  
  WorldImage drawAt(Cell c, FloodItWorld game, WorldImage background) {
    
    int backgroundLength = ((game.numberOfCols+1) * c.CELL_SIZE) / 2 - c.CELL_SIZE; 
    int backgroundWidth = ((game.numberOfRows+1) * c.CELL_SIZE) / 2 - c.CELL_SIZE; 
        
    return new OverlayOffsetImage
        (c.cellImage(),
         backgroundLength - (c.x * c.CELL_SIZE),  
         backgroundWidth - (c.y * c.CELL_SIZE),  
         background); 
  }
  
  
  WorldImage drawGrid(FloodItWorld game) {
    
    WorldImage result = game.background(); 
      
    for (int idxRow = 0; 
        idxRow < game.numberOfRows;
        idxRow = idxRow + 1) {
      
      for (int idxCol = 0; 
          idxCol < game.numberOfCols;
          idxCol = idxCol + 1) {
      
        Cell thisCell = game.board.get(idxRow).get(idxCol);
        
        result = new Utils().drawAt(thisCell, game, result); 
        
      }
    } 
    
    return result; 
    
  }
  
  
  ArrayList<ArrayList<Cell>> generateBoard (int rows, int cols, int colors) {
    
    ArrayList<Cell> resultRow = new ArrayList<Cell>(); 
    ArrayList<ArrayList<Cell>> resultGrid = new ArrayList<ArrayList<Cell>>(); 
    

    for (int idxRow = 0; 
        idxRow < rows;
        idxRow = idxRow + 1) {
    
      for (int idxCol = 0;                                   
          idxCol < cols;                              
          idxCol = idxCol + 1) {
        
        if (idxCol == 0 && idxRow == 0) {
          
          resultRow.add
          (new Cell(idxCol, idxRow, new Random().nextInt(colors), true)); 
          
        }
        else {
        
          resultRow.add
          (new Cell(idxCol, idxRow, new Random().nextInt(colors), false));     
        }
      }
      resultGrid.add(resultRow);
      resultRow = new ArrayList<Cell>(); 
       
    }
    
    return resultGrid; 
     
  }
  
  
  int checkRange(int val, int min, int max, String msg) {
    
    if (val >= min && val <= max) {
      return val;
    }
    else {
      throw new IllegalArgumentException(msg);
    }
  }

}

class ExampleFloodItWorld {
  
  Cell cell1, cell2, cell3, cell4, cell1a, cell5, cell6, cell7, cell8;  
  ArrayList<Cell> row1, row2, row1a, row3, row4; 
  ArrayList<ArrayList<Cell>> board, boarda, board2;  
  FloodItWorld game, gamea, game2;  
  
  void initData() {
    
    cell1 = new Cell(0,0,5,true); 
    cell2 = new Cell(0,1,4,false); 
    cell3 = new Cell(1,0,3,false); 
    cell4 = new Cell(1,1,2,false); 
    cell1a = new Cell(0,0,2,true); 
    
    cell5 = new Cell (0,0,5, true); 
    cell6 = new Cell (0,1,5, true);
    cell7 = new Cell (1,0,5, true);
    cell8 = new Cell (1,1,5, true);
    
    row1 = new ArrayList<Cell>(Arrays.asList(cell1, cell2)); 
    row2 = new ArrayList<Cell>(Arrays.asList(cell3, cell4));
    row1a = new ArrayList<Cell>(Arrays.asList(cell1a, cell2)); 
    
    row3 = new ArrayList<Cell>(Arrays.asList(cell5, cell6)); 
    row4 = new ArrayList<Cell>(Arrays.asList(cell7, cell8)); 
    
    board = new ArrayList<ArrayList<Cell>>(Arrays.asList(row1, row2)); 
    boarda = new ArrayList<ArrayList<Cell>>(Arrays.asList(row1a, row2));
    
    board2 = new ArrayList<ArrayList<Cell>>(Arrays.asList(row3, row4));
    
    game = new FloodItWorld(board, 2, 2, 5); 
    gamea = new FloodItWorld(boarda, 2, 2, 5);
    
    game2 = new FloodItWorld(board2, 2, 2, 5);
    
    
  }
  
  void testFloodedFirstCell (Tester t) {
    FloodItWorld game = new FloodItWorld(5,5,4);
    t.checkExpect(game.board.get(0).get(0).flooded, true); 
    t.checkExpect(game.board.get(1).get(1).flooded, false); 
  }
  

  
  void testMouseClick(Tester t) {
    this.initData();
    game.onMouseClicked(new Posn(48,48));
    gamea.mouseClicks = 1; 
    t.checkExpect(game, gamea);
    
  }
  
  void testAllCellsFlooded(Tester t) {
    this.initData();
    t.checkExpect(game2.allCellsFlooded(), true); 
    t.checkExpect(game.allCellsFlooded
        (), false); 
    
  }
 
  
  void testGame(Tester t) {
    
    FloodItWorld g = new FloodItWorld(15,15,4);    
    int widthOfGrid = g.numberOfCols * new Cell().CELL_SIZE; 
    int lengthOfGrid = g.numberOfRows * new Cell().CELL_SIZE; 
    int lengthOfMouseClickCounter =  new Cell().CELL_SIZE * 6; 
    
    g.bigBang(widthOfGrid, lengthOfGrid + lengthOfMouseClickCounter); 
    
  }
  

    
}

