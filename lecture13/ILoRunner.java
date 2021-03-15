package lecture13;
import tester.Tester; 

public interface ILoRunner {
  
  ILoRunner find (IPred<Runner> pred); 
  
  ILoRunner sortBy (IRunnerComparator comp); 
  
  ILoRunner insertBy (IRunnerComparator comp, Runner r); 
  
  Runner findWinner(); 
  
  Runner getFirst(); 
  
  Runner findMin(IRunnerComparator comp); 
  
  // returns the minimum Runner of the given accumulator and every 
  // Runner on this list, according to the given comparator 
  Runner findMinHelp(IRunnerComparator comp, Runner acc);
  
}

class MTLoRunner implements ILoRunner {
  
  public ILoRunner find (IPred<Runner> pred) {
    return this; 
  }
  
  public ILoRunner sortBy (IRunnerComparator comp) {
    return this; 
  }
  
  public ILoRunner insertBy (IRunnerComparator  comp, Runner r) {
    return new ConsLoRunner (r, this); 
  }
  
  public Runner findWinner() {
    throw new RuntimeException("No winner of an empty list of Runners");
  }
  
  public Runner getFirst() {
    throw new RuntimeException("No winner of an empty list of Runners"); 
  }
  
  public Runner findMin(IRunnerComparator comp) {
    throw new RuntimeException("No minimum time of an empty list of Runners");
  }
  
  public Runner findMinHelp(IRunnerComparator comp, Runner acc) {
    return acc; 
  }
  
}

class ConsLoRunner implements ILoRunner {
  Runner first;
  ILoRunner rest;
  
  ConsLoRunner (Runner first, ILoRunner rest) {
    this.first = first;
    this.rest = rest;   
  }
  
  public ILoRunner find (IPred<Runner> pred) {
    if (pred.apply(this.first)) {
      return new ConsLoRunner(this.first, this.rest.find(pred));
    }
    else {
      return this.rest.find(pred); 
    } 
  }
  
  public ILoRunner sortBy (IRunnerComparator comp) {
    return this.rest.sortBy(comp)
                    .insertBy(comp, this.first); 
  }
  
  public ILoRunner insertBy (IRunnerComparator comp, Runner r) {
    if (comp.compare(this.first, r) > 0) {
      return new ConsLoRunner (this.first, this.rest.insertBy(comp, r));
    }
    else {
      return new ConsLoRunner (r, this); 
    }
  }
  
  public Runner findWinner () {
    return this.findMin(new CompareByTime()); 
  }
  
  public Runner getFirst () {
     return this.first; 
  }
  
  public Runner findMin (IRunnerComparator comp) {
    return this.rest.findMinHelp(comp, this.first); 
  }
  
  public Runner findMinHelp (IRunnerComparator comp, Runner acc) {
    if (comp.compare(this.first, acc) > 0) {
      return this.rest.findMinHelp(comp, acc); 
    }
    else {
      return this.rest.findMinHelp(comp, this.first); 
    }
      
      
  }
  
  
}

class Runner {
  String name;   
  int age;       
  int bib;      
  boolean isMale;
  int pos;       
  int time;
  
  Runner (String name, int age, int bib, boolean isMale, int pos, int time) {
    this.name = name;
    this.age = age;
    this.bib = bib;
    this.isMale = isMale;
    this.pos = pos;
    this.time = time;
  }  
}

interface IRunnerComparator {
  // returns a negative number if r1 comes before r2 in this order (as in r1 wins)
  // returns zero if r1 is tied with r2 
  // return a positive number if r1 comes after r2 in this order (as in r1 loses)
  int compare (Runner r1, Runner r2); 
  
}


class CompareByTime implements IRunnerComparator {
  
  public int compare (Runner r1, Runner r2) {
    return r1.time - r2.time; 
      
    
  }
}

interface IPred<T> {
  boolean apply (T t); 
}


class AndPredicate implements IPred<Runner> {
  IPred<Runner> left;
  IPred<Runner> right;
  
  AndPredicate (IPred<Runner> left, IPred<Runner> right) {
    this.left = left;
    this.right = right; 
  }
  
  public boolean apply (Runner r) {
    return this.left.apply(r) && 
           this.right.apply(r); 
  }
}

class OrPredicate implements IPred<Runner> {
  IPred<Runner> left;
  IPred<Runner> right;
  
  OrPredicate (IPred<Runner> left, IPred<Runner> right) {
    this.left = left;
    this.right = right;
    
  }
  
  public boolean apply (Runner r) {
    return this.left.apply(r) ||
           this.right.apply(r); 
  }
}

class RunnerIsMale implements IPred<Runner> {
  public boolean apply(Runner r) { return r.isMale; }
}

class RunnerIsFemale implements IPred<Runner> {
  public boolean apply(Runner r) { return !r.isMale; }
}

class RunnerIsYounger40 implements IPred<Runner> {
  public boolean apply(Runner r) {return r.age < 40; }
}

class RunnerIsInFirst50 implements IPred<Runner> {
  public boolean apply(Runner r) { return r.pos <= 50; }
}


class ExamplesRunner {

  Runner johnny = new Runner("Kelly", 97, 999, true, 30, 360);
  Runner frank  = new Runner("Shorter", 32, 888, true, 245, 130);
  Runner bill = new Runner("Rogers", 36, 777, true, 119, 129);
  Runner joan = new Runner("Benoit", 29, 444, false, 18, 155);

  ILoRunner mtlist = new MTLoRunner();
  ILoRunner list1 = new ConsLoRunner(johnny, new ConsLoRunner(joan, mtlist));
  ILoRunner list2 = new ConsLoRunner(frank, new ConsLoRunner(bill, list1)); 
  
  boolean testFindMethods(Tester t) {
    return
      t.checkExpect(this.list2.find(new RunnerIsFemale()),
                    new ConsLoRunner(this.joan, new MTLoRunner())) &&
      t.checkExpect(this.list2.find(new RunnerIsMale()),
                    new ConsLoRunner(this.frank,
                      new ConsLoRunner(this.bill,
                        new ConsLoRunner(this.johnny, new MTLoRunner())))) &&
      t.checkExpect(this.list2.find(
          new AndPredicate(new RunnerIsFemale(),
            new AndPredicate(new RunnerIsYounger40(),
                             new RunnerIsInFirst50()))),
        new ConsLoRunner(this.joan, new MTLoRunner())) &&
      t.checkExpect(this.list2.find(
          new OrPredicate(new RunnerIsFemale(), new RunnerIsMale())),
          this.list2); 
  }
}








