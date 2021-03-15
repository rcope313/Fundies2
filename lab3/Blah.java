package lab3;
import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import javalib.worldcanvas.*;   // so we can view our images
import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Red, Green, Yellow, Blue, Black, White)
      

public class Blah {

}


class ExamplesShapes {
  
  boolean showImage(WorldImage image, String description) {
    int width = (int) Math.ceil(image.getWidth());
    int height = (int) Math.ceil(image.getHeight());
    WorldCanvas canvas = new WorldCanvas(width, height, description);
    WorldScene scene = new WorldScene(width, height);
    return canvas.drawScene(scene.placeImageXY(image, width / 2, height / 2))
        && canvas.show();
  }
  
  WorldImage circle = new CircleImage(100, OutlineMode.SOLID, Color.BLUE);
  WorldCanvas c = new WorldCanvas(500, 500);
  WorldScene s = new WorldScene(500, 500);
  WorldImage circleBlue = new CircleImage(100, OutlineMode.SOLID, Color.BLUE);
  WorldImage circleRed = new CircleImage(100, OutlineMode.SOLID, Color.RED);
  
  WorldImage circle2 = new CircleImage(50,OutlineMode.OUTLINE,Color.RED);
  WorldImage triangle = new TriangleImage(new Posn(0,0), new Posn (0,20), new Posn(20,40),
                                          OutlineMode.OUTLINE, Color.MAGENTA); 
  WorldImage rectangle = new RectangleImage(50, 10, OutlineMode.OUTLINE, Color.DARK_GRAY); 
  WorldImage helloWorld = new TextImage("Hello World", 24, FontStyle.BOLD_ITALIC, Color.cyan);
  WorldImage empty = new EmptyImage(); 
  
  WorldImage besideImage = new BesideImage(rectangle,circle2);
  WorldImage AboveImage = new AboveImage(rectangle,circle2); 
  
  WorldImage visiblePinHole = new VisiblePinholeImage(circle);
  WorldImage movedPinHole = new VisiblePinholeImage(circle.movePinhole(5, 5));
  
  
  
  boolean testDrawMyShapes(Tester t) {
    return showImage(movedPinHole, "Moved PinHole");

  }
}
