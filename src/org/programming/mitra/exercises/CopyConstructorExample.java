package org.programming.mitra.exercises;

class Mammal {

    protected String type;

    public Mammal(String type) {
        this.type = type;
    }

    public Mammal(Mammal original) {
        this.type = original.type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Mammal cloneObject() {
        return new Mammal(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mammal mammal = (Mammal) o;

        if (!type.equals(mammal.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return "Mammal{" + "type='" + type + "'}";
    }
}

class Human extends Mammal {

    protected String name;

    public Human(String type, String name) {
        super(type);
        this.name = name;
    }

    public Human(Human original) {
        super(original.type);
        this.name = original.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Human cloneObject() {
        return new Human(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Human human = (Human) o;

        if (!type.equals(human.type)) return false;
        if (!name.equals(human.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Human{" + "type='" + type + "', name='" + name + "'}";
    }
}

public class CopyConstructorExample {

    public static void main(String[] args) {
        Mammal mammal = new Mammal("Human");
        Human human = new Human("Human", "Naresh");

        Mammal mammalHuman = new Human("Human", "Mahesh");

        Mammal clonedMammal = new Mammal(mammal);
        //Mammal clonedMammal = mammal.cloneObject();

        Human clonedHuman = new Human(human);
        //Human clonedHuman = human.cloneObject();

        //Mammal clonedMammalHuman = new Human(mammalHuman); // compilation error
        //Mammal clonedMammalHuman = new Mammal(mammalHuman);
        Mammal clonedMammalHuman = mammalHuman.cloneObject();

        System.out.println("Object " + mammal + " and copied object " + clonedMammal + " are == : " + (mammal == clonedMammal));
        System.out.println("Object " + mammal + " and copied object " + clonedMammal + " are equal : " + (mammal.equals(clonedMammal)) + "\n");


        System.out.println("Object " + human + " and copied object " + clonedHuman + " are == : " + (human == clonedHuman));
        System.out.println("Object " + human + " and copied object " + clonedHuman + " are equal : " + (human.equals(clonedHuman)) + "\n");

        System.out.println("Object " + mammalHuman + " and copied object " + clonedMammalHuman + " are == : " + (mammalHuman == clonedMammalHuman));
        System.out.println("Object " + mammalHuman + " and copied object " + clonedMammalHuman + " are equal : " + (mammalHuman.equals(clonedMammalHuman)) + "\n");

    }

}
