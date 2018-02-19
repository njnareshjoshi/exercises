package org.programming.mitra.exercises;

import java.util.Date;

/**
 * @author Naresh Joshi
 */

// 1. Declare your class as final, So other classes can't extend it and break its immutability
final class ImmutableEmployee {

    // 2. Make all your fields private they can't be accessed outside your class
    //    Also mark them as final so can not modify them anywhere else apart from the constructor, if you do not have any specific requirement to not do so
    private final int id;
    private final String name;
    private final String email;
    private final Date dob;

    // 3. Create an constructor with argument so you can assign instantiate your object with a proper state
    public ImmutableEmployee(int id, String name, String email, Date dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        // 4. Initialise all your fields by deeply copying them if they are not immutable in nature
        this.dob = new Date(dob.getTime());
    }

    // 4. Do not provide setters for your fields, or define them private if you have some requirement
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // 5. Instead of returning objects from the getters return deep copy them if your objects are not immutable
    public Date getDob() {
        return new Date(dob.getTime());
    }
}

public class ImmutableClassExample {

    private static class Mammal {
        public void speak() {
            System.out.println("Well might speak something like ohlllalalalalalaoaoaoa");
        }
    }

    public static void main(String[] args) {
        Mammal anyMammal = new Mammal();
    }
}