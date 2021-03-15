package lecture2;

// to represent a train station
public interface IStation {

}

// to represent a subway station
class TStop implements IStation {
  String name;
  String line;
  double price;
  
  TStop (String name, String line, double price) {
    this.name = name;
    this.line = line;
    this.price = price;
    
    
  }
  
}

// to represent a stop on the commuter line
class CommStation implements IStation {
  String name;
  String line;
  boolean express;
  
  CommStation (String name, String line, boolean express) {
    this.name = name;
    this.line = line;
    this.express = express;
  }
}
