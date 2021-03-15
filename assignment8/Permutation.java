package assignment8; 
import java.util.*;

import tester.Tester;


/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class Permutation {
    // The original list of characters to be encoded
    ArrayList<Character> alphabet = 
        new ArrayList<Character>(Arrays.asList(
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
                    't', 'u', 'v', 'w', 'x', 'y', 'z'));

    ArrayList<Character> code = new ArrayList<Character>(26); 

    // A random number generator
    Random rand = new Random();

    // Create a new instance of the encoder/decoder with a new permutation code 
    // i.e i need to create the code using this method 
    Permutation() {
        this.code = this.initEncoder();
    }

    // Create a new instance of the encoder/decoder with the given code 
    // i.e i've already created the code
    Permutation(ArrayList<Character> code) {
        this.code = code;
    }

    // Initialize the encoding permutation of the characters
    ArrayList<Character> initEncoder() {
      
      ArrayList<Integer> encoderInteger = this.initEncoderHelp();
      ArrayList<Character> result = new ArrayList<Character>();
      
      for (int idx = 0;                                   
          idx < encoderInteger.size();                              
          idx = idx + 1) {
        
       char newChar = this.alphabet.get(encoderInteger.get(idx)); 
       result.add(newChar); 
      }
      
      return result; 
        
    }
    
    ArrayList<Integer> initEncoderHelp() {
        
      ArrayList<Integer> result = new ArrayList<Integer>(); 
      ArrayList<Integer> numbersAssigned = new ArrayList<Integer>(); 
      int idxVar = 0; 
      
      while (idxVar < 26) {                                  

        int randomInt = this.rand.nextInt(26); 
        
        if (!new ArrayUtils().isInList(numbersAssigned, randomInt)) {
          
          result.add(randomInt); 
          numbersAssigned.add(randomInt); 
          idxVar = idxVar + 1; 
          } 
      }
      
      return result; 
   
    }
      
      
    

    // produce an encoded String from the given String
    String encode(String source) {
    
      ArrayList<Character> ListEncodingFrom = 
          new ArrayUtils().string2ArrayListCharacter(source); 
      ArrayList<Character> result = new ArrayList<Character>(); 
      
      for (int idx = 0;                                   
          idx < ListEncodingFrom.size();                              
          idx = idx + 1) { 
        
        int comparator = new ArrayUtils().find
            (this.alphabet, ListEncodingFrom.get(idx)); 
        result.add(this.code.get(comparator)); 
 
      }   
      return new ArrayUtils().arrayListCharacter2String(result); 
    }
    
    // produce a decoded String from the given String
    String decode(String code) {
      
      ArrayList<Character> ListDecodingFrom = 
          new ArrayUtils().string2ArrayListCharacter(code); 
      ArrayList<Character> result = new ArrayList<Character>(); 
      
      for (int idx = 0;                                   
          idx < ListDecodingFrom.size();                              
          idx = idx + 1) { 
        
        int comparator = new ArrayUtils().find
            (this.code, ListDecodingFrom.get(idx)); 
        result.add(this.alphabet.get(comparator)); 
 
      }   
      return new ArrayUtils().arrayListCharacter2String(result); 
    }
    
    
}

class ArrayUtils {
  
  boolean isInList (ArrayList<Integer> arr, int x) {
      
      for (int idx = 0;                                   
          idx < arr.size();                              
          idx = idx + 1) {
        
        if (arr.get(idx) == x) {
          return true; 
        }
      }
      return false; 
  }
  
  
  <T> int find (ArrayList<T> arr, T t) {
    
    int result = -1; 
    
    for (int idx = 0;                                   
        idx < arr.size();                              
        idx = idx + 1) {
      
      if (arr.get(idx).equals(t)) {
        result = idx; 
        }
      }
      
      return result; 
    
    }
  
  
  ArrayList<Character> string2ArrayListCharacter (String str) {
    
    ArrayList<Character> chars = new ArrayList<Character>(str.length());
    
    for (int idx = 0;                                   
        idx < str.length();                              
        idx = idx + 1) { 
      
      chars.add(str.charAt(idx)); 
    }
    
    return chars; 
    
  } 
  
  String arrayListCharacter2String (ArrayList<Character> arr) {
   
    String result = ""; 
    
    for (int idx = 0;                                   
        idx < arr.size();                               
        idx = idx + 1) { 
      
      result = result.concat(arr.get(idx).toString()); 
    }
    
    return result; 
  }
  
}

class ExamplesPermutationCodes {
  
  ArrayList<Character> testCode = 
      new ArrayList<Character>(Arrays.asList(
      'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 
      'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
      'u', 'v', 'w', 'x', 'y', 'z', 'a'));
  Permutation testPermuationCode = new Permutation(this.testCode); 
  Permutation testInit = new Permutation(); 
  
  
  void testDecodeAndEncode (Tester t) {
    t.checkExpect(new ArrayUtils().string2ArrayListCharacter("apple"),
                  new ArrayList<Character>(Arrays.asList(
                      'a', 'p', 'p', 'l', 'e'))); 
    t.checkExpect(new ArrayUtils().arrayListCharacter2String(
                  new ArrayList<Character>(Arrays.asList(
                      'a', 'p', 'p', 'l', 'e'))), 
       "apple"); 
    t.checkExpect(new ArrayUtils().find(testCode, 'c'), 1); 
    t.checkExpect(this.testPermuationCode.decode("bqqmf"), "apple"); 
    t.checkExpect(this.testPermuationCode.encode("apple"), "bqqmf");

  }
  
  void testinitEncoder (Tester t) {
    t.checkExpect(new ArrayUtils().isInList(
        new ArrayList<Integer>(Arrays.asList(1,2,3,4)), 2), true);
    t.checkExpect(new ArrayUtils().isInList(
        new ArrayList<Integer>(Arrays.asList(1,2,3,4)), 5), false);
    t.checkExpect(this.testPermuationCode.initEncoderHelp(), 
        new ArrayList<Integer>(26)); 
    t.checkExpect(this.testInit.encode("apple"), "effgbx"); 
    
   
    
         
  }
  
}
