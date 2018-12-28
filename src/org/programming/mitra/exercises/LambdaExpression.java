package org.programming.mitra.exercises;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @author Naresh Joshi
 *
 * See complete article on below link
 *
 * https://www.programmingmitra.com/2016/06/java-lambda-expression-explained-with-example.html
 */
public class LambdaExpression {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		// Example : Thread Creation
		// Old Way
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Running thread using anonymous class");
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();

		// New Way
		runnable = () -> System.out.println("Running thread using lambda expression");
		thread = new Thread(runnable);
		thread.start();

		// Example : Comparator Creation
		Comparator<Integer> comparator = (Integer i, Integer j) -> i.compareTo(j);

		// Example : Consumer Creation
		Consumer<String> consumer = obj -> System.out.println(obj);

		// Example : Consumer Creation
		Button button = new Button();

		// Old way:
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("button clicked");
			}
		});

		// New way:
		button.addActionListener((e) -> {
			System.out.println("button clicked");
		});

		// Example : Iteration
		System.out.println("####### Old way #######");
		for (int i = 1; i < 10; i++) {
			int j = i * i;
			System.out.println(j);
		}

		System.out.println("####### Lambda(Stream) way #######");
		IntStream.range(1, 10)
				.map(num -> num * num)
				.forEach(i -> System.out.println(i));

	}

}
