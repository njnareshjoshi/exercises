package org.programming.mitra.exercises;

/**
 * @author Naresh Joshi
 *
 * See complete article on below link
 *
 * https://www.programmingmitra.com/2017/04/Difference-Between-ClassNotFoundException-and-NoClassDefFoundError.html
 */
public class ClassNotFoundExceptionExample {
    public static void main(String[] args) throws Exception {

        // NoClassDefFoundError Example
        // Do javac on ClassNotFoundExceptionExample.java,
        // Program will compile successfully because Employee class exits
        // Manually delete Employee.class file
        // Run the program using java ClassNotFoundExceptionExample
        Employee emp = new Employee();
        emp.saySomething();

        // ClassNotFoundException Example
        // Provide any class to Class.forName() which doe not exist
        // Or compile ClassNotFoundExceptionExample.java and then manually delete Person.class file so Person class will become unavailable
        // Run the program using java ClassNotFoundExceptionExample
        Class personClass = Class.forName("Person");
        Person person = (Person) personClass.newInstance();
        person.saySomething();
    }

    static class Employee {
        void saySomething() {
            System.out.println("Hello");
        }
    }

    static class Person {
        void saySomething() {
            System.out.println("Hello");
        }
    }
}