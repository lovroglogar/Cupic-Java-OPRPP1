package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

//Program prima izraz sa standardnog ulaza te ga evaluira pomocu stoga
public class StackDemo {
	public static void main(String[] args) {
		String expression;
		try {
			expression = args[0]; // Izraz za evaluirati
			String[] elements = expression.split(" ");
			ObjectStack stack = new ObjectStack();
			int operand1, operand2;
			for(String element: elements) {
				
				// ako je element znamenka stavlja se na vrh stoga
				if((element.charAt(0) >= 48 && element.charAt(0) <= 57) || element.length() > 1) {
					stack.push(Integer.parseInt(element));
				}
				else { // Inace obavlja se procitana operacija na gornja 2 elementa stoga
					operand1 = (Integer) stack.pop();
					operand2 = (Integer) stack.pop();
					switch(element) {
						case "+": stack.push(operand2 + operand1); break;
						case "-": stack.push(operand2 - operand1); break;
						case "*": stack.push(operand2 * operand1); break;
						case "/": 
							if(operand1 == 0) {
								throw new Exception("Dividing by 0 is not good...");
							}else {
								stack.push(operand2 / operand1);
								break;
							}
						case "%": stack.push(operand2 % operand1); break;
					}
				}
			}
			if(stack.size() != 1)
				throw new Exception("Invalid expression");
			else
				System.out.println(stack.pop());
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
