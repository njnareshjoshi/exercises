package org.programming.mitra.exercises;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class LambdaExpression {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		// Example : Thread Creation
		// Old Way
		Runnable r1 = new Runnable() {

			@Override
			public void run() {
				System.out.println("In thread t1");
			}
		};
		Thread t1 = new Thread(r1);
		t1.start();

		// New Way
		Runnable r2 = () -> System.out.println("In thread t2");
		Thread t2 = new Thread(r2);
		t2.start();

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
