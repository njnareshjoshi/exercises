package org.programming.mitra.exercises;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Naresh Joshi
 */

// 1. Declare your class as final, So other classes can't extend it and break its immutability
final class ImmutableEmployee {

    // 2. Make all your fields private they can't be accessed outside your class
    // 3. Mark them as final so no one can modify them anywhere else apart from the constructor, if you do not have any specific requirement to not do so
    private final int id;
    private final String name;
    private final Date dob;

    // 4. Create an constructor with argument so you can assign instantiate your object with a proper state
    public ImmutableEmployee(int id, String name, Date dob) {
        this.id = id;
        this.name = name;
        // 5. Initialise all your fields by deeply copying them if they are not immutable in nature
        this.dob = new Date(dob.getTime());
    }

    // 6. Do not provide setters for your fields, or define them private if you have some requirement
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // 7. Instead of returning objects from the getters return deep copy them if your objects are not immutable
    public Date getDob() {
        return new Date(dob.getTime());
    }

    @Override
    public String toString() {
        return "ImmutableEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                '}';
    }

}

public class ImmutableClassExample {

    public static void main(String[] args) throws ParseException {
        Date dob = new SimpleDateFormat("dd-mm-yyyy").parse("10-12-1993");
        ImmutableEmployee employee = new ImmutableEmployee(1, "Naresh", dob);

        System.out.println(employee); // Prints - ImmutableEmployee{id=1, name='Naresh', dob=Sun Jan 10 00:12:00 IST 1993}

        dob.setMonth(1);
        System.out.println(dob); // Prints - Wed Feb 10 00:12:00 IST 1993

        Date temp = employee.getDob();
        temp.setMonth(2);
        System.out.println(temp); // Prints - Wed Mar 10 00:12:00 IST 1993

        System.out.println(employee.getDob()); // Prints - Sun Jan 10 00:12:00 IST 1993
        System.out.println(employee); // Prints - ImmutableEmployee{id=1, name='Naresh', dob=Sun Jan 10 00:12:00 IST 1993}
    }
}