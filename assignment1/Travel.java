package assignment1;

public interface Travel {

}

class Horse implements Travel {
  String name;
  String color;
  Housing from;
  Housing to;
  
  Horse (String name, String color, Housing from, Housing to) {
   this.name = name;
   this.color = color;
   this.from = from;
   this.to = to;
    
  } 
}

class Carriage implements Travel {
  int tonage; 
  Housing from;
  Housing to;
  
  Carriage (int tonage, Housing from, Housing to) {
   this.tonage = tonage;
   this.from = from;
   this.to = to;
  
  }
}

interface Housing {
  
}

class Hut implements Housing {
  int capacity;
  int population;
  
  Hut (int capacity, int population) {
    this.capacity = capacity;
    this.population = population;
    
  }
}

class Inn implements Housing {
  String name;
  int capacity;
  int population;
  int stalls;
  
  Inn (String name, int capacity, int population, int stalls) {
    this.name = name;
    this.capacity = capacity;
    this.population = population;
    this.stalls = stalls;
    
  }
}

class Castle implements Housing {
  String name;
  String familyname;
  int population;
  int carriagehouse;
  
  Castle (String name, String familyname, int population, int carriagehouse) {
    this.name = name;
    this.familyname = familyname;
    this.population = population;
    this.carriagehouse = carriagehouse;
    
  }
}

class ExamplesTravel {
  ExamplesTravel () {}
  
  Housing hovel = new Hut (5,1);
  Housing copehouse = new Hut (12,11);
  Housing winterfell = new Castle ("Winterfell", "Stark", 500, 6);
  Housing kingsLanding = new Castle ("Kings Landing", "Lanniser", 200, 20);
  Housing crossroads = new Inn ("Inn At The Crossroads", 40, 20, 12);
  Housing rachieInn = new Inn ("Rachie Inn", 12, 6, 1);
   
  
  Travel horse1 = new Horse ("Kyky", "Brown", this.hovel, this.winterfell); 
  Travel horse2 = new Horse ("Kyky2", "Spotted", this.copehouse, this.kingsLanding);
  Travel carriage1 = new Carriage (5, this.crossroads, this.winterfell);
  Travel carriage2 = new Carriage (10, this.rachieInn, this.kingsLanding);
      
       
}


