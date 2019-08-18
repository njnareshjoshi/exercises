package org.programming.mitra.exercises;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * See complete articles on below links.
 *
 * https://www.programmingmitra.com/2019/08/how-to-deep-clone-an-object-using-java-in-memory-serialization.html,
 * https://www.programmingmitra.com/2019/08/what-is-serialization-everything-about-java-serialization-explained-with-example.html,
 * https://www.programmingmitra.com/2019/08/how-to-customize-serialization-in-java-by-using-externalizable-interface.html,
 * https://www.programmingmitra.com/2016/11/Java-Cloning-Types-of-Cloning-Shallow-Deep-in-Details-with-Example.html,
 * https://www.programmingmitra.com/2017/01/Java-cloning-copy-constructor-versus-Object-clone-or-cloning.html,
 * https://www.programmingmitra.com/2017/01/java-cloning-why-copy-constructors-are-not-sufficient-or-good.html
 * https://www.programmingmitra.com/2016/05/different-ways-to-create-objects-in-java-with-example.html
 *
 * @author Naresh Joshi
 */
public class DeepCloningUsingSerializationExample
{
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        Employee emp = new Employee("Naresh Joshi", LocalDate.now(), Arrays.asList("Java", "Scala", "Spring"));
        System.out.println("Employee object: " + emp);

        // Deep cloning `emp` object by using our `deepClone` method.
        Employee clonedEmp = emp.deepClone();
        System.out.println("Cloned employee object: " + clonedEmp);

        System.out.println();

        // All of this will print false because both object are separate
        System.out.println(emp == clonedEmp);
        System.out.println(emp.getDoj() == clonedEmp.getDoj());
        System.out.println(emp.getSkills() == clonedEmp.getSkills());

        System.out.println();

        // All of this will print true because `clonedEmp` is a deep clone of `emp` and both have same content
        System.out.println(Objects.equals(emp, clonedEmp));
        System.out.println(Objects.equals(emp.getDoj(), clonedEmp.getDoj()));
        System.out.println(Objects.equals(emp.getSkills(), clonedEmp.getSkills()));
    }

    static class Employee implements Serializable
    {
        private static final long serialVersionUID = 2L;

        private String name;
        private LocalDate doj;
        private List<String> skills;

        public Employee(String name, LocalDate doj, List<String> skills)
        {
            this.name = name;
            this.doj = doj;
            this.skills = skills;
        }

        public String getName()
        {
            return name;
        }

        public LocalDate getDoj()
        {
            return doj;
        }

        public List<String> getSkills()
        {
            return skills;
        }

        // Method to deep clone an object using in memory serialization.
        public Employee deepClone() throws IOException, ClassNotFoundException
        {
            // First serializing the object and its state to memory using ByteArrayOutputStream instead of FileOutputStream.
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);

            // And then deserializing it from memory using ByteArrayOutputStream instead of FileInputStream,
            // Deserialization process will create a new object with the same state as in the serialized object.
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            return (Employee) in.readObject();
        }

        @Override
        public String toString()
        {
            return String.format("Employee{name='%s', doj=%s, skills=%s}", name, doj, skills);
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            Employee employee = (Employee) o;

            return Objects.equals(name, employee.name) &&
                Objects.equals(doj, employee.doj) &&
                Objects.equals(skills, employee.skills);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(name, doj, skills);
        }
    }

}