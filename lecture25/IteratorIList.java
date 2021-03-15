package lecture25;

//Iterator<T> iterator = anyIterable.iterator();
//
//while (iterator.hasNext()) {
//    T t = iterator.next();
//    ...body...
//}



class IListIterator<T> implements Iterator<T> {
  IList<T> items;
  
  IListIterator(IList<T> items) {
    this.items = items;
    
  }
  
  public boolean hasNext() {
    return this.items.isCons();
   
  }
  
  public T next() {
    ConsList<T> itemsAsCons = this.items.asCons();
    T answer = itemsAsCons.first;
    this.items = itemsAsCons.rest;
    return answer;
  }
  
  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
  
}

interface IList<T> extends Iterable<T>{
  
  boolean isCons();
  ConsList<T> asCons(); 
  
}


class MtList<T> implements IList<T> {

  MtList() {} 
  
  public boolean isCons() {return false;}
  
  public ConsList<T> asCons() {throw new RuntimeException ("cannot convert empty list to consList"); }
  
  // create an iterator list such at newIListIterator.items == this; 
  public Iterator<T> iterator() {
    return new IListIterator<T>(this); 
    
  }
  
}

class ConsList<T> implements IList<T> {
  T first; 
  IList<T> rest;
  
  public ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public boolean isCons() {return true;}
  
  public ConsList<T> asCons() {return this;}
  
  //create an iterator list such at newIListIterator.items == this; 
  public Iterator<T> iterator() {
    return new IListIterator<T>(this); 
  }
}
