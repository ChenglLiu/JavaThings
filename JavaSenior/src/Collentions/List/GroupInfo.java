package Collentions.List;

public class GroupInfo {
    private String name;
    private Integer age;
    private Integer id;
    private Boolean isStudent;

    public GroupInfo() {
    }

    public GroupInfo(String name, Integer age, Integer id, Boolean isStudent) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.isStudent = isStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getStudent() {
        return isStudent;
    }

    public void setStudent(Boolean student) {
        isStudent = student;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                ", isStudent=" + isStudent +
                '}';
    }
}
