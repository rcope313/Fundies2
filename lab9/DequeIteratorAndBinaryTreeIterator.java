package lab9;
import java.util.Iterator;

class DequeForwardIterator<T> implements Iterator<T> {
  ANode<T> item; 
  
  DequeForwardIterator (ANode<T> item) {
    this.item = item; 
  }

  @Override
  public boolean hasNext() {
    return this.item.isNode(); 
  }

  @Override
  public T next() {
    Node<T> itemAsNode = this.item.asNode(); 
    T answer = itemAsNode.data; 
    this.item = this.item.next; 
    return answer; 
  } 
}

class BreadthFirstIterator<T> implements Iterator<T> {
  
  Deque<IBinaryTree<T>> worklist; 
  
  BreadthFirstIterator(IBinaryTree<T> source) {
    this.worklist = new Deque<IBinaryTree<T>>();
    this.addIfNotLeaf(source);
    
  }
  
  //EFFECT: only adds the given binary-tree if it's not a leaf
  void addIfNotLeaf(IBinaryTree<T> bt) {
    if (bt.isNode()) {
     this.worklist.addAtTail(bt);
   }
 }
  

  @Override
  public boolean hasNext() {
    return this.worklist.size() > 0;
  }

  @Override
  public T next() {
    // Get (and remove) the first item on the worklist --
    // and we know it must be a BTNode
    BTNode<T> BTnode = this.worklist.removeFromHead().asNode(); 
    // Add the children of the node to the tail of the list
    this.addIfNotLeaf(BTnode.left);
    this.addIfNotLeaf(BTnode.right);
    // return the answer
    return BTnode.data;
  }
  
  
}


class Deque<T> implements Iterable<T> {
  
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
  
  void addAtHead (T t) {
    ANode<T> previousHead = this.header.next; 
    this.header.next = new Node<T>(t, this.header, previousHead); 
  }
  
  void addAtTail (T t) {
    ANode<T> previousTail = this.header.prev;
    this.header.prev = new Node<T>(t, previousTail, this.header); 
  
  }
  
  T removeFromHead () {
    ANode<T> currentHead = this.header.next; 
    ANode<T> newHead = this.header.next.next; 
    
    if (currentHead.isNode()) {
      this.header.next = newHead; 
      return currentHead.asNode().data; 
    }
    else {
      throw new RuntimeException ("cannot remove from an empty list"); 
    }
       
  }
  
  T removeFromTail() {
    ANode<T> currentTail = this.header.prev; 
    ANode<T> newTail = this.header.prev.prev; 
    
    if (currentTail.isNode()) {
      this.header.prev = newTail; 
      return currentTail.asNode().data; 
    }
    else {
      throw new RuntimeException ("cannot remove from an empty list"); 
    }
    
  }
  
  int size() {
    
    int result = 0; 
    
    for (T item : this) {
      
      result ++; 
    }
    return result; 
    
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
 
  abstract boolean isNode(); 
  
  abstract Node<T> asNode(); 
    
  void updateNext (ANode<T> theNode) {
    if (theNode == null) {
      throw new IllegalArgumentException ("cannot add a null node"); 
    } 
    else {
      this.next = theNode; 
    }
  }
  
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
  
  @Override 
  public boolean isNode() {
    return false; 
  }

  @Override
  Node<T> asNode() {
    throw new RuntimeException ("cannot convert Sentinel to Node"); 
  }

  
}

class Node<T> extends ANode<T> {
  T data;
  
  Node (T data) {
    this.prev = null;
    this.next = null; 
    this.data = data; 

  }
  
  Node (T data, ANode<T> prev, ANode<T> next) {
    super (prev, next); 
    this.next = next; 
    
    this.prev.updateNext(this);
    this.next.updatePrev(this);
    
    
  }
  
  @Override 
  public boolean isNode() {
    return true;  
  }

  @Override
  Node<T> asNode() {
    return this;  
  }

}

interface IBinaryTree<T> extends Iterable<T> {
  
  boolean isNode(); 
  BTNode<T> asNode(); 
  
  
}

class BTNode<T> implements IBinaryTree<T> {
  IBinaryTree<T> left;
  IBinaryTree<T> right;
  T data; 
  
  BTNode (IBinaryTree<T> left, IBinaryTree<T> right, T data) {
    this.left = left;
    this.right = right; 
    this.data = data; 
  }
  
  @Override
  public Iterator<T> iterator() {
    return new BreadthFirstIterator<T>(this); 
  } 

  @Override
  public boolean isNode() {
    return true; 
  }

  @Override
  public BTNode<T> asNode() {
    return this; 
  }

   
}

class Leaf<T> implements IBinaryTree<T> {
  
  Leaf () {}
  
  @Override
  public Iterator<T> iterator() {
    return new BreadthFirstIterator<T>(this); 
  } 
  
  @Override
  public boolean isNode() {
    return false;
  }

  @Override
  public BTNode<T> asNode() {
    throw new RuntimeException ("cannot convert leaf to node"); 
  }

 
}
