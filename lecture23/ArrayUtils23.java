package lecture23;
import java.util.ArrayList;
import java.util.function.Function;

import tester.*;

public class ArrayUtils23 {
  
  <U> ArrayList<U> buildList(int n, Function<Integer, U> func) {
    ArrayList<U> result = new ArrayList<U>();
    for (int i = 0; i < n; i = i + 1) {
      result.add(func.apply(i));
    }
    return result;
  }

  //In ArrayUtils
  //EFFECT: Modifies all the books in the given ArrayList, to capitalize their titles
  void capitalizeTitles(ArrayList<Book> books) {
   for (Book b : books) {
     b.capitalizeTitle();
   }
  }
  
  
}

class Book {
  String title;
  
  Book (String title) {
    this.title = title;
    
  }
  
  void capitalizeTitle() {
    this.title = this.title.toUpperCase();
   }
}

class ExamplesBooks {
  
  Book TFTNA, TFTUA, RCTM; 
  
  void initdata() {
    
    TFTNA = new Book ("Training for the New Alpinism");
    TFTUA = new Book ("Training for the Uphill Athlete");
    RCTM = new Book ("Rock Climbers' Training Manual"); 
          
  }
  
  void testCapitalizeTitles(Tester t) {
    
    this.initdata();
    ArrayList<Book> bookList = new ArrayList<Book>();
    bookList.add(TFTNA);
    bookList.add(TFTUA);
    bookList.add(RCTM);
    new ArrayUtils23().capitalizeTitles(bookList);
    t.checkExpect(bookList.get(0).title, "TRAINING FOR THE NEW ALPINISM");
    t.checkExpect(this.TFTNA.title, "TRAINING FOR THE NEW ALPINISM"); 
   

    
    
  }
      
  
  
}
