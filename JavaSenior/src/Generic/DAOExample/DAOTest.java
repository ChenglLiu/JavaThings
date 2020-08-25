package Generic.DAOExample;

import org.junit.Test;

import java.util.List;

public class DAOTest {
    @Test
    public void test01() {
        CustomerDAO customerDAO = new CustomerDAO();

        customerDAO.save("MM", new Customer());

        Customer customer = customerDAO.getIndex(3);
        List<Customer> list = customerDAO.getForList(7);

        System.out.println("----------");
        StudentDAO studentDAO = new StudentDAO();

        studentDAO.save("GG", new Student());
        //studentDAO.save("Plus", new Student());       //NullPointerException: map has not instantiated
        //studentDAO.list();

        System.out.println("****************");
        DAO<Student> dao = new DAO<>();
        dao.save("AA", new Student(1000, 23, "Plus"));
        dao.save("BB", new Student(1001, 23, "Accepted"));
        dao.save("CC", new Student(1002, 23, "Torking"));

        dao.update("AA", new Student(1000, 23, "Plus Torking"));

        List<Student> list1 = dao.list();
        System.out.println(list1);

        System.out.println("#################");

        dao.delete("CC");
        list1 = dao.list();
        list1.forEach(System.out::println);
    }
}
