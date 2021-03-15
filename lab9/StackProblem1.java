package lab9;
import java.util.ArrayList;

class StackProblem1<T> {
  Deque<T> contents;
  
  // adds an item to the stack
  void push(T item) {
    contents.addAtHead(item);
  }
  
  boolean isEmpty() {
    return contents.size() == 0; 
  }
  
  //removes and returns the top of the stack
  T pop() {
    return contents.removeFromHead(); 
  }

}

class StringCreator {
  String string;
  StackProblem1<String> stack; 
  
  StringCreator (String string) {
    this.string = string; 
    
  }
  
  // adds a character to the end of the string
  void add (Character c) {
    
   this.stack.push(this.string);  
   this.string.concat(Character.toString(c));
   
  }
  
  void remove () {
    
    this.stack.push(this.string);
    
    int stringLength = string.length() - 1; 
    
    if (stringLength > 0) { 
       this.string.substring(0, stringLength); 
    }
    else {
      throw new RuntimeException ("cannot remove from an empty string"); 
    }
    
    
  }
  
  String getString() {
    return this.string; 
  }
  
  void undo() {
    
    String newString = this.stack.pop(); 
    this.stack.pop();
    
    this.string = newString; 
     
  }
  
}

class Utils {
  
  <T> StackProblem1<T> reverseHelper(ArrayList<T> source) {
    
    StackProblem1<T> stack = new StackProblem1<T>(); 
    
    for (T item : source) {
      
      stack.push(item);
      
    }
    
    return stack; 
    
  }
  
  <T> ArrayList<T> reverse(ArrayList<T> source) {
    
    StackProblem1<T> reversedStack = new Utils().reverseHelper(source);
    ArrayList<T> resultList = new ArrayList<T>(); 
    
    for (T item : reversedStack.contents) {
      
      resultList.add(item); 
       
    }
    
    return resultList; 
  }
  
}
