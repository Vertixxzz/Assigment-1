import java.util.ArrayList;
import java.io.*;

 class Person {
    private String name;
    private String surname;
    private int age;
    private boolean gender; // true for Male, false for Female

    // Constructor to initialize the attributes
    public Person(String name, String surname, int age, boolean gender) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
    }

    // Method to return a string introducing the person
    public String toString() {
        String genderStr = gender ? "Male" : "Female";
        return "Hi, I am " + name + " " + surname + ", a " + age + "-year-old " + genderStr + ".";
    }
}

class Student extends Person {
    private int studentID;
    private ArrayList<Integer> grades;

    // Constructor
    public Student(String name, String surname, int age, boolean gender, int studentID) {
        super(name, surname, age, gender);
        this.studentID = studentID;
        this.grades = new ArrayList<>();
    }

    // Add a grade
    public void addGrade(int grade) {
        grades.add(grade);
    }

    // Calculate GPA
    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;
        int total = 0;
        for (int grade : grades) {
            total += grade;
        }
        return total / (double) grades.size();
    }

    // Override toString
    @Override
    public String toString() {
        return super.toString() + " I am a student with ID " + studentID + ".";
    }
}

class Teacher extends Person {
    private String subject;
    private int yearsOfExperience;
    private int salary;

    // Constructor
    public Teacher(String name, String surname, int age, boolean gender, String subject, int yearsOfExperience, int salary) {
        super(name, surname, age, gender);
        this.subject = subject;
        this.yearsOfExperience = yearsOfExperience;
        this.salary = salary;
    }

    // Give a raise
    public void giveRaise(int percentage) {
        salary += salary * percentage / 100;
    }

    // Override toString
    @Override
    public String toString() {
        return super.toString() + " I teach " + subject + ".";
    }
}

class School {
    private ArrayList<Person> members;

    // Constructor
    public School() {
        this.members = new ArrayList<>();
    }

    // Add a member
    public void addMember(Person person) {
        members.add(person);
    }

    // Override toString
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Person member : members) {
            result.append(member.toString()).append("\n");
        }
        return result.toString();
    }
}

class Main {
    public static void main(String[] args) {
        School school = new School();

        try {
            // Reading students from students.txt
            BufferedReader studentReader = new BufferedReader(new FileReader("students.txt"));
            String line;
            while ((line = studentReader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String surname = parts[1];
                int age = Integer.parseInt(parts[2]);
                boolean gender = parts[3].equalsIgnoreCase("Male");
                int studentID = Integer.parseInt(parts[4]);

                Student student = new Student(name, surname, age, gender, studentID);
                for (int i = 5; i < parts.length; i++) {
                    student.addGrade(Integer.parseInt(parts[i]));
                }
                school.addMember(student);
            }
            studentReader.close();

            // Reading teachers from teachers.txt
            BufferedReader teacherReader = new BufferedReader(new FileReader("teachers.txt"));
            while ((line = teacherReader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String surname = parts[1];
                int age = Integer.parseInt(parts[2]);
                boolean gender = parts[3].equalsIgnoreCase("Male");
                String subject = parts[4];
                int yearsOfExperience = Integer.parseInt(parts[5]);
                int salary = Integer.parseInt(parts[6]);

                Teacher teacher = new Teacher(name, surname, age, gender, subject, yearsOfExperience, salary);

                if (yearsOfExperience > 10) {
                    teacher.giveRaise(10); // 10% raise
                }

                school.addMember(teacher);
            }
            teacherReader.close();

            // Print all members
            System.out.println(school.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
