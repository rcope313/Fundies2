package myFirstProject;
import tester.Tester;
import javalib.funworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;

// represent the world of a fishy 
class FishyWorld extends World {
  int width = 600; 
  int height = 500;
  PlayerFishy playerfishy; 
  BackgroundFishy fishy1;
  BackgroundFishy fishy2;
  BackgroundFishy fishy3;
  BackgroundFishy fishy4;
  BackgroundFishy fishy5;
  
  // the constructor
  public FishyWorld (PlayerFishy playerfishy, BackgroundFishy fishy1, BackgroundFishy fishy2, 
      BackgroundFishy fishy3, BackgroundFishy fishy4, BackgroundFishy fishy5) {
    super ();
    this.playerfishy = playerfishy; 
    this.fishy1 = fishy1;
    this.fishy2 = fishy2;
    this.fishy3 = fishy3;
    this.fishy4 = fishy4;
    this.fishy5 = fishy5; 
    
  }
  
  // move fishy when the player presses a key
  public World onKeyEvent(String ke) {
      return new FishyWorld(this.playerfishy.moveFishy(ke, this), 
                            this.fishy1,
                            this.fishy2,
                            this.fishy3,
                            this.fishy4,
                            this.fishy5); 
  }
  
  //On tick move other fishies in the background
  public World onTick() {
      return new FishyWorld(this.playerfishy.fishyInteraction(fishy1, fishy2, fishy3, fishy4, fishy5, this), 
                            this.fishy1.swimOnScreen(this).fishyInteraction(this.playerfishy, this),
                            this.fishy2.swimOnScreen(this).fishyInteraction(this.playerfishy, this),
                            this.fishy3.swimOnScreen(this).fishyInteraction(this.playerfishy, this),
                            this.fishy4.swimOnScreen(this).fishyInteraction(this.playerfishy, this),
                            this.fishy5.swimOnScreen(this).fishyInteraction(this.playerfishy, this)); 
  }
  
  // produce the image of this world by adding the moving blob to the
  // background image
  public WorldScene makeScene() {
    
    // makeSceneHelper(getEmptyScene(), this.fishies);
    return this
        .getEmptyScene()
        .placeImageXY(this.playerfishy.fishyImage(), 
                      this.playerfishy.x, 
                      this.playerfishy.y)
        .placeImageXY(this.fishy1.BackgroundFishyImage(), 
                      this.fishy1.x,
                      this.fishy1.y)
        .placeImageXY(this.fishy2.BackgroundFishyImage(), 
                      this.fishy2.x, 
                      this.fishy2.y)
        .placeImageXY(this.fishy3.BackgroundFishyImage(), 
                      this.fishy3.x, 
                      this.fishy3.y) 
        .placeImageXY(this.fishy4.BackgroundFishyImage(), 
                      this.fishy4.x, 
                      this.fishy4.y)
        .placeImageXY(this.fishy5.BackgroundFishyImage(), 
                      this.fishy5.x, 
                      this.fishy5.y);
      
  }
  
//  private WorldScene makeSceneHelper(WorldScene startScene, LoFishy fishies) {
//    if (fishies.isEmpty()) {
//      return startScene;
//    }
//    
//    return makeSceneHelper(
//        startScene.placeImageXY(fishies.first().image, fishies.first().x, fishies.first.y)
//    );
//  }
    
  // end world when fishy is x radius large 
  public WorldEnd worldEnds() {
    if (this.playerfishy.radius >= 100) {
      return new WorldEnd(true, this.lastScene("You've Won!"));
    }
    else if (this.fishy1.radius == 0 ||
             this.fishy2.radius == 0 ||
             this.fishy3.radius == 0 ||
             this.fishy4.radius == 0 ||
             this.fishy5.radius == 0) {
      return new WorldEnd(true, this.lastScene("GULP!!!")); 
    }
    else {
      return new WorldEnd(false, this.makeScene()); 
    }
  }
  
//produce the last image of this world by adding text to the image 
  public WorldScene lastScene(String s) {
      return this.makeScene().placeImageXY
          (new TextImage(s, 50, FontStyle.BOLD, Color.black), this.width/2, this.height/2);
  }
  
}

//Class that represents the fishy moving on the canvas
class PlayerFishy {
  int x;
  int y;
  int radius;


  // The constructor 
  PlayerFishy(int x, int y, int radius) {
       this.x = x;
       this.y = y;
       this.radius = radius;

 }

  // produce the image of this fishy 
  WorldImage fishyImage() {
       return new CircleImage(this.radius, "solid", Color.red);
 }

  // move this blob 5 pixels in the direction given by the ke or change its
  // color to Green, Red or Yellow
  public PlayerFishy moveFishy(String ke, FishyWorld world) {
       if (ke.equals("right")) {
         return new PlayerFishy(this.x + 10, this.y, this.radius).outsideBounds(world); 
         
     } else if (ke.equals("left")) {
         return new PlayerFishy(this.x - 10, this.y, this.radius).outsideBounds(world); 
         
     } else if (ke.equals("up")) {
         return new PlayerFishy(this.x, this.y - 10, this.radius).outsideBounds(world); 
         
     } else if (ke.equals("down")) {
         return new PlayerFishy(this.x, this.y + 10, this.radius).outsideBounds(world); 
         
     } else if (ke.equals("x")) {
         return new PlayerFishy(this.x, this.y, this.radius + 10).outsideBounds(world); 
     }
         return this;
  }
  
