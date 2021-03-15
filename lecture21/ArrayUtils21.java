package lecture21;
import java.util.ArrayList;
import java.util.function.Function;

import tester.Tester;

class ArrayUtils {
  // EFFECT: Exchanges the values at the given two indices in the given array
  <T> void swap(ArrayList<T> arr, int index1, int index2) {
    T oldValueAtIndex2 = arr.get(index2);
   
    arr.set(index2, arr.get(index1));
    arr.set(index1, oldValueAtIndex2);
  }
  
  
  <T, U> ArrayList<U> mapBeta(ArrayList<T> arr, Function<T, U> func) {
    ArrayList<U> result = new ArrayList<U>(); // set the destiation list as an empty list
    return this.mapHelpBeta(arr, func, 0, result); // as seen down here 
  }
  
  //Computes the result of mapping the given function over the source list
  //from the given current index to the end of the list, and returns the
  //given destination list
  //EFFECT: modifies the destination list to contain the mapped results
  <T, U> ArrayList<U> mapHelpBeta(ArrayList<T> source, Function<T, U> func,
                             int curIdx, ArrayList<U> dest) {
   if (curIdx >= source.size()) {// if the current index is at the end, i.e. the source size
     return dest;
   }
   else {
     dest.add(func.apply(source.get(curIdx))); //to the new list, apply this function to the current index of the source list
     return this.mapHelpBeta(source, func, curIdx + 1, dest); // then recurse on the next index 
   }
  }
  

  <T, U> ArrayList<U> map(ArrayList<T> arr, Function<T, U> func) {
    
   ArrayList<U> result = new ArrayList<U>(); // set our result list as empty
   for (T t : arr) { // for each element in our array list..
     result.add(func.apply(t)); // apply our function to it, and add it to our resulting list
   }
   return result;
  }
  
  
  
  
  
}


class ExampleArrayLists {
  void testGet(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    t.checkException(new IndexOutOfBoundsException("Index: 0, Size: 0"),
                     someStrings, "get", 0);
    someStrings.add("First string");
    someStrings.add("Second string");
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Second string");
    t.checkException(new IndexOutOfBoundsException("Index: 3, Size: 2"),
                     someStrings, "get", 3);
  }
  
  void testAdd(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    someStrings.add("First string");
    someStrings.add("Second string");
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Second string");
 
    // Insert this item at index 1, and move everything else back
    someStrings.add(1, "Squeezed in");
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Squeezed in");
    t.checkExpect(someStrings.get(2), "Second string");
  }
  
  //In ExampleArrayLists
  void testSwap(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    someStrings.add("Second string");
    someStrings.add("First string");

 // Do something to fix the list's order

    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Second string");
  }

}
