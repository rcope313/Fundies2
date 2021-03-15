package lab4;
import tester.*;


public interface IBook {
  
  int daysOverdue (int day); 
  
  boolean isOverdue (int day); 
  
  double computerFine (int day); 

}

abstract class ABook implements IBook {
  String title;
  int dayTaken;
  
  ABook (String title, int dayTaken) {
    this.title = title;
    this.dayTaken = dayTaken;
    
  }
  
  public int daysOverdue (int day) {
    if (day - this.dayTaken >= 0) {
      return 0; 
    }
    else {
      return (day - this.dayTaken) * -1; 
    }
  }
  
  public boolean isOverdue (int day) {
    return !(this.daysOverdue(day) == 0); 
  }
  
  public double computerFine (int day) {
    return this.daysOverdue(day) * .1; 
  }
  
}

class Book extends ABook {
  String author;
  
  Book (String title, String author, int dayTaken) {
    super (title, dayTaken);
    this.author = author; 
  }
  
}

class RefBook extends ABook {
  
  RefBook (String title, int dayTaken) {
    super (title, dayTaken); 
  }
} 

class AudioBook extends ABook {
  String author;

  
  AudioBook (String title, String author, int dayTaken) {
    super (title, dayTaken);
    this.author = author;  
    
  }
  
  public double computerFine(int day) {
    return this.daysOverdue(day) * .2; 
  }
}

class ExamplesBooks {
  ExamplesBooks () {} 
  
  IBook twilight = new Book ("Twilight", "Stephanie Meyer", 200); 
  IBook newMoon = new Book ("New Moon", "Stephanie Meyer", 220); 
  
  IBook twilightAudio = new AudioBook ("Twilight", "Stephanie Meyer", 200);
  IBook newMoonAudio = new Book ("New Moon", "Stephanie Meyer", 220); 
  
  IBook twilightSaga = new RefBook ("Twilight Saga", 300); 
  
  boolean testDaysOverdue (Tester t) {
    return
        t.checkExpect(twilight.daysOverdue(300), 0) &&
        t.checkExpect(twilight.daysOverdue(190), 10);  
  }
  
  boolean testIsOverdue (Tester t) {
    return
        t.checkExpect(twilight.isOverdue(300), false) &&
        t.checkExpect(twilight.isOverdue(190), true);  
  }
  
  boolean testComputerFine (Tester t) {
    return
        t.checkInexact(twilight.computerFine(300), 0.0, .01) &&
        t.checkInexact(twilight.computerFine(190), 1.0, .01) &&
        t.checkInexact(twilightAudio.computerFine(190), 2.0, .01); 
  }
}


