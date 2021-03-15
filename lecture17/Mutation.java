package lecture17;
import lists.ConsList;
import lists.IList;
import lists.MtList;
import tester.Tester;

//Represents authors of books
class Author {
  String first;
  String last;
  int yob;
  IList<Book> books;

  Author(String fst, String lst, int yob, IList<Book> bk) {
    this.first = fst;
    this.last = lst;
    this.yob = yob;
    this.books = new MtList<Book>(); 
  }
  
  boolean sameAuthor (Author that) {
    return this.first == that.first &&
           this.last == that.last &&
           this.yob == that.yob &&
           this.books == that.books;
  }
  
  // EFFECT: modifies the author's book field to refer to the given book
  void addBook (Book b) {
    if (!b.author.sameAuthor(this)) {
      throw new RuntimeException("book was not written by this author");
    }
    this.books = new ConsList<Book> (b, this.books); 
   
  }
  
}

//Represent books
class Book {
  String title;
  int price;
  int quantity;
  Author author;
  
  Book(String title, int price, int quantity, Author ath) {
    this.title = title;
    this.price = price;
    this.quantity = quantity;
    this.author = ath;
 // NEW! Fix up the author for us, using *this* newly-constructed Book
    this.author.addBook(this); // to the author's book field, add this book 
  }
}

class ExamplesBooks {
  
   Author house;
   Book TFTNA, TFTUA; 
  
   // EFFECT: Sets up the initial conditions for our tests, by re-initializing
   // house, TFTNA, TFTUA
   void initTestConditions() {
     this.house = new Author ("Steve", "House", 1970, null);
     this.TFTNA = new Book ("Training for the New Alpinsim", 35, 1, this.house);
     this.TFTUA = new Book ("Training for the Uphill Athlete", 35, 1, this.house);

   }
   
   // In ExampleBooks
   void testBookAuthors(Tester t) {
     this.initTestConditions();
     t.checkExpect(this.house.books, new ConsList<Book> (TFTUA, 
                                     new ConsList<Book> (TFTNA, 
                                     new MtList<Book>()))); 

   }
   
}
    
  
