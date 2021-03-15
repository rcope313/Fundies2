package lab8;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import tester.Tester;

class ArrayUtil {
  
  <T> ArrayList<T> filter (ArrayList<T> arr, Predicate<T> pred) {
    
    ArrayList<T> result = new ArrayList<T>(); 
    
    for (T t : arr) {
      
      if (pred.test(t)) {
        result.add(t); 
      }
    }
    
    return result; 
    
  }
  
  <T> void removeExcept(ArrayList<T> arr, Predicate<T> pred) {
    
    ArrayList<T> duplicate = arr; 
    
    for (int idx = 0;                                   
        idx < arr.size();                              
        idx = idx + 1) {
      
      if (!pred.test(arr.get(idx))) {
        duplicate.remove(idx); 
        
      } 
    }
    
    duplicate = arr; 
    
  }
  
  <T> void removeExcept_v2(ArrayList<T> arr, T t) {
    
    ArrayList<T> duplicate = arr; 
    
    for (int idx = 0;                                   
        idx < arr.size();                              
        idx = idx + 1) {
      
      if (t == (arr.get(idx))) {
        duplicate.remove(idx); 
        
      } 
    }
    
    duplicate = arr; 
    
  }
}
  
class porOrNeg implements Predicate<Integer> {
  
  public boolean test (Integer x) {
    return x >= 0; 
  }
    
}
 


class ExamplesArrays {
  
  ArrayList<Integer> listBefore, listAfter; 
  
  void voidData() {
  
  listBefore = new ArrayList<Integer>(Arrays.asList(-1, 1, -2, 2, -3, 3)); 
  listAfter = new ArrayList<Integer>(Arrays.asList(1,2,3)); 
  
  }
  
  
  void testArrayUtils (Tester t) {
    this.voidData();
    t.checkExpect(new ArrayUtil().filter(listBefore, new porOrNeg()), listAfter); 
    new ArrayUtil().removeExcept(listBefore, new porOrNeg()); 
    t.checkExpect(listBefore, listAfter); 
    this.voidData();
    new ArrayUtil().removeExcept_v2(listBefore, 2); 
    t.checkExpect(listBefore, 
        new ArrayList<Integer>(Arrays.asList(-1, 1, -2, -3, 3))); 
    

    
  }
  
}


