package lecture5;

public interface ILoBook {
  
  // count the books in this list
  int count ();
  
  // to produce a list of all books published before the given date
  // from this list of books
  ILoBook allBefore(int year);
  
  // calculate the total sale price of all books in this list or a given discount
  double salePrice(int discount);
  
  // produce a list of all books in this list, sorted by their price 
  ILoBook sortByPrice(); 
  
  //insert the given book into this list of books
  //already sorted by price
  ILoBook insert(Book b);
}

class MtLoBook implements ILoBook {
  
  public int count() {return 0;} 
  
  public ILoBook allBefore(int year) {return this;}
  
  public double salePrice(int discount) {return 0;}
  
  public ILoBook sortByPrice() {return this;}
  
  public ILoBook insert(Book b) {
    return new ConsLoBook(b, this);
  }
  
  
}

class ConsLoBook implements ILoBook {
  Book first;
  ILoBook rest; 
  
  ConsLoBook(Book first, ILoBook rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /*
  TEMPLATE:
  ---------
  Fields:
  ... this.first ...                             -- Book
  ... this.rest ...                              -- ILoBook
  Methods:
  ... this.count() ...                           -- int
  ... this.salePrice(int discount) ...           -- double
  ... this.allBefore(int year) ...               -- ILoBook
  Methods for Fields:
  ... this.rest.count() ...                      -- int
  ... this.rest.salePrice(int discount) ...      -- double
  ... this.rest.allBefore(int year) ...          -- ILoBook
  */

  
  public int count() {return 1 + this.rest.count();} 
  
  public ILoBook allBefore(int year) { 
  if (this.first.publishedBefore(year)) {
    return new ConsLoBook (this.first, this.rest.allBefore(year));
  }
  else {
    return this.rest.allBefore(year);
    
  }
  }
 
  public double salePrice(int discount) 
  {return this.first.salePrice(discount)
      + this.rest.salePrice(discount);}
  
  public ILoBook sortByPrice() {
    return this.rest.sortByPrice()  // sort the rest of the list...
              .insert(this.first); // and insert the first book into that result
 }
  

  //insert the given book into this list of books
  //already sorted by price
  public ILoBook insert(Book b) {
    if (this.first.cheaperThan(b)) {
      return new ConsLoBook(this.first, this.rest.insert(b));
    }
    else {
      return new ConsLoBook(b, this);
    }
  }  
  
  
  
  }

class Book {
  String title;
  String author;
  int year;
  double price;
  
  Book (String title, String author, int year, double price) {
    this.title = title;
    this.author = author;
    this.year = year;
    this.price = price;
    
  }
  
  double salePrice (int discount) 
  {return this.price * discount;}
  
  //was this book published before the given year?
  boolean publishedBefore(int year)
  {return this.year < year;}
  
  boolean cheaperThan(Book that)
  {return this.price < that.price;}
  
}
  
    


class ExamplesBooks {
  ExamplesBooks () {}
  
//Books
Book htdp = new Book("HtDP", "MF", 2001, 60);
Book lpp = new Book("LPP", "STX", 1942, 25);
Book ll = new Book("LL", "FF", 1986, 10);
 
// lists of Books
ILoBook mtlist = new MtLoBook();
ILoBook lista = new ConsLoBook(this.lpp, this.mtlist);
ILoBook listb = new ConsLoBook(this.htdp, this.mtlist);
ILoBook listc = new ConsLoBook(this.lpp,
                new ConsLoBook(this.ll, this.listb));
ILoBook listd = new ConsLoBook(this.ll,
                  new ConsLoBook(this.lpp,
                    new ConsLoBook(this.htdp, this.mtlist)));

  
  
  }
 
