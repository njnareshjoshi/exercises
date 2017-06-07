package org.programming.mitra.exercises;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Naresh Joshi
 */
public class CloningExample {

    public static void main(String[] args) throws CloneNotSupportedException {

        City city = new City("Dehradun");
        Country country = new Country("India");
        Person person1 = new Person("Naresh", 10000, city, country);
        System.out.println(person1);

        Person person2 = person1.clone();
        System.out.println(person2);

        // Evaluate false, because person1 and person2 holds different objects
        if (person1 == person2) {
            System.out.println("Both person1 and person2 holds same object");
        }

        // Evaluate true, person1 and person2 are equal and have same content
        if (person1.equals(person2)) {
            System.out.println("But both person1 and person2 are equal and have same content");
        }

        // Evaluate false
        if (person1.getCity() == person2.getCity()) {
            System.out.println("Both person1 and person2 have same city object");
        }

        // Evaluate true, because we have not implemented clone in Country class
        if (person1.getCountry() == person2.getCountry()) {
            System.out.println("Both person1 and person2 have same country object");
        }

        // Now lets change city and country object and print person1 and person2
        city.setName("Pune");
        country.setName("IN");

        // person1 will print new Pune city
        System.out.println(person1);
        // while person2 will still print Dehradun city because person2.city holds a separate city object
        System.out.println(person2);
    }
}

class Person implements Cloneable {
    private String name; // Will holds address of the String object which lives in SCP, instead of String object itself
    private int income; // Will hold bit representation of int, which is assigned to it
    private City city; // Will holds address of the City object which lives in heap, instead of City object
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Person(String name, int income, City city, Country country) {
        super();
        this.name = name;
        this.income = income;
        this.city = city;
        this.country = country;
    }

    /**
     * Copy Constructor for cloning Person
     *
     * @param original
     */
    public Person(Person original) {
        this.name = new String(original.name);
        this.income = original.income;
        this.city = new City(original.city);
        this.country = new Country(original.country);
    }

    /**
     * Method to clone person using Serialization
     *
     * @param original
     * @return
     */
    public Person copy(Person original) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.obj"));
             ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"))) {

            // Serialization
            out.writeObject(original);

            //De Serialization
            return (Person) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e); // log exception or throw it by your own way
        }
    }

    // We can also create this any other name
    @Override
    public Person clone() throws CloneNotSupportedException {
        Person clonedObj = (Person) super.clone();
        clonedObj.name = new String(this.name);
        clonedObj.city = this.city.clone();
        return clonedObj;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", income=" + income + ", city=" + city + ", country=" + country + "]";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((city == null) ? 0 : city.hashCode());
        result = 31 * result + ((country == null) ? 0 : country.hashCode());
        result = 31 * result + income;
        result = 31 * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (income != other.income)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}

class City implements Cloneable {

    private String name;

    /**
     * Copy Constructor for cloning City
     *
     * @param original
     */
    public City(City original) {
        this.name = new String(original.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City(String name) {
        super();
        this.name = name;
    }

    @Override
    public City clone() throws CloneNotSupportedException {
        return (City) super.clone();
    }

    @Override
    public String toString() {
        return "City [name=" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        City other = (City) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}

class Country {

    private String name;

    /**
     * Copy Constructor for cloning City
     *
     * @param original
     */
    public Country(Country original) {
        this.name = new String(original.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country [name=" + name + "]";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Country other = (Country) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
