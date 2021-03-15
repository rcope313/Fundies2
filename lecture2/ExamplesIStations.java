package lecture2;

public class ExamplesIStations {
  ExamplesIStations() {}
  IStation harvard = new TStop ("Harvard", "red", 1.25);
  IStation kenmore = new TStop ("Kenmore", "green", 1.25);
  IStation riverside = new TStop ("Riverside", "green", 2.50);
  
  IStation backbay = new CommStation ("Backbay", "Framingham", true);
  IStation wnewton = new CommStation ("West Newton", "Framingham", false);
  IStation wellhills = new CommStation ("Wellesly Hills", "Worcester", false);
  
  
  
  

}
