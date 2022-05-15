package hr.fer.oprpp1.custom.collections.demo;

import java.util.Iterator;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.collections.SimpleHashtable;

public class TestMain {
	
	public static void main(String[] args) {
		Collection<Number> c1 = new ArrayIndexedCollection<>();
		c1.add(1);
		c1.add(2);
		c1.add(3);
		
		Collection<Integer> c2 = new LinkedListIndexedCollection<>();
		c2.add(4);
		c2.add(5);
		c2.add(0);
		
		//c1 = c2;
		
		//c1.addAll(c2);
		c1.addAllSatisfying(c2, (Number obj) -> {
			return obj.equals(0);
		});
		
		ObjectStack<String> stack = new ObjectStack<>();
		stack.push("Lovro");
		stack.push("Glogar");
		
//		while(stack.peek() != null)
//			System.out.println(stack.pop());
		
		System.out.println("C1:");
		ElementsGetter<Number> getter = c1.createElementsGetter();
		while(getter.hasNextElement()) {
			System.out.println(getter.getNextElement());
		}
		
		System.out.println("C2:");
		ElementsGetter<Integer> getter2 = c2.createElementsGetter();
		while(getter2.hasNextElement()) {
			System.out.println(getter2.getNextElement());
		}
				
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		
		System.out.println();
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}
		System.out.printf("Veličina: %d%n", examMarks.size());
	}
}
