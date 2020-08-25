package Annotation;

public class AnnotationTest {
    public static void main(String[] args) {

    }
}

@MyAnnotation(value = "hello")
class Person {
    private String name;
    private int age;
}

/*
public @interface MyAnnotation {
    String value() default "hello";
}

@MyAnnotation()
class Person {
    private String name;
    private int age;
}
 */