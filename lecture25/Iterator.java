package lecture25;

import java.util.ArrayList;
import lists.IList;
import lists.ConsList;
import lists.MtList;


//Iterator<T> iterator = anyIterable.iterator();
//
//  while (iterator.hasNext()) {
//      T t = iterator.next();
//      ...body...
//}




//Represents anything that can be iterated over
interface Iterable<T> {
  
  // Returns an iterator over this collection
  Iterator<T> iterator();
  
}

//Represents the ability to produce a sequence of values
//of type T, one at a time
interface Iterator<T> {
  
   // Does this sequence have at least one more value?
   boolean hasNext();
   
   // Get the next value in this sequence
   // EFFECT: Advance the iterator to the subsequent value
   T next();
   
   // EFFECT: Remove the item just returned by next()
   // NOTE: This method may not be supported by every iterator; ignore it for now
   void remove();

}

class ArrayListIterator<T> implements Iterator<T> {
  
  // the list of items that this iterator iterates over
  ArrayList<T> items;
  // the index of the next item to be returned
  int nextIdx;
  
  // Construct an iterator for a given ArrayList
  ArrayListIterator(ArrayList<T> items) {
    this.items = items;
    this.nextIdx = 0;
  }
 
  public boolean hasNext() {
    return this.nextIdx < this.items.size();
  
  }
 
  public T next() {
    T answer = this.items.get(this.nextIdx);
    this.nextIdx = this.nextIdx + 1;
    return answer;
  }
 
  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

