package lecture25;


class DequeForwardIterator<T> implements Iterator<T> {
  
  Deque<T> items;
  ANode<T> nextItem; 
  
  DequeForwardIterator (Deque<T> items, ANode<T> nextItem) {
    this.items = items;
    this.nextItem = nextItem; 
 
  }
  
  public boolean hasNext() {
    return this.items.header.next.isNotSentinel(); 
  }
  
  public T next() {
    Node<T> itemsAsNode = this.items.header.next.asNode();
    T answer = itemsAsNode.data;
    this.nextItem = itemsAsNode.next; 
    return answer;
  }
    
  
  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

class Deque<T> implements Iterable<T>{
  
  Sentinel<T> header;
  
  Deque (Sentinel<T> header) {
    this.header = header;
  }
  
  Deque () {
    this.header = new Sentinel<T>();  
  }

  @Override
  public Iterator<T> iterator() {
    return new DequeForwardIterator<T>(this.header.next);
  }
  
  
  
}

abstract class ANode<T> {
  ANode<T> prev;
  ANode<T> next;
  
  ANode (ANode<T> prev, ANode<T> next) {
    this.prev = prev;
    this.next = next; 
  }
  
  ANode() {
    this.prev = this;
    this.next = this;
  }
 
  abstract boolean isNotSentinel(); 
  
  abstract Node<T> asNode();
   
  void updateNext (ANode<T> theNode) {
    if (theNode == null) {
      throw new IllegalArgumentException ("cannot add a null node"); 
    } 
    else {
      this.next = theNode; 
    }
  }
  
  // this.next.updatePrev(this)
  void updatePrev (ANode<T> theNode) {
    if (theNode == null) {
      throw new IllegalArgumentException ("cannot add a null node"); 
    } 
    else {
      this.prev = theNode;  
    }
  }
  
}

class Sentinel<T> extends ANode<T> {
  
  Sentinel (ANode<T> prev, ANode<T> next) {
    super (prev, next); 
    
    this.prev.updateNext(this);
    this.next.updatePrev(this);

  }
  
  Sentinel () {
    this.prev = this;
    this.next = this;

  }
  
  public boolean isNotSentinel() { return false; }
  
  public Node<T> asNode() { throw new RuntimeException("cannot convert Sentinel to Node"); }
  
  
}

class Node<T> extends ANode<T> {
  T data;
  
  Node (T data) {
    this.prev = null;
    this.next = null; 
    this.data = data; 

  }
  
  Node (T data, ANode<T> prev, ANode<T> next) {
    this.data = data;
    this.prev = prev;  
    this.next = next; 
    
    this.prev.updateNext(this);
    this.next.updatePrev(this);
    
    
  }
  
  public boolean isNotSentinel() { return true; }
  
  public Node<T> asNode() { return this; }
  
}
