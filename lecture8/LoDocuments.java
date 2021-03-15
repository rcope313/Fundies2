package lecture8;
import tester.Tester;

public interface LoDocuments {
  
  // produce a bibliography containing just the authors and titles of the books you’ve found, 
  // either directly or transitively through the bibliographies of other documents
  String authorAndTitles (); 

}


class MtLoDocuments implements LoDocuments {
  
  //produce a bibliography containing just the authors and titles of the books you’ve found, 
  // either directly or transitively through the bibliographies of other documents
  public String authorAndTitles () {
      return ""; 
  }
  
  
}
  
class ConsLoDocuments implements LoDocuments {
  Documents first;
  LoDocuments rest;
  
  ConsLoDocuments (Documents first, LoDocuments rest) {
    this.first = first;
    this.rest = rest; 
  }
  
 /* Template
  * 
  *  Fields:
  *  first -- Documents
  *  rest -- LoDocuments
  *  
  *  Methods:
  *  this.authorAndTitles() -- String
  *  
  *  Methods on Fields:
  *  this.rest.authorAndTitles() -- String
  */
  
  public String authorAndTitles() {
    return this.first.authorAndTitlesHelp() + this.rest.authorAndTitles(); 
  }
 
}

interface Documents {
  
  // structures authors and titles as “Last name, First name. "Title".”
  public String authorAndTitlesHelp() ; 
  
}

class Book implements Documents {
  String authorFirst;
  String authorLast;
  String title;
  String publisher; 
  LoDocuments bib;
  
  Book(String authorFirst, String authorLast, String title, String publisher, LoDocuments bib) {
    this.authorFirst = authorFirst;
    this.authorLast = authorLast;
    this.title = title;
    this.publisher = publisher;
    this.bib = bib;
  }
  
  /* Template
   * 
   *  Fields:
   *  authorFirst -- String
   *  authorLast -- String 
   *  title -- String
   *  publisher -- STring
   *  bib -- LoDocuments 
   *  
   *  Methods:
   *  this.authorAndTitlesHelp() -- String
   *  
   *  
   *  Methods on Fields:
   *  this.bib.authorAndTitles -- String
   */
  
 
  public String authorAndTitlesHelp() {
    return 
        this.authorFirst
        + ", "
          + this.authorLast
        + ". '"
        + this.title
        + "'. "; 
    
  }
}

class Wiki implements Documents {
  String authorFirst;
  String authorLast;
  String title;
  String url;
  LoDocuments bib;
  
  Wiki(String authorFirst, String authorLast, String title, String url, LoDocuments bib) {
    this.authorFirst = authorFirst;
    this.authorLast = authorLast;
    this.title = title;
    this.url = url;
    this.bib = bib;
  }
  
  public String authorAndTitlesHelp() {
    return this.bib.authorAndTitles(); 
  }
}

class ExamplesLoDocuments {
  ExamplesLoDocuments() {}
  
  Documents wiki1 = new Wiki ("Rachel", "Cope", "Coffee", "wiki.com/coffee", this.list4); 
  Documents wiki2 = new Wiki ("Alex", "Cope", "Tea is Great", "wiki.com/teaisgreat", this.list4);  
  Documents book1 = new Book ("Kyle", "Alpert", "Coffee is King", "Kyky Publishing", this.empty); 
  Documents book2 = new Book ("Alex", "Coblin", "Tea is Yum", "Penguin Books", this.empty); 
  
  LoDocuments empty = new MtLoDocuments (); 
  LoDocuments list1 = new ConsLoDocuments (this.wiki1, this.empty); 
  LoDocuments list2 = new ConsLoDocuments (this.wiki2, this.list1); 
  LoDocuments list3 = new ConsLoDocuments (this.book1, this.list2); 
  LoDocuments list4 = new ConsLoDocuments (this.book2, this.empty); 
  
  boolean testAuthorAndTiles(Tester t) {
    return
        t.checkExpect(this.empty.authorAndTitles(), "") &&
        t.checkExpect(this.list1.authorAndTitles(), "Alex, Coblin. 'Tea is Yum'.");    
  }
    
    
}
  


  
  

