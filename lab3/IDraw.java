package lab3;
import tester.Tester;

public interface IDraw {
  
  //computes and returns a list of all player names that this player has won against
  //in the order in which they played this player
  ILoString wonAgainst ();
 
  
  // return the name of the loser
  String loser (); 
  
  // return the name of the loser (helper)
  String loserHelper (); 
  
  // returns the name of the player who eliminated a player whose name is the argument, in this draw.
  // If this player won the draw then it should return "Won".
  // If there is no player with the given name, it should return "Not Found". 
  String nemesis(String player); 

  
  // finds the person who defeated the that player
  String nemesisHelper(String player, String playerBeater);


  String winner(); 
  

}

class SeedPlayer implements IDraw {
  String name;
  
  SeedPlayer (String name) {
    this.name =name; 
  }
  
  /* Template
   * 
   * Fields:
   * name -- string
   * 
   * Methods
   * wonAgainst -- ILoString
   * loser -- String
   * loserHelper -- String
   * nemesis -- String
   * realPlayer -- boolean
   * 
   * Methods on Fields:
   
   * 
   */
  
  public ILoString wonAgainst () {
    return new MtLoString (); 
  }
  
  public String loser () {
    return this.name; 
  }
  
  public String loserHelper () {
    return this.name; 
  }
  
  public String winner() {
    return this.name;
  }
  
  public String nemesis(String player) {
    return "Not Found"; 
  }
  
  public String nemesisHelper(String player, String playerBeater) {
    if (player.equals(this.name)) {
      return playerBeater;
    } else {
      return "Not Found";
    }
  }
  
 }
  


class PlayResult implements IDraw {
  String winner;
  IDraw one;
  IDraw two;
  
  PlayResult(String winner, IDraw one, IDraw two) {
    this.winner = winner;
    this.one = one;
    this.two = two;
  } 
  
  /* Template
   * 
   * Fields:
   * winner - String
   * one - IDraw
   * two - IDraw
   * 
   * Methods
   * wonAgainst -- ILoString
   * loser -- String
   * loserHelper -- String
   * nemesis -- String
   * 
   * Methods on Fields:
   * this.one.wonAgainst
   * this.two.wonAgainst
   * this.one.loser
   * this.two.loser
   * this.one.loserHelper
   * this.two.loserHelper
   * this.one.nemesis
   * this.two.nemesis
   * 
   */
  
  public ILoString wonAgainst () {
    return new ConsLoString 
        (this.loser(), this.one.wonAgainst()); 
  
  }
  
  public String loser () {
    return this.two.loserHelper (); 
  }
  
  public String loserHelper() {
    return this.winner; 
  }
 
  public String nemesis(String player) {
    if (this.winner.equals(player)) {
      return "won";
    }
    return this.nemesisHelper(player, this.winner);
  }
  
 
  public String nemesisHelper(String player, String playerBeater) {
    if (this.two.winner().equals(player)) {
      return this.winner;
    }
    else if (!this.one.nemesisHelper(player, this.winner).equals("Not Found")) {
      return this.one.nemesisHelper(player, this.winner);
    }
    else if (!this.two.nemesisHelper(player, this.winner).equals("Not Found")) {
      return this.two.nemesisHelper(player, this.winner);
    }
    else {
      return "Not Found";
    }
  }
  
  public String winner() {
    return this.winner;
  }
  
  
}

class ExamplesIDraws {
  ExamplesIDraws () {} 
  
  IDraw djokovic = new SeedPlayer("Djokovic");
  IDraw goffin = new SeedPlayer("Goffin");
  IDraw pella = new SeedPlayer("Pella");
  IDraw agut = new SeedPlayer("Agut");
  IDraw querrey = new SeedPlayer("Querrey"); 
  IDraw nadal = new SeedPlayer("Nadal"); 
  IDraw nishikori = new SeedPlayer("Nishikori"); 
  IDraw federer = new SeedPlayer("Federer"); 
  
  IDraw quarterFinal1 = new PlayResult("Djokovic",djokovic,goffin);
  IDraw quarterFinal2 = new PlayResult("Agut",agut,pella);
  IDraw quarterFinal3 = new PlayResult("Nadal",nadal,querrey);
  IDraw quarterFinal4 = new PlayResult("Federer",federer,nishikori); 
  
  IDraw semifinal1 = new PlayResult("Djokovic", quarterFinal1,quarterFinal2);
  IDraw semifinal2 = new PlayResult("Federer", quarterFinal4, quarterFinal3);
  
  IDraw finals = new PlayResult("Djokovic",semifinal1,semifinal2);
  
//  boolean testWonAgainst (Tester t) {
//    return
//        t.checkExpect(finals.wonAgainst(), 
//           "Goffin, Agut, Federer") &&
//        t.checkExpect(goffin.wonAgainst(), new MtLoString()); 
//  }
  
  boolean testNemesis (Tester t) {
    return
        t.checkExpect(finals.nemesis("Djokovic"), "won") &&
        t.checkExpect(finals.nemesis("Nadal"), "Federer") &&
        t.checkExpect(finals.nemesis("Nishikori"), "Federer") &&
        t.checkExpect(finals.nemesis("Goffin"),  "Djokovic") && 
        t.checkExpect(finals.nemesis("Rachie"), "Not Found"); 
  }
}  


interface ILoString {
 
}


class ConsLoString implements ILoString {
  String first;
  ILoString last;
  
  ConsLoString (String first, ILoString last) {
    this.first = first;
    this.last = last;
    
  }
  
  /* Template
   * 
   * Fields:
   * first -- String
   * last -- ILoString
   *
   */
 
}

class MtLoString implements ILoString {
  MtLoString () {}
  
}