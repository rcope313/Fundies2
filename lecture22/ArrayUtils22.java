package lecture22;
import java.util.ArrayList;
import java.util.Comparator;

import tester.Tester;


class ArrayUtils22 {
  

  <T> int gen_binarySearch_v2(ArrayList<T> arr, T target, Comparator<T> comp) {
   return this.gen_binarySearchHelp_v2(arr, target, comp, 0, arr.size());
  }
  
  <T> int gen_binarySearchHelp_v2(ArrayList<T> arr, T target, Comparator<T> comp,
                                 int lowIdx, int highIdx) {
   int midIdx = (lowIdx + highIdx) / 2;
   if (lowIdx >= highIdx) {
     return -1;
   }
   else if (comp.compare(target, arr.get(midIdx)) == 0) {
     return midIdx;
   }
   else if (comp.compare(target, arr.get(midIdx)) > 0) {
     return this.gen_binarySearchHelp_v2(arr, target, comp, midIdx + 1, highIdx);
   }
   else {
     return this.gen_binarySearchHelp_v2(arr, target, comp, lowIdx, midIdx);
   }
  }
  
  <T> void swap(ArrayList<T> arr, int index1, int index2) {
    T oldValueAtIndex2 = arr.get(index2);
   
    arr.set(index2, arr.get(index1));
    arr.set(index1, oldValueAtIndex2);
  }
  

 
  //EFFECT: Sorts the given list of strings alphabetically
  //For each value of idx starting at 0 and continuing while idx < arr.size(), 
  //advancing by 1, execute the body.”
  void sort(ArrayList<String> arr) {
    
   for (int idx = 0;                                   // (1)
        idx < arr.size();                              // (2)
        idx = idx + 1) {                               // (4)
      
     ArrayList<String> list = new ArrayList<String>(); 
     list.addAll(idx, arr); 
     
     int idxOfMinValue = getMin_v2(list); 
     this.swap(arr, idx, idxOfMinValue);
   }
  }
  
  int getMin_v2(ArrayList<String> arr) {
    
    int indexOfLeast = 0; 
    
    for (int idx = 0;                                   // (1)
         idx < arr.size();                              // (2)
         idx = idx + 1) {                               // (4)
      
      if (arr.get(indexOfLeast).compareTo(arr.get(idx)) >= 0)  { 
        indexOfLeast = idx; 
      }    
    }
   
    return indexOfLeast; 
  }
  
  
  
  <T> int getMin (ArrayList<String> arr) {
    return this.getMinHelper(arr, 0, 0, 0); 
  }
 
  <T> int getMinHelper (ArrayList<String> arr, int acc, int compIdx, int nextIdx) {
    if (nextIdx == arr.size()) {
      return acc; 
    }
    // if the current index is smaller than the second
    else if (arr.get(compIdx).compareTo(arr.get(nextIdx)) < 0) {
      // the acc becomes the compIdx, keep using our compIndx, and we will compare it to the next index
      return getMinHelper(arr, compIdx, compIdx, nextIdx + 1); 
    }
    else {
      // the acc becomes the nextIdx, the compIdx becomes that which we compared to, which we will compare to the next index
      return getMinHelper(arr, nextIdx, nextIdx, nextIdx + 1); 
      
    }
  }
  
  
  
}

class CompareStrings implements Comparator<String> {
  
  public int compare (String a, String b) {
    return a.compareTo(b); 
  }
 } 

class ExampleArrayLists {
  
  void testBinaraySearch(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    someStrings.add("apple"); 
    someStrings.add("cherry");
    someStrings.add("plum");
 
  }
  

  void testSort(Tester t) {
    ArrayList<String> unsortedList = new ArrayList<String> ();
    unsortedList.add("apple"); 
    unsortedList.add("cherry");
    unsortedList.add("plum");
    unsortedList.add("orange"); 
    
  }
  
  

}


