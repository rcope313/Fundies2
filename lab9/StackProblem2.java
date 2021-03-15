package lab9;
import java.util.ArrayList;
import java.util.Iterator;

class ListofLists<T> implements Iterable<T> {
  ArrayList<ArrayList<T>> contents; 
  
  void addNewList() {
   
    this.contents.add(new ArrayList<T>()); 
 
  }
  
  void add(int index, T object) {
    
    this.contents.get(index).add(object); 
  }
  
  ArrayList<T> get(int index) {
    
    return this.contents.get(index); 
  }
  
  int size() {
    
    int result = 0; 
    
    for (ArrayList<T> list : contents) {
      
      for (T item : list) {
        
        result ++; 
        
      }
    }
    
    return result; 
  }

  @Override
  public Iterator<T> iterator() {
    return new ListofListsIterator<T>(this.contents); 
  }
  
}

class ListofListsIterator<T> implements Iterator<T> {
  
  ArrayList<ArrayList<T>> list; 
  int nextIdxLoL; 
  int nextIdxL; 
  
  ListofListsIterator (ArrayList<ArrayList<T>> list) {
    this.list = list; 
    this.nextIdxLoL = 0; 
    this.nextIdxL = 0; 
    
  }
  @Override
  public boolean hasNext() {
    return this.nextIdxL < this.numberOfElements(this.list); 
  }
  
  int numberOfElements(ArrayList<ArrayList<T>> lol) {
    
    int result = 0; 
    
    for (ArrayList<T> list : lol) {
      for (T item : list) {
        result ++; 
        
      }
    }
    return result; 
  }
    
  @Override 
  public T next() { 
    T answer = this.list.get(this.nextIdxLoL).get(nextIdxL); 
    this.nextIdxL ++; 
    return answer;
  }
  

  
}

