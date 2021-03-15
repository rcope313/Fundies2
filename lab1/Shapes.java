package lab1;

// to represent a geometric shape
interface IShape { }

// to represent a circle
class Circle implements IShape {
    int x;
    int y;
    int rad;

    Circle(int x, int y, int rad) {
        this.x = x;
        this.y = y;
        this.rad = rad;
    }
}

// to represent a rectangle
class Rect implements IShape {
    int x;
    int y;
    int w;
    int h;

    Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}

// to represent a combined shape
class Combo implements IShape {
    IShape top;
    IShape bot;

    Combo(IShape top, IShape bot) {
        this.top = top;
        this.bot = bot;
    }
}


// to represent examples and tests for shapes
class ExampleShapes {
    IShape circle = new Circle(50, 50, 50);
    IShape rleft = new Rect(20, 20, 20, 20);
    IShape rBot = new Rect(20, 60, 60, 20);
    IShape blackCircle = new Circle (20,20,20);
    IShape longRectangle = new Rect (0, 0, 50, 10);
    IShape shortRectangle = new Rect (0, 0, 10, 50);

    IShape addMouth = new Combo(this.rBot, this.circle);
    IShape addLeftEye = new Combo(this.rleft, this.addMouth);
    IShape face = new Combo(new Rect(60, 20, 20, 20), this.addLeftEye); 
    IShape car = new Combo (this.shortRectangle, this.longRectangle);
}