  //fishy movement that when it exits the screen on one side it re-enters on the other side
  PlayerFishy outsideBounds(FishyWorld world) {
    if (this.x > world.width) {
      return new PlayerFishy (0, this.y, this.radius);  
    }
    else if (this.x < 0) {
      return new PlayerFishy (world.width, this.y, this.radius); 
    }
    else if (this.y > world.height) {
      return new PlayerFishy (this.x, 0, this.radius); 
    }
    else if (this.y < 0) {
      return new PlayerFishy (this.x, world.height, this.radius); 
    }
    else {
      return this; 
    }
  }
  
  //how background fishy interact with that playerfishy 
  public PlayerFishy fishyInteraction (BackgroundFishy fishy1, BackgroundFishy fishy2, 
      BackgroundFishy fishy3, BackgroundFishy fishy4, BackgroundFishy fishy5, FishyWorld world) {
   if (fishy1.fishyInteractionBol(this, world) ||
       fishy2.fishyInteractionBol(this, world) ||
       fishy3.fishyInteractionBol(this, world) ||
       fishy4.fishyInteractionBol(this, world) ||
       fishy5.fishyInteractionBol(this, world)) {
     return new PlayerFishy (this.x, this.y, this.radius +2);
   }
   else {
     return this; 
   }
 }
  
  
  
  
}

class BackgroundFishy {
  boolean swimToRight;  
  int x;
  int y;
  int radius;
 
  
  BackgroundFishy (boolean swimToRight, int x, int y, int radius) {
    this.swimToRight = swimToRight; 
    this.x = x;
    this.y = y;
    this.radius = radius; 
   
  }
  
  //method for ontick key handler 
  public BackgroundFishy swimOnScreen (FishyWorld world) {
    if (this.swimToRight) {
      return new BackgroundFishy 
          (this.swimToRight, this.x + 5, this.y, this.radius).outsideBounds(world); 
    }
    else {
      return new BackgroundFishy 
          (this.swimToRight, this.x - 5, this.y, this.radius).outsideBounds(world); 
    }
  }
  
  //method to indicate background fishy movement type
  public BackgroundFishy outsideBounds (FishyWorld world) {
    if (this.x > world.width || this.x < 0) {
      return new Utils().newBackgroundFishy(world); 
    }
    else {
      return this; 
    }
  }
  
  // image of background fishy
  public WorldImage BackgroundFishyImage() {
    return new CircleImage(this.radius, "solid", Color.blue);
  }
  
  
  // how background fishy interact with that playerfishy 
  public BackgroundFishy fishyInteraction (PlayerFishy that, FishyWorld world) {
    if ((this.x + this.radius) >= (that.x - that.radius) &&
        (this.x - this.radius) <= (that.x + that.radius) &&
        (this.y + this.radius) >= (that.y - that.radius) &&
        (this.y - this.radius) <= (that.y + that.radius) &&
         this.radius <= that.radius) {         
      return new Utils().newBackgroundFishy(world); 
    }
    else if ((this.x + this.radius) >= (that.x - that.radius) &&
             (this.x - this.radius) <= (that.x + that.radius) &&
             (this.y + this.radius) >= (that.y - that.radius) &&
             (this.y - this.radius) <= (that.y + that.radius) &&
             this.radius > that.radius) {  
      return new BackgroundFishy (false, 0, 0, 0);
    }
    else {
      return this; 
    } 
  }
  
  //how background fishy interact with that playerfishy 
  public boolean fishyInteractionBol (PlayerFishy that, FishyWorld world) {
    if ((this.x + this.radius) >= (that.x - that.radius) &&
        (this.x - this.radius) <= (that.x + that.radius) &&
        (this.y + this.radius) >= (that.y - that.radius) &&
        (this.y - this.radius) <= (that.y + that.radius) &&
        this.radius <= that.radius) {         
      return true; 
    }
    else {
      return false; 
    }
  }
}


class Utils {
  

  // creates a new background fishy
  public BackgroundFishy newBackgroundFishy (FishyWorld world) {
    if (new Random().nextInt(2) == 1) { 
      return new BackgroundFishy 
          (true, 0, new Random().nextInt(world.height+1), new Random().nextInt(51));
    }
    else {
      return new BackgroundFishy 
          (false, world.width, new Random().nextInt(world.height+1), new Random().nextInt(51)); 
    }
  }
  

}

class FishyExamples {
  
  boolean testBlobWorld(Tester t) {
    
    // run the game
    FishyWorld w = new FishyWorld
        (new PlayerFishy(300, 250, 20),
         new BackgroundFishy (true, 0, 320, 19), 
         new BackgroundFishy (false, 600, 240, 19),
         new BackgroundFishy (true, 0, 40, 19),
         new BackgroundFishy (false, 600, 40, 19),
         new BackgroundFishy (true, 0, 140, 19)); 
        
    return w.bigBang(600, 500, .1);
    }
}

