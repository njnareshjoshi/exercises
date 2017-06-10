package org.programming.mitra.exercises;

/**
 * @author Naresh Joshi
 */
public class OverridingInternalExample {

    private static class Mammal {
        public void speak() { System.out.println("Well might speak something like ohlllalalalalalaoaoaoa"); }
    }

    private static class Human extends Mammal {

        @Override
        public void speak() { System.out.println("Hello"); }

        // Valid overload of speak
        public void speak(String language) {
            if (language.equals("Hindi")) System.out.println("Namaste");
            else System.out.println("Hello");
        }

    }

    public static void main(String[] args) {
        Mammal anyMammal = new Mammal();
        anyMammal.speak();  // Output - Well might speak something like ohlllalalalalalaoaoaoa
        // 10: invokevirtual #4 // Method org/programming/mitra/exercises/OverridingInternalExample$Mammal.speak:()V

        Mammal humanMammal = new Human();
        humanMammal.speak(); // Output - Hello
        // 23: invokevirtual #4 // Method org/programming/mitra/exercises/OverridingInternalExample$Mammal.speak:()V

        Human human = new Human();
        human.speak(); // Output - Hello
        // 36: invokevirtual #7 // Method org/programming/mitra/exercises/OverridingInternalExample$Human.speak:()V

        human.speak("Hindi"); // Output - Namaste
        // 42: invokevirtual #9 // Method org/programming/mitra/exercises/OverridingInternalExample$Human.speak:(Ljava/lang/String;)V
    }
}
