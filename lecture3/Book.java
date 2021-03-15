package lecture3;

import tester.Tester;

public class Book {
  String title;
  Author author;
  int price;
  
  Book (String title, Author author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
     
  }

  /* TEMPLATE:
  Fields:
  ... this.title ...        -- String
  ... this.author ...       -- Author
  ... this.price ...        -- int

  Methods:
  ... this.salePrice(int) ...   -- int
  ... this.sameAuthor(Book) ... -- boolean

  Methods for fields:
  ... this.author.sameAuthor(Book) ... --boolean
  
  Fields of parameters:
  ... that.title ...        -- String
  ... that.author ...       -- Author
  ... that.price ...        -- int
 
  Methods on parameters:
  ... that.salePrice(int) ...   -- int
  ... that.author.sameAuthor(Book) ... -- boolean
*/


int salePrice(int discount) {
  return this.price - (this.price * discount) / 100;
}

boolean sameAuthor (Book that) {
  return this.author.sameAuthor(that.author);
}

Book reducedPrice () {
  return new Book (this.title, this.author, this.salePrice(20));
}
}



class Author {
  String name;
  int yob;
  
  Author (String name, int yob) {
    this.name = name;
    this.yob = yob;
  }  
  
  /* TEMPLATE:
  Fields:
  ... this.name ...      -- String
  ... this.yob ...       -- int


  Methods:
  ... this.sameAuthor(Author ...  -- boolean

  Methods for fields:
  ... this.author.mmm(??) ...    -- ??
*/
  
boolean sameAuthor(Author that) {
  return this.name.equals(that.name) &&
      this.yob == that.yob;
  }  
}

// examples and tests for the classes that represent books and authors
class ExamplesBooksAuthors {
  ExamplesBooksAuthors () {}
  
  // examples of authors 
  Author pat = new Author ("Pat Conroy", 1948);
  Author dan = new Author ("Dan Brown", 1962);
  Author rachel = new Author ("Rachel", 1994);
  Author kyle = new Author ("Kyle", 1994);
  
  
  // examples of books
  Book beaches = new Book ("Beaches", this.pat, 20);
  Book prince = new Book ("Prince of Tides", this.pat, 15);
  Book code = new Book ("Da Vinci Code", this.dan, 20);
  Book htdp = new Book ("HTDP", this.rachel, 60);
  Book ror = new Book ("Realm of Racket", this.kyle, 20);
  
//to test the methods
 boolean testSalePrice (Tester t) {
   return t.checkExpect(this.htdp.salePrice(30), 42)
       && t.checkExpect(this.ror.salePrice(20), 16);   
}
 boolean testSameAuthor(Tester t) {
   return t.checkExpect(
           this.pat.sameAuthor(new Author("Pat Conroy", 1948)),
           true)
      && t.checkExpect(this.pat.sameAuthor(this.dan), false);
 }
 boolean testSameBookAuthor(Tester t) {
   return t.checkExpect(this.beaches.sameAuthor(this.prince), true)
       && t.checkExpect(this.beaches.sameAuthor(this.code), false);
 }
 
 boolean testReducedPrice(Tester t) {
   return t.checkExpect(this.htdp.reducedPrice(),
                        new Book("HtDP", this.pat, 48))
      && t.checkExpect(this.beaches.reducedPrice(),
                       new Book("Beaches", this.pat, 16));
 }
 }



      






