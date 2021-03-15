package lecture24;
import java.util.ArrayList;

public class ArrayUtils24 {
  
int getMin_v2(ArrayList<String> arr) {
    
    int indexOfLeast = 0; 
    
    for (int idx = 0;                                   
         idx < arr.size();                              
         idx = idx + 1) {                               
      
      if (arr.get(indexOfLeast).compareTo(arr.get(idx)) >= 0)  { 
        indexOfLeast = idx; 
      }    
    }
   
    return indexOfLeast; 
  }


  int getMin_v3(ArrayList<String> arr) {
    
    int indexOfLeast = 0;
    int idxVar = 0; 
    
    while (idxVar < arr.size()) {
      if (arr.get(indexOfLeast).compareTo(arr.get(idxVar)) >= 0)  { 
        
        indexOfLeast = idxVar; 
        idxVar = idxVar + 1; 
        
      }
    }
    return indexOfLeast;
     
  }
  
  boolean getsToOne(int n) {
    while (n > 1) {
      if (n % 2 == 0) {
        n = n / 2;
      }
      else {
        n = 3 * n + 1;
      }
    }
    return true;
  }
  
  
    
  
    
}
