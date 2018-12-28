package org.programming.mitra.exercises;

/**
 * @author Naresh Joshi
 *
 * See complete articles on below links
 *
 * https://www.programmingmitra.com/2017/05/everything-about-method-overloading-vs-method-overriding.html,
 * https://www.programmingmitra.com/2017/12/why-we-should-follow-method-overriding-rules.html,
 * https://www.programmingmitra.com/2017/05/how-does-jvm-handle-method-overriding-internally.html,
 * https://www.programmingmitra.com/2018/11/why-instance-variable-of-super-class-is-not-overridden-In-sub-class.html
 */
public class OverloadingOverridingExample {

    public static void main(String[] args) {

        Mammal mammal1 = new Cat();
        System.out.println(mammal1.speak());

        Mammal mammal2 = new Human();
        System.out.println(mammal2.speak());

        // Compilation error speak(String) is specific to Human class and we can not refer it from Mammal reference
        // mammal1.speak("Hindi");

        Human human = new Human();
        System.out.println(human.speak());
        System.out.println(human.speak("Hindi"));

        // Compilation Error ambiguous method call between calculate(int, long) and calculate(long, int)
        // human.calculate(5, 5);


        System.out.println(human.calculate(5, 5L));
        System.out.println(human.calculate(5L, 5));
    }

    static abstract class Mammal {
        public String speak() {
            return "Well might speak something like ohlllalalalalalaoaoaoa";
        }
    }

    static class Cat extends Mammal {

        @Override
        public String speak() {
            return "Meow";
        }

    }

    static class Human extends Mammal {

        @Override
        // Using Override annotation is not necessary but using it will tell you if you are not obeying overriding rules
        public String speak() {
            return "Hello";
        }

        // Valid overload of speak
        public String speak(String language) {
            if (language.equals("Hindi")) return "Namaste";
            else return "Hello";
        }

        public long calculate(int a, long b) {
            return a + b;
        }

        // However nobody should do it but Valid overload of calculate by just changing sequence of arguments
        public long calculate(long b, int a) {
            return a + b;
        }
    }
}
