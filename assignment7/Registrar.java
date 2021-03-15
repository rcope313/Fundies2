package assignment7;
import java.util.function.BiFunction;
import java.util.function.Function;
import tester.Tester;

interface IList<T> {
  <U> IList<U> map(Function<T,U> converter);
  <U> Void forEach (U u, BiFunction<T,U,Void> conv); 
  Boolean containsHelp (T t, BiFunction <T,T,Boolean> func); 
  Boolean contains (IList<T> lot, BiFunction <T,T,Boolean> func);
  <U> int countU (U u, int acc, Function<T,IList<U>> func, BiFunction<U,U,Boolean> comp); 
 
  
}

class MtList<T> implements IList<T> {
  
  MtList() {}
  
  
  public <U> IList<U> map(Function<T, U> converter) {
    return new MtList<U>();
  }
 
  public <U> Void forEach (U u, BiFunction<T,U,Void> conv) {
    return null; 
  }
  
  public Boolean containsHelp (T t, BiFunction <T,T,Boolean> func) {
    return false;
  }

  public Boolean contains (IList<T> lot, BiFunction <T,T,Boolean> func) {
    return false;
  }
  
  public <U> int countU (U u, int acc, Function<T,IList<U>> func, BiFunction<U,U,Boolean> comp) {
    return acc; 
  }
  
 
  
  
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public <U> IList<U> map(Function<T, U> converter) {
    return new ConsList<U>(converter.apply(this.first),this.rest.map(converter));
  }  
  
  public <U> Void forEach (U u, BiFunction<T,U,Void> conv) {
    conv.apply(this.first, u);
    return this.rest.forEach(u,conv); 
  }
  
  public Boolean containsHelp (T t, BiFunction <T,T,Boolean> func) {
    if (func.apply(this.first, t)) {
      return true; 
    }
      return this.rest.containsHelp(t, func); 
    }
  
  public Boolean contains (IList<T> lot, BiFunction <T,T,Boolean> func) {
    if (lot.containsHelp(this.first, func)) {
      return true;
    }
      return this.rest.contains(lot, func);  
  }
  
  public <U> int countU (U u, int acc, Function<T,IList<U>> func, BiFunction<U,U,Boolean> comp) {
    if (func.apply(this.first).containsHelp(u, comp)) {
      return this.rest.countU(u, acc + 1, func, comp); 
    }
    return this.rest.countU(u, acc, func, comp);
  }
  
}
  
class Course2Student implements Function<Course, IList<Student>> {

  public IList<Student> apply (Course c) {
     return c.students; 
}

}

class SameCourse implements BiFunction<Course,Course,Boolean> {

  public Boolean apply (Course x, Course y) {
    return x.name == y.name &&
           x.prof == y.prof &&
           x.students == y.students;
  }
}

class SameStudent implements BiFunction<Student,Student,Boolean> {
  
  public Boolean apply (Student x, Student y) {
    return x.name == y.name &&
           x.id == y.id &&
           x.courses == y.courses; 
  }
}

class Enroll implements BiFunction<Student,Course,Void> {
  
  public Void apply (Student s, Course c) {
    s.courses = new ConsList<Course> (c, s.courses);  
    return null; 
  } 
  
}


 class Instructor {
  
  String name;
  IList<Course> courses;
  
  Instructor (String name, IList<Course> courses) {
    this.name = name;
    this.courses = new MtList<Course>();   
  } 
  
  void enroll (Course c) {
    if (!c.prof.sameInstructor(this)) {
      throw new RuntimeException ("course not taught by this instructor"); 
    }
    this.courses = new ConsList<Course> (c, this.courses); 
  }
 
  
  boolean sameInstructor (Instructor that) {
    return this.name == that.name &&
           this.courses == that.courses; 
  }
  
  boolean dejaVu (Student s) {
    if (this.courses.countU(s, 0, new Course2Student(), new SameStudent()) > 1) {
      return true;
    }
    return false;     
  }
  
  
 }

class Student {
  
  String name;
  int id; 
  IList<Course> courses;
  
  Student (String name, int id, IList<Course> courses) {
    this.name = name;
    this.id = id;
    this.courses = new MtList<Course>();     
  }
  
  boolean classmates (Student that) {
    return this.courses.contains(that.courses, new SameCourse()); 
  }
  
  
  
}

class Course {
  
  String name;
  Instructor prof;
  IList<Student> students;
  
  Course (String name, Instructor prof, IList<Student> students) {
    this.name = name;
    this.prof = prof;
    this.students = students;  
    // to the instructors's course field, add this course
    this.prof.enroll(this);
    // to the student's course field, add this course
    this.students.forEach(this, new Enroll()); 
    
  }
 
}

class ExampleCourses {
  
  Course fundies, psychoBio, animation; 
  Student rachel, kyle, alex; 
  Instructor ann, rob; 
  
  IList<Student> fundiesStudents, psychoBioStudents, animationStudents; 
  IList<Course> rachelCourses, kyleCourses, alexCourses, annCourses, robCourses;
  
  
  void initData() {
    
    // Instructors are initially constructed without any Courses to teach.
    this.ann = new Instructor ("Ann", null);
    this.rob = new Instructor ("Rob", null);
    
    // Students are initially constructed without taking any Courses.
    this.rachel = new Student ("Rachel", 001, null);
    this.kyle = new Student ("Kyle", 002, null);
    this.alex = new Student ("Alex", 003, null);
    
    this.fundiesStudents = new ConsList<Student> (this.rachel,
                               new ConsList<Student> (this.kyle,
                               new MtList<Student>())); 
    this.psychoBioStudents = new ConsList<Student> (this.rachel,
                                 new ConsList<Student> (this.kyle,
                                 new MtList<Student>()));
    this.animationStudents = new ConsList<Student>(this.alex,
                                 new MtList<Student>()); 
   
    
    this.fundies = new Course ("Fundies", this.ann, this.fundiesStudents); 
    this.psychoBio = new Course ("PsychoBio", this.ann, this.psychoBioStudents);
    this.animation = new Course ("Animation", this.rob, this.animationStudents); 
    
    this.rachelCourses = new ConsList<Course> (this.psychoBio, 
        new ConsList<Course> (this.fundies,
        new MtList<Course>())); 
    this.kyleCourses = new ConsList<Course> (this.fundies, 
         new ConsList<Course> (this.psychoBio,
         new MtList<Course>())); 
    this.alexCourses = new ConsList<Course> (this.fundies, 
         new ConsList<Course> (this.animation,
         new MtList<Course>()));
    this.annCourses = new ConsList<Course> (this.psychoBio, 
     new ConsList<Course> (this.fundies,
     new MtList<Course>())); 
    this.robCourses = new ConsList<Course> (this.animation, 
     new MtList<Course>()); 
                               
  }
  
  void testInstructorCourses(Tester t) {
    this.initData();
    t.checkExpect(this.ann.courses, this.annCourses); 
    t.checkExpect(this.rachel.courses, this.rachelCourses); 
  }
  
  void testClassmates(Tester t) {
    this.initData();
    t.checkExpect(this.rachel.classmates(this.alex), false);
  }
  
  void testDejaVu (Tester t) {
    this.initData();
    t.checkExpect(this.ann.dejaVu(this.rachel), true); 
    t.checkExpect(this.ann.dejaVu(this.alex), false); 
  }
}

  









