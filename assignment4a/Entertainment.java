package assignment4a;
import tester.Tester;

interface IEntertainment {
    //compute the total price of this Entertainment
    double totalPrice();
    //computes the minutes of entertainment of this IEntertainment
    int duration();
    //produce a String that shows the name and price of this IEntertainment
    String format();
    //is this IEntertainment the same as that one?
    boolean sameEntertainment(IEntertainment that);
    //is this magazine the same as that magazine?
    boolean sameMagazine (Magazine that); 
    //is this tv series the same as that tv series?
    boolean sameTVSeries (TVSeries that);
    //is this podcast the same as that podcast?
    boolean samePodcast (Podcast that); 
    
}

abstract class AEntertainments implements IEntertainment {
  String name;
  double price;
  int installments;
  
  AEntertainments (String name, double price, int installments) {
    this.name = name;
    this.price = price;
    this.installments = installments; 
  }
  
  //compute the total price of this Entertainment
  public double totalPrice() {
    return this.price * this.installments;
  }
  
  //computes the minutes of entertainment of this IEntertainment
  public int duration() {
      return 50 * this.installments;
  }
  //produce a String that shows the name and price of this IEntertainment
  public String format() {
    return this.name
          + ", " 
          + String.valueOf(this.price) 
          + ".";
  }
  
  //is this IEntertainment the same as that one?
  public abstract boolean sameEntertainment(IEntertainment that);
  //is this magazine the same as that magazine?
  public boolean sameMagazine (Magazine that) {
    return false;
  }
  //is this tv series the same as that tv series?
  public boolean sameTVSeries (TVSeries that) {
    return false; 
  }
  //is this podcast the same as that podcast?
  public boolean samePodcast (Podcast that) {
    return false; 
  }
  
}

class Magazine extends AEntertainments {
    String genre;
    int pages;
    
    
    Magazine(String name, double price, String genre, int pages, int installments) {
        super (name, price, installments); 
        this.genre = genre;
        this.pages = pages;
  
    }
    
    
    //computes the minutes of entertainment of this Magazine, (includes all installments)
    public int duration() {
        return 5 * this.pages; 
    }    
    
    //is this IEntertainment the same as that one?
    public boolean sameEntertainment(IEntertainment that) {
      return that.sameMagazine(this); 
    }
    
    //is this magazine the same as that magazine?
    public boolean sameMagazine (Magazine that) {
      return this.name == that.name &&
          this.price == that.price &&
          this.genre == that.genre &&
          this.pages == that.pages &&
          this.installments == that.installments; 
   
    }
      
}

class TVSeries extends AEntertainments {
    String corporation;
    
    TVSeries(String name, double price, int installments, String corporation) {
        super (name, price, installments); 
        this.corporation = corporation;
    }
   
    
  //is this IEntertainment the same as that one?
    public boolean sameEntertainment(IEntertainment that) {
      return that.sameTVSeries(this); 
    }
    
  
    //is this tv series the same as that tv series?
    public boolean sameTVSeries (TVSeries that) {
      return this.name == that.name &&
          this.price == that.price &&
          this.installments == that.installments &&
          this.corporation == that.corporation; 
    }
    
    
    
    
}

class Podcast extends AEntertainments {
    
    Podcast(String name, double price, int installments) {
        super (name, price, installments); 
    }
   
    
  //is this IEntertainment the same as that one?
    public boolean sameEntertainment(IEntertainment that) {
      return that.samePodcast(this); 
    }
    
    //is this podcast the same as that podcast?
    public boolean samePodcast (Podcast that) {
      return this.name == that.name &&
          this.price == that.price &&
          this.installments == that.installments; 
    }
    
}

class ExamplesEntertainment {
    IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
    IEntertainment rockAndIce = new Magazine ("Rock and Ice", 3.99, "Sport", 100, 2);
    
    IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
    IEntertainment fleaBag = new TVSeries ("Fleabag", 2.99, 12, "Amazon Prime"); 
    
    IEntertainment serial = new Podcast("Serial", 0.0, 8);
    IEntertainment theDaily = new Podcast ("The Daily", 0.0, 100); 
    
    
    //testing total price method
    boolean testTotalPrice(Tester t) {
        return t.checkInexact(this.rollingStone.totalPrice(), 2.55*12, .0001) 
        && t.checkInexact(this.houseOfCards.totalPrice(), 5.25*13, .0001)
        && t.checkInexact(this.serial.totalPrice(), 0.0, .0001)
        && t.checkInexact(this.rockAndIce.totalPrice(), 3.99*2, .0001)
        && t.checkInexact(this.fleaBag.totalPrice(), 2.99*12, .0001)
        && t.checkInexact(this.theDaily.totalPrice(), 0.0, .0001); 
    }
    
  //testing duration method
    boolean testDuration(Tester t) {
        return t.checkExpect(this.rollingStone.duration(), 5*60) 
        && t.checkExpect(this.houseOfCards.duration(), 50*13)
        && t.checkExpect(this.serial.duration(), 50*8)
        && t.checkExpect(this.rockAndIce.duration(), 5*100)
        && t.checkExpect(this.fleaBag.duration(), 50*12)
        && t.checkExpect(this.theDaily.duration(), 50*100); 
    }
    
  //testing format method
    boolean testFormat(Tester t) {
        return t.checkExpect(this.rollingStone.format(), "Rolling Stone, 2.55.") 
        && t.checkExpect(this.houseOfCards.format(), "House of Cards, 5.25.")
        && t.checkExpect(this.serial.format(), "Serial, 0.0.")
        && t.checkExpect(this.rockAndIce.format(), "Rock and Ice, 3.99.")
        && t.checkExpect(this.fleaBag.format(), "Fleabag, 2.99.")
        && t.checkExpect(this.theDaily.format(), "The Daily, 0.0.");
    }
    
  //testing the same entertainment method
    boolean testSameEntertainments(Tester t) {
      return t.checkExpect (rollingStone.sameEntertainment(rollingStone), true) 
          && t.checkExpect (rollingStone.sameEntertainment(rockAndIce), false)
          && t.checkExpect (rollingStone.sameEntertainment(fleaBag), false)
          && t.checkExpect (theDaily.sameEntertainment(fleaBag), false)
          && t.checkExpect (theDaily.sameEntertainment(theDaily), true); 
              
          
      
    }
    
}
