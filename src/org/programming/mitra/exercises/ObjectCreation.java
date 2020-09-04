package org.programming.mitra.exercises;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * @author Naresh Joshi
 *
 * See complete articles on below links.
 *
 * https://www.programmingmitra.com/2016/05/different-ways-to-create-objects-in-java-with-example.html,
 * https://www.programmingmitra.com/2016/05/creating-objects-through-reflection-in-java-with-example.html
 */
public class ObjectCreation {
    public static void main(String... args) throws Exception {

        // 1. By using new keyword
        Employee emp1 = new Employee();
        emp1.setName("emp1");


        // 2. By using Class class's newInstance() method
        // Employee emp2 = (Employee) Class.forName("org.programming.mitra.exercises.Employee").newInstance();
        Employee emp2 = Employee.class.newInstance();
        emp2.setName("emp2");


        // 3. By using Constructor class's newInstance() method
        Constructor<Employee> constructor = Employee.class.getConstructor();

        Employee emp3 = constructor.newInstance();
        emp3.setName("emp3");

        // 4. By using clone() method
        Employee emp4 = (Employee) emp3.clone();
        emp4.setName("emp4");


        // 5. By using Deserialization

        // Serialization
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.obj"))) {
            out.writeObject(emp4);
        }

        //Deserialization
        Employee emp5;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"))) {
            emp5 = (Employee) in.readObject();
            emp5.setName("emp5");
        }

        System.out.println(emp1 + ", hashcode : " + emp1.hashCode());
        System.out.println(emp2 + ", hashcode : " + emp2.hashCode());
        System.out.println(emp3 + ", hashcode : " + emp3.hashCode());
        System.out.println(emp4 + ", hashcode : " + emp4.hashCode());
        System.out.println(emp5 + ", hashcode : " + emp5.hashCode());
    }
}

class Employee implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public Employee() {
        System.out.println("Employee Constructor Called...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("Employee{name='%s'}", name);
    }

    @Override
    public Object clone() {

        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
