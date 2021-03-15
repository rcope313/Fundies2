package lecture16;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


interface IList<T> {
  <U> IList<U> map(Function<T,U> converter);
  
}
  

class MtList<T> implements IList<T> {
  
  MtList() {}

  @Override
  public <U> IList<U> map(Function<T, U> converter) {
    // TODO Auto-generated method stub
    return new MtList<U>();
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public <U> IList<U> map(Function<T, U> converter) {
    // TODO Auto-generated method stub
    return new ConsList<U>(converter.apply(this.first),this.rest.map(converter));
  }

}

//Represents a function object defined over Shapes that returns a Double
interface IShapeVisitor<R> extends IFunc<IShape, R> {
  
  R visitCircle (Circle c);
  R visitRectangle (Rect r); 
  
}

// Implements a function taking a Shape and returning a Double,
//that computes the area of the given shape
class ShapeArea implements IShapeVisitor<Double> {
  
  //Everything from the IShapeVisitor interface:
  public Double visitCircle (Circle c) {
    return Math.PI * c.radius * c.radius;
  }
  public Double visitRectangle (Rect r) {
    return (double) (r.w * r.h); 
  }
  
  //Everything from the IFunc interface:
  public Double apply (IShape shape) {
    return shape.accept(this);
  }
}


public interface IShape {
  
// Implements a function taking a Shape and returning a Double,
// that computes the area of the given shape
  <R> R accept (IShapeVisitor<R> func);
  
}

class Circle implements IShape {
  int x, y;
  int radius;
  String color;
  Circle(int x, int y, int r, String color) {
    this.x = x;
    this.y = y;
    this.radius = r;
    this.color = color;
  }
  
  public <R> R accept (IShapeVisitor<R> func) {
    return func.visitCircle(this); 
  }
  
}

class Rect implements IShape {
  int x, y, w, h;
  String color;
  Rect(int x, int y, int w, int h, String color) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.color = color;
  }
  
  public <R> R accept (IShapeVisitor<R> func) {
    return func.visitRectangle(this); 
  }
  
}
