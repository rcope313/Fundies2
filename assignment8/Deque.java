package assignment8;

import tester.Tester;

class Deque<T> {
  
  Sentinel<T> header;
  
  Deque (Sentinel<T> header) {
    this.header = header;
  }
  
  Deque () {
    this.header = new Sentinel<T>();  
  }
  
  int size() {
    return this.sizeHelper(this.header, 0);
  }
  
  int sizeHelper(ANode<T> theNode, int acc) {
    if (theNode.next == this.header) {
      return acc; 
    }
    return this.sizeHelper(theNode.next, acc + 1); 
    
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
  
}

class ExamplesDeque {
  
  
  Deque<String> dequeS, dequeUS, emptyDeque; 
  Node<String> node1S, node2S, node3S, node4S, 
               node1US, node2US, node3US, node4US; 
  Sentinel<String> sentinelS, sentinelUS; 

  
  void initTestConditions() {
    
  
//  this.sentinelUS = new Sentinel<String> (node4US, node1US); 
//  this.dequeUS = new Deque<String> (sentinelUS); 
//  this.emptyDeque = new Deque<String> (new Sentinel<String>());
   
  sentinelS = new Sentinel<String>(); 
    
  node1S = new Node<String> ("a");  
  node2S = new Node<String> ("b"); 
  node3S = new Node<String> ("c"); 
  node4S = new Node<String> ("d");
  
  
  
  dequeS = new Deque<String> (sentinelS); 
  
//  this.node1US = new Node<String> ("e", sentinelUS, node2US); 
//  this.node2US = new Node<String> ("h", node1US, node3US); 
//  this.node3US = new Node<String> ("g", node2US, node4US); 
//  this.node4US = new Node<String> ("f", node3US, sentinelUS);
  
   
  }
  
  void testSize (Tester t) {
    this.initTestConditions();
    t.checkExpect(this.dequeS, null); 
    
//    t.checkExpect(dequeS.size(), 4);  
//    t.checkExpect(emptyDeque.size(), 0); 

  }
  
}


