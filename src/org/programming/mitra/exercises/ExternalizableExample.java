package org.programming.mitra.exercises;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * See complete articles on below links.
 *
 * https://www.programmingmitra.com/2019/08/what-is-serialization-everything-about-java-serialization-explained-with-example.html,
 * https://www.programmingmitra.com/2019/08/how-to-customize-serialization-in-java-by-using-externalizable-interface.html,
 * https://www.programmingmitra.com/2016/05/different-ways-to-create-objects-in-java-with-example.html
 *
 * @author Naresh Joshi
 */
public class ExternalizableExample
{

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        Employee empObj = new Employee("Shanti", "Sharma", 25, "IT");
        System.out.println("Object before serialization  => " + empObj.toString());

        // Serialization
        serialize(empObj);

        // Deserialization
        Employee deserializedEmpObj = deserialize();
        System.out.println("Object after deserialization => " + deserializedEmpObj.toString());
    }

    // Serialization code
    static void serialize(Employee empObj) throws IOException
    {
        try (FileOutputStream fos = new FileOutputStream("data.obj");
             ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(empObj);
        }
    }

    // Deserialization code
    static Employee deserialize() throws IOException, ClassNotFoundException
    {
        try (FileInputStream fis = new FileInputStream("data.obj");
             ObjectInputStream ois = new ObjectInputStream(fis))
        {
            return (Employee) ois.readObject();
        }
    }

    // Using Externalizable, complete serialization/deserialization logic becomes our responsibility,
    // We need to tell what to serialize using writeExternal() method and what to deserialize using readExternal(),
    // We can even serialize/deserialize static and transient variables,
    // With implementation of writeExternal() and readExternal(),  methods writeObject() and readObject() becomes redundant and they do not get called.
    static class Employee implements Externalizable
    {
        // This serialVersionUID field is necessary for Serializable as well as Externalizable to provide version control,
        // Compiler will provide this field if we do not provide it which might change if we modify class structure of our class, and we will get InvalidClassException,
        // If we provide a value to this field and do not change it, serialization-deserialization will not fail if we change our class structure.
        private static final long serialVersionUID = 2L;

        private String firstName;
        private transient String lastName; // Using Externalizable, we can even serialize/deserialize transient variables, so declaring fields transient becomes unnecessary.
        private int age;
        private static String department; // Using Externalizable, we can even serialize/deserialize static variables according to our need.

        // Mandatory to have to make our class Externalizable
        // When an Externalizable object is reconstructed, the object is created using public no-arg constructor before the readExternal method is called.
        // If a public no-arg constructor is not present then a InvalidClassException is thrown at runtime.
        public Employee()
        {
        }

        // All-arg constructor to create objects manually
        public Employee(String firstName, String lastName, int age, String department)
        {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            Employee.department = department;

            validateAge();
        }

        private void validateAge()
        {
            System.out.println("Validating age.");

            if (age < 18 || age > 70)
            {
                throw new IllegalArgumentException("Not a valid age to create an employee");
            }
        }

        @Override
        // We need to tell what to serialize in writeExternal() method
        public void writeExternal(ObjectOutput out) throws IOException
        {
            System.out.println("Custom externalizable serialization logic invoked.");

            out.writeUTF(firstName);
            out.writeUTF(lastName);
            out.writeInt(age);
            out.writeUTF(department);
        }

        @Override
        // We need to tell what to deserialize in readExternal() method
        // The readExternal method must read the values in the same sequence and with the same types as were written by writeExternal
        public void readExternal(ObjectInput in) throws IOException
        {
            System.out.println("Custom externalizable serialization logic invoked.");

            firstName = in.readUTF();
            lastName = in.readUTF();
            age = in.readInt();
            department = in.readUTF();

            validateAge();
        }

        @Override
        public String toString()
        {
            return String.format("Employee {firstName='%s', lastName='%s', age='%s', department='%s'}", firstName, lastName, age, department);
        }

        // Custom serialization logic, It will be called only if we have implemented Serializable instead of Externalizable.
        private void writeObject(ObjectOutputStream oos) throws IOException
        {
            System.out.println("Custom serialization logic invoked.");
        }

        // Custom deserialization logic, It will be called only if we have implemented Serializable instead of Externalizable.
        private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
        {
            System.out.println("Custom deserialization logic invoked.");
        }
    }

}