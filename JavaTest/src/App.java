import java.util.*;
import java.io.*;

class StudentCourse {
   String code;
   String title;
   int unit;

   public StudentCourse(String code, String title, int unit) {
     this.code = code;
     this.title = title;
     this.unit = unit;
   }

   public void display() {
      System.out.println(code + "-" + title + "-" + unit + "unit");
   }
} 

class StudentsCourseManager {
  ArrayList<StudentCourse> courses = new ArrayList<>();
  
  public void addCourse(StudentCourse course) {
    courses.add(course);
    System.out.println("Added " + course.code + " Successfully ");
}

public void showAll() {
  System.out.println("\n---All Courses ---");
  for (StudentCourse c : courses) {
      c.display();
  }
}

public StudentCourse searchCourseByCode(String code) {
  for (StudentCourse c : courses) {
      if (c.code.strip().toUpperCase().equals(code.strip().toUpperCase())) {
         return c;
      }
  }
  return null;

}

public int totalUnits() {
   int  total = 0;
   for (StudentCourse c : courses) {
        total = total + c.unit;
   }
   return total;

}

public int calculateTotalRecursive(int index) {
    if (index >= courses.size()) {
       return 0;
    }
    return courses.get(index).unit + calculateTotalRecursive(index + 1);
}

public void saveToFile(String filename) {
   try {
       FileWriter f = new FileWriter(filename); 
       for (StudentCourse c : courses) {
            f.write(c.code + "," + c.title + "," + c.unit + "\n");
       }
       f.close();
       System.out.println("Saved " + courses.size() + " courses to " + filename);
   }  catch (IOException e) {
       System.out.println("Error saving file: " + e.getMessage());
   }
}

public void loadFromFile(String filename) {
   try {
       BufferedReader f = new BufferedReader(new FileReader(filename));
       courses.clear();
       String line;
       while ((line = f.readLine())!= null) {
          String[] parts = line.split(",");
          String code = parts[0];
          String title = parts[1];
          int unit = Integer.parseInt(parts[2]);
          courses.add(new StudentCourse(code, title, unit));

       }
       f.close();
       System.out.println("Loaded " + courses.size() + " courses from " + filename);
       } catch (FileNotFoundException e) {
           System.out.println("No saved file found yet. ");
       } catch (Exception e) {
           System.out.println("Error loading: " + e.getMessage());
       }
   }
}


public class App {
      public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      StudentsCourseManager manager = new StudentsCourseManager();

      manager.addCourse(new StudentCourse("COS 201", "Computer Programming I", 3));
      manager.addCourse(new StudentCourse("IFT 211", "Digital Logic Design", 2));
      manager.addCourse(new StudentCourse("COS 203", "Discrete Structures", 2));
      manager.addCourse(new StudentCourse("ENT 211", "Entrepreneurship and Innovation", 2));
      manager.addCourse(new StudentCourse("SEN 201", "Introduction to Software Engineering",2));
      manager.addCourse(new StudentCourse("MTH 201", "Mathematical Method I", 2));

      while (true) {
         System.out.println("\n--- STUDENT COURSE MANAGER ---");
         System.out.println("1. Add Course");
         System.out.println("2. View All Courses");
         System.out.println("3. Search Course by code");
         System.out.println("4. Compute Total Units");
         System.out.println("5. Save to File");
         System.out.println("6. Load from File");
         System.out.println("7. Exit Program");

         System.out.println("choose (1-7): ");
         String choice = scanner.nextLine();

         if (choice.equals("1")) {
            System.out.println("Enter code: ");
            String code = scanner.nextLine();
            System.out.println("Enter title: ");
            String title = scanner.nextLine();
            System.out.println("Enter unit: ");
            int unit = Integer.parseInt(scanner.nextLine());
            manager.addCourse(new StudentCourse(code, title, unit));

         } else if (choice.equals("2")) {
             manager.showAll();

         } else if  (choice.equals("3")) {
            System.out.println("Enter code to fiind: ");
            String code = scanner.nextLine();
            StudentCourse found = manager.searchCourseByCode(code);
            if (found!= null) {
               System.out.println("Found");
               found.display();
            } else {
               System.out.println("Course " + code + " not found. ");
            }
         } else if (choice.equals("4")) {
            System.out.println("Total units (loop): " + manager.totalUnits());
            System.out.println("Total units (recursion): " + manager.calculateTotalRecursive(0));

         } else if (choice.equals("5")) {
             manager.saveToFile("student_courses.txt");

         } else if (choice.equals("6")) {
            manager.loadFromFile("student_courses.txt");

         } else if (choice.equals("7")) {
            System.out.println("Thank You!");
            break;

         } else {
            System.out.println("Invailid choice. Try 1-7");
         }    
         
      }
      scanner.close(); 
   }
}