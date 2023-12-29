import java.util.*;

class TimeSlot {
    private String day;
    private int startTime;
    private int endTime;

    public TimeSlot(String day, int startTime, int endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public boolean overlapsWith(TimeSlot other) {
        return this.day.equals(other.day) &&
               ((this.startTime >= other.startTime && this.startTime < other.endTime) ||
                (this.endTime > other.startTime && this.endTime <= other.endTime));
    }
}

class Class {
    private String className;
    private TimeSlot timeSlot;
    private List<Class> overlappingClasses;

    public Class(String className, TimeSlot timeSlot) {
        this.className = className;
        this.timeSlot = timeSlot;
        this.overlappingClasses = new ArrayList<>();
    }

    public String getClassName() {
        return className;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void addOverlappingClass(Class otherClass) {
        overlappingClasses.add(otherClass);
    }

    public List<Class> getOverlappingClasses() {
        return overlappingClasses;
    }
}

class Student {
    private String studentId;
    private List<Class> classes;

    public Student(String studentId) {
        this.studentId = studentId;
        this.classes = new ArrayList<>();
    }

    public void addClass(Class aClass) {
        classes.add(aClass);
    }

    public List<Class> findClassesByTimeSlot(TimeSlot targetTimeSlot) {
        List<Class> matchingClasses = new ArrayList<>();
        for (Class aClass : classes) {
            if (aClass.getTimeSlot().overlapsWith(targetTimeSlot)) {
                matchingClasses.add(aClass);
            }
        }
        return matchingClasses;
    }
}

class GraphAlgorithmProject {
    public static void main(String[] args) {
        // Create a sample schedule
        TimeSlot timeSlot1 = new TimeSlot("Monday", 10, 12);
        TimeSlot timeSlot2 = new TimeSlot("Monday", 13, 14);
        TimeSlot timeSlot3 = new TimeSlot("Monday", 11, 13);
        
        TimeSlot timeSlot4 = new TimeSlot("Wednesday", 17, 18);
        TimeSlot timeSlot5 = new TimeSlot("Wednesday", 15, 19);
        TimeSlot timeSlot6 = new TimeSlot("Wednesday", 14, 15);   

        Class class1 = new Class("Math", timeSlot1);
        Class class2 = new Class("Bangla", timeSlot2);
        Class class3 = new Class("English", timeSlot3);
        
      
        Class class4 = new Class("History", timeSlot4);
        Class class5 = new Class("Algorithm Lab", timeSlot5);
        Class class6 = new Class("DataBase", timeSlot6);

        class1.addOverlappingClass(class3);
         class2.addOverlappingClass(class3);
          class4.addOverlappingClass(class5);
          class6.addOverlappingClass(class4);
            class5.addOverlappingClass(class4);
       

        // Create a student
        Student student = new Student("habib");

        // Add classes to the student's schedule
        student.addClass(class1);
        student.addClass(class2);
        student.addClass(class3);
        student.addClass(class4);
        student.addClass(class5);
        student.addClass(class6);

        // Search for classes on a specific time slot
        TimeSlot targetTimeSlot = new TimeSlot("Wednesday", 14, 19);

        List<Class> matchingClasses = findClassesByTimeSlotDFS(class5, targetTimeSlot, new HashSet<>());

        // Display the results
        System.out.println("Classes for student " + student.getClass() + " on " + targetTimeSlot.getDay() + ":");
        for (Class aClass : matchingClasses) {
            System.out.println(aClass.getClassName() + " at " + aClass.getTimeSlot().getStartTime() + " - " + aClass.getTimeSlot().getEndTime());
        }
    }

    private static List<Class> findClassesByTimeSlotDFS(Class currentClass, TimeSlot targetTimeSlot, Set<Class> visited) {
        List<Class> matchingClasses = new ArrayList<>();
        visited.add(currentClass);

        if (currentClass.getTimeSlot().overlapsWith(targetTimeSlot)) {
            matchingClasses.add(currentClass);
        }

        for (Class adjacentClass : currentClass.getOverlappingClasses()) {
            if (!visited.contains(adjacentClass)) {
                matchingClasses.addAll(findClassesByTimeSlotDFS(adjacentClass, targetTimeSlot, visited));
            }
        }

        return matchingClasses;
    }
}
