package Generic.DAOExample;

import java.util.Objects;

public class Student {
    private int id;
    private int age;
    private String name;

    public Student() {
    }

    public Student(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" + "id = " + id + ", age = " + age + ", name = '" + name + "\'}";
    }

    @Override
    public boolean equals(Object obj) {
        Student student = (Student)obj;
        if (id != student.id) return false;
        if (age != student.age) return false;
        return Objects.equals(name, student.name);
        //return name != null ? name.equals(student.name) : student.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + age;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